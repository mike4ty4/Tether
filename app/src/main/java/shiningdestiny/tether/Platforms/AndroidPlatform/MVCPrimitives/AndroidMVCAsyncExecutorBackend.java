package shiningdestiny.tether.Platforms.AndroidPlatform.MVCPrimitives;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import shiningdestiny.tether.MVCPrimitives.MVCAsyncTask;
import shiningdestiny.tether.MVCPrimitives.MVCCallback;

// The back-end executor object for asynchronous processing on Android. This uses a Java
// ThreadPoolExecutor with relevant hooks to the Android system and comprises a hidden singleton.
class AndroidMVCAsyncExecutorBackend {
    // Singleton implementation.
    private static AndroidMVCAsyncExecutorBackend _inst = null;
    public static AndroidMVCAsyncExecutorBackend getInstance() {
        if(_inst == null) {
            _inst = new AndroidMVCAsyncExecutorBackend();
        }

        return _inst;
    }

    // Wrapper around ThreadPoolExecutor.
    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private static int KEEP_ALIVE_TIME = 1;
    private static TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

    private static int MSG_TASK_COMPLETED = 0;

    private BlockingQueue<Runnable> taskQueue;
    private ThreadPoolExecutor threadPoolExecutor;
    private Handler onCompletionHandler;

    private class CallbackBinding<T> {
        private MVCCallback<T> mvcCallback;
        private T callbackArgument;

        public CallbackBinding(MVCCallback<T> mvcCallback, T callbackArgument) {
            this.mvcCallback = mvcCallback;
            this.callbackArgument = callbackArgument;
        }

        public void invoke() {
            mvcCallback.invoke(callbackArgument);
        }
    }

    private class TaskRunnable<T> implements Runnable {
        private UUID taskUuid;
        private MVCAsyncTask<T> mvcAsyncTask;
        private MVCCallback<T> onCompletedCallback;

        public TaskRunnable(UUID taskUuid, MVCAsyncTask<T> mvcAsyncTask, MVCCallback<T> onCompletedCallback) {
            this.taskUuid = taskUuid;
            this.mvcAsyncTask = mvcAsyncTask;
            this.onCompletedCallback = onCompletedCallback;
        }

        @Override
        public void run() {
            T callbackArgument = mvcAsyncTask.execute();
            CallbackBinding<T> callbackBinding = new CallbackBinding<>(onCompletedCallback,
                    callbackArgument);
            onCompletionHandler.sendMessage(Message.obtain(onCompletionHandler,
                    MSG_TASK_COMPLETED, callbackBinding));
        }
    }

    private AndroidMVCAsyncExecutorBackend() {
        taskQueue = new LinkedBlockingQueue<>();
        threadPoolExecutor = new ThreadPoolExecutor(
                NUMBER_OF_CORES,
                NUMBER_OF_CORES,
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                taskQueue);

        onCompletionHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if(msg.what == MSG_TASK_COMPLETED) {
                    ((CallbackBinding) msg.obj).invoke();
                }
            }
        };
    }

    public<T> void executeTask(UUID taskUuid, MVCAsyncTask<T> mvcAsyncTask, MVCCallback<T> onCompletedCallback) {
        TaskRunnable<T> taskRunnable = new TaskRunnable<>(taskUuid, mvcAsyncTask, onCompletedCallback);
        threadPoolExecutor.execute(taskRunnable);
    }
}
