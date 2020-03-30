package shiningdestiny.tether.MVCPrimitives;

// A base interface for MVC service objects.
public interface MVCService {
    // Return if the service has been loaded.
    public boolean isServiceReady();

    // Begin loading the service from persistent storage.
    public void beginLoadingService(MVCCallback<MVCService> onServiceLoadedCallback);

    // Begin saving the service to persistent storage.
    public void saveService();
}
