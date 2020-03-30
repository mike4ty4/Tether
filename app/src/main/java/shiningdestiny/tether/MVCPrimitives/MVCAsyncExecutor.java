package shiningdestiny.tether.MVCPrimitives;

// An interface for executors to execute asynchronous tasks.
public interface MVCAsyncExecutor {
    // Register a task to be executed.
    public <T> void registerTask(MVCAsyncTask<T> mvcAsyncTask, MVCCallback<T> onCompletedCallback);

    // Execute all pending tasks.
    public void executePendingTasks(MVCCallback<Void> onAllCompletedCallback);

    // Purge the pending task queue.
    public void purgeJobQueue();
}
