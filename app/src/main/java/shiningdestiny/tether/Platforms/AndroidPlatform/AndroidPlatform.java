package shiningdestiny.tether.Platforms.AndroidPlatform;

import shiningdestiny.tether.GPS.GPSTracker;
import shiningdestiny.tether.MVCPrimitives.MVCAsyncExecutor;
import shiningdestiny.tether.Platforms.AndroidPlatform.GPS.AndroidGPSTracker;
import shiningdestiny.tether.Platforms.AndroidPlatform.MVCPrimitives.AndroidMVCAsyncExecutor;
import shiningdestiny.tether.Platforms.Platform;
import shiningdestiny.tether.Utils.Repeater;

// The class for all Android platform-specific functionality at the model layer level.
public class AndroidPlatform implements Platform {
    // Singleton implementation.
    private static AndroidPlatform _inst = null;
    public static AndroidPlatform getInstance() {
        if(_inst == null) {
            _inst = new AndroidPlatform();
        }

        return _inst;
    }

    // Methods.
    @Override
    public MVCAsyncExecutor createMVCAsyncExecutor() {
        return new AndroidMVCAsyncExecutor();
    }

    @Override
    public GPSTracker createGPSTracker() {
        return new AndroidGPSTracker();
    }

    @Override
    public Repeater createRepeater(Runnable repeatingCode) {
        return null;
    }
}
