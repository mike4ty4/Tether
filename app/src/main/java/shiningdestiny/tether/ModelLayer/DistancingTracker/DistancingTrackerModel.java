package shiningdestiny.tether.ModelLayer.DistancingTracker;

import shiningdestiny.tether.ModelLayer.DistancingTracker.Services.DistancingTrackerMVCService;
import shiningdestiny.tether.Platforms.Platform;

// The model object for the distancing tracker.
public class DistancingTrackerModel {
    private DistancingTrackerMVCService distancingTrackerMVCService;

    public DistancingTrackerModel(Platform platform) {
        distancingTrackerMVCService = new DistancingTrackerMVCService(platform);
    }

    public DistancingTrackerMVCService getDistancingTrackerMVCService() {
        return distancingTrackerMVCService;
    }
}
