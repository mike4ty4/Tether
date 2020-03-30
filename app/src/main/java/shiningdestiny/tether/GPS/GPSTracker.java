package shiningdestiny.tether.GPS;

// An interface for the device GPS tracking facilities, which are platform-specific.
public interface GPSTracker {
    // Get the most recently-available geographic location of this device.
    public GPSLocation getMostRecentDeviceLocation();
}
