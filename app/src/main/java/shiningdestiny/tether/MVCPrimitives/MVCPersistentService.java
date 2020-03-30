package shiningdestiny.tether.MVCPrimitives;

import shiningdestiny.tether.Platforms.Platform;

// Base class for persistent MVC services.
public abstract class MVCPersistentService implements MVCService {
    // Overloadables.
    protected abstract void addLoadTasks(MVCAsyncExecutor executor);
    protected abstract void addSaveTasks(MVCAsyncExecutor executor);

    // Internal members.
    private MVCServiceStatus mvcServiceStatus;
    private MVCAsyncExecutor mvcAsyncExecutor;

    // Constructors.
    public MVCPersistentService(Platform platform) {
        mvcServiceStatus = MVCServiceStatus.SERVICE_NOT_READY;
        mvcAsyncExecutor = platform.createMVCAsyncExecutor();
    }

    // Interface methods.
    @Override
    public boolean isServiceReady() {
        return (mvcServiceStatus == MVCServiceStatus.SERVICE_IS_READY);
    }

    @Override
    public void beginLoadingService(final MVCCallback<MVCService> onServiceLoadedCallback) {
        if(mvcServiceStatus == MVCServiceStatus.SERVICE_NOT_READY) {
            addLoadTasks(mvcAsyncExecutor);
            mvcAsyncExecutor.executePendingTasks(new MVCCallback<Void>() {
                @Override
                public void invoke(Void obj) {
                    mvcServiceStatus = MVCServiceStatus.SERVICE_IS_READY;
                    onServiceLoadedCallback.invoke(MVCPersistentService.this);
                }
            });
        }
    }

    @Override
    public void saveService() {
        if(mvcServiceStatus == MVCServiceStatus.SERVICE_IS_READY) {
            addSaveTasks(mvcAsyncExecutor);
            mvcAsyncExecutor.executePendingTasks(null);
        }
    }
}
