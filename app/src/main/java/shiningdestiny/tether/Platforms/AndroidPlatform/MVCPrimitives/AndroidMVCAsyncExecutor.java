package shiningdestiny.tether.Platforms.AndroidPlatform.MVCPrimitives;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;

import shiningdestiny.tether.MVCPrimitives.MVCAsyncExecutor;
import shiningdestiny.tether.MVCPrimitives.MVCAsyncTask;
import shiningdestiny.tether.MVCPrimitives.MVCCallback;

// The asynchronous task executor for the Android platform. The purpose of this is to execute a
// task on work threads, and leave callbacks to execute on the main thread (UI thread).
public class AndroidMVCAsyncExecutor implements MVCAsyncExecutor {
    private class MVCAsyncTaskListEntry {
        private UUID taskUuid;
        private MVCAsyncTask mvcAsyncTask;
        private MVCCallback onCompletedCallback;

        public MVCAsyncTaskListEntry(UUID taskUuid,
                                     MVCAsyncTask mvcAsyncTask,
                                     MVCCallback onCompletedCallback) {
            this.taskUuid = taskUuid;
            this.mvcAsyncTask = mvcAsyncTask;
            this.onCompletedCallback = onCompletedCallback;
        }

        public UUID getTaskUuid() {
            return taskUuid;
        }

        public MVCAsyncTask getMvcAsyncTask() {
            return mvcAsyncTask;
        }

        public MVCCallback getOnCompletedCallback() {
            return onCompletedCallback;
        }
    }

    private class MVCAsyncTaskBatch {
        private List<MVCAsyncTaskListEntry> mvcAsyncTaskList = new ArrayList<>();

        private class MVCBatchTaskCallback<T> implements MVCCallback<T> {
            private MVCAsyncTaskListEntry mvcAsyncTaskListEntry;
            private MVCCallback<Void> onBatchCompletedCallback;

            public MVCBatchTaskCallback(MVCAsyncTaskListEntry mvcAsyncTaskListEntry,
                                        MVCCallback<Void> onBatchCompletedCallback) {
                this.mvcAsyncTaskListEntry = mvcAsyncTaskListEntry;

            }
            @Override
            public void invoke(T obj) {
                mvcAsyncTaskListEntry.getOnCompletedCallback().invoke(obj);
                mvcAsyncTaskList.remove(mvcAsyncTaskListEntry);

                if(mvcAsyncTaskList.isEmpty()) {
                    onBatchCompletedCallback.invoke(null);
                }
            }
        }

        public <T> void addTask(UUID taskUuid, MVCAsyncTask<T> mvcAsyncTask, MVCCallback<T> onCompletedCallback) {
            MVCAsyncTaskListEntry mvcAsyncTaskListEntry = new MVCAsyncTaskListEntry
                    (taskUuid, mvcAsyncTask, onCompletedCallback);
            mvcAsyncTaskList.add(mvcAsyncTaskListEntry);
        }

        public void submit(MVCCallback<Void> onBatchCompleteCallback) {
            for(MVCAsyncTaskListEntry mvcAsyncTaskListEntry : mvcAsyncTaskList) {
                MVCBatchTaskCallback mvcBatchTaskCallback = new MVCBatchTaskCallback
                        (mvcAsyncTaskListEntry, onBatchCompleteCallback);
                AndroidMVCAsyncExecutorBackend.getInstance().
                        executeTask(mvcAsyncTaskListEntry.getTaskUuid(),
                                mvcAsyncTaskListEntry.getMvcAsyncTask(),
                                mvcBatchTaskCallback);
            }
        }
    }

    private MVCAsyncTaskBatch pendingBatch;

    public AndroidMVCAsyncExecutor() {
        pendingBatch = new MVCAsyncTaskBatch();
    }

    @Override
    public <T> void registerTask(MVCAsyncTask<T> mvcAsyncTask, MVCCallback<T> onCompletedCallback) {
        UUID taskUuid = UUID.randomUUID();
        pendingBatch.addTask(taskUuid, mvcAsyncTask, onCompletedCallback);
    }

    @Override
    public void executePendingTasks(MVCCallback onAllCompletedCallback) {
        pendingBatch.submit(onAllCompletedCallback);
        pendingBatch = new MVCAsyncTaskBatch();
    }

    @Override
    public void purgeJobQueue() {
        pendingBatch = new MVCAsyncTaskBatch();
    }
}
