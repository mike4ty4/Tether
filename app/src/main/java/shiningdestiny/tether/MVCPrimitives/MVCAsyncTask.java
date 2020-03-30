package shiningdestiny.tether.MVCPrimitives;

// An interface for asynchronous tasks used within the MVC components. The user code is to override
// this interface to define a task; executing the tasks should be done using an MVCAsyncExecutor.
public interface MVCAsyncTask<T> {
    public T execute();
}
