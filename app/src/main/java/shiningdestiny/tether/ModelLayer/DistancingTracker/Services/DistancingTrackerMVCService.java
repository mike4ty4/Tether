package shiningdestiny.tether.ModelLayer.DistancingTracker.Services;

import shiningdestiny.tether.MVCPrimitives.MVCAsyncExecutor;
import shiningdestiny.tether.MVCPrimitives.MVCPersistentService;
import shiningdestiny.tether.ModelLayer.DistancingTracker.DomainObjects.GPSMonitor;
import shiningdestiny.tether.ModelLayer.DistancingTracker.DomainObjects.PointsTracker;
import shiningdestiny.tether.Platforms.AndroidPlatform.GPS.AndroidGPSLocation;
import shiningdestiny.tether.Platforms.Platform;

// The MVC service for the distancing tracker functionality.
public class DistancingTrackerMVCService extends MVCPersistentService {
    // Class implementation.
    private GPSMonitor gpsMonitor;
    private PointsTracker pointsTracker;

    public DistancingTrackerMVCService(Platform platform) {
        super(platform);

        this.pointsTracker = new PointsTracker(new AndroidGPSLocation(System.currentTimeMillis(), 2000.0, 0.0), 10.0);
        this.gpsMonitor = new GPSMonitor(platform, pointsTracker, 1000);
    }

    public void startTracking() {
        gpsMonitor.startTracking();
    }

    public void stopTracking() {
        gpsMonitor.stopTracking();
    }

    // Persistence routines.
    @Override
    protected void addLoadTasks(MVCAsyncExecutor executor) {

    }

    @Override
    protected void addSaveTasks(MVCAsyncExecutor executor) {

    }
}
