package shiningdestiny.tether.ModelLayer.DistancingTracker.DomainObjects;

import shiningdestiny.tether.GPS.GPSLocation;
import shiningdestiny.tether.GPS.GPSTracker;
import shiningdestiny.tether.Platforms.Platform;
import shiningdestiny.tether.Utils.Repeater;

// A domain object for performing the GPS monitoring. This fits on top of a PointsTracker and
// injects it with entries for the device's current whereabouts.
public class GPSMonitor {
    private GPSTracker gpsTracker; // The underlying GPS platform object.

    private PointsTracker pointsTracker; // The fitted points tracker.
    private long pollIntervalMillis; // The interval, in milliseconds, at which we poll the GPS
                                     // system.

    private GPSLocationUpdateListener gpsLocationUpdateListener;

    private Repeater pollRepeater;

    public GPSMonitor(Platform platform, final PointsTracker pointsTracker, long pollIntervalMillis) {
        gpsTracker = platform.createGPSTracker();
        this.pointsTracker = pointsTracker;
        this.pollIntervalMillis = pollIntervalMillis;
        this.pollRepeater = platform.createRepeater(new Runnable() {
            @Override
            public void run() {
                GPSLocation gpsLocation = gpsTracker.getMostRecentDeviceLocation();
                pointsTracker.logNewGpsUpdate(gpsLocation);

                if(gpsLocationUpdateListener != null) {
                    gpsLocationUpdateListener.onLocationUpdate();
                }
            }
        });
    }

    public void setGpsLocationUpdateListener(GPSLocationUpdateListener gpsLocationUpdateListener) {
        this.gpsLocationUpdateListener = gpsLocationUpdateListener;
    }

    public void startTracking() {
        pointsTracker.startTracking();
        pollRepeater.startRepeating(pollIntervalMillis);
    }

    public void stopTracking() {
        pointsTracker.stopTracking();
    }
}
