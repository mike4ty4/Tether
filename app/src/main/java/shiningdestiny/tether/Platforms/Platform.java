package shiningdestiny.tether.Platforms;

import shiningdestiny.tether.GPS.GPSTracker;
import shiningdestiny.tether.MVCPrimitives.MVCAsyncExecutor;
import shiningdestiny.tether.Utils.Repeater;

// A factory interface for all objects that have platform-specific implementations.
public interface Platform {
    public MVCAsyncExecutor createMVCAsyncExecutor();
    public GPSTracker createGPSTracker();
    public Repeater createRepeater(Runnable repeatingCode);
}
