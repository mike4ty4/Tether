package shiningdestiny.tether.MVCPrimitives;

// Base class for non-persistent MVC services.
public abstract class MVCNonPersistentService implements MVCService {
    @Override
    public boolean isServiceReady() {
        return true; // this type of service is always ready
    }

    @Override
    public void beginLoadingService(MVCCallback<MVCService> onServiceLoadedCallback) {
        // nothing required for this type of service
    }

    @Override
    public void saveService() {
        // nothing required for this type of service
    }
}
