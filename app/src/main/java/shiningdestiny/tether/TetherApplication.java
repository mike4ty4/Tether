package shiningdestiny.tether;

import android.app.Application;

import shiningdestiny.tether.ModelLayer.DistancingTracker.DistancingTrackerModel;
import shiningdestiny.tether.Platforms.AndroidPlatform.AndroidPlatform;

public class TetherApplication extends Application {
    private DistancingTrackerModel distancingTrackerModel;

    @Override
    public void onCreate() {
        super.onCreate();

        distancingTrackerModel = new DistancingTrackerModel(AndroidPlatform.getInstance());
    }

    public DistancingTrackerModel getDistancingTrackerModel() {
        return distancingTrackerModel;
    }
}
