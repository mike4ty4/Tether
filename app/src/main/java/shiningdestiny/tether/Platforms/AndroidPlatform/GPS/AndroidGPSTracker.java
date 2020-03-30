package shiningdestiny.tether.Platforms.AndroidPlatform.GPS;

import shiningdestiny.tether.GPS.GPSLocation;
import shiningdestiny.tether.GPS.GPSTracker;

// The GPS tracker for the Android platform. Note that because there are various security-related
// issues here, the behavior of this tracker may not necessarily be what one may expect - e.g. on
// Android Oreo and above, it may not give a real-time stream of the device's location under all
// circumstances.
public class AndroidGPSTracker implements GPSTracker {
    @Override
    public GPSLocation getMostRecentDeviceLocation() {
        return new AndroidGPSLocation(System.currentTimeMillis(), 0, 0);
    }
}
