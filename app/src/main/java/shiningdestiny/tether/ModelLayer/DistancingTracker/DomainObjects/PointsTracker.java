package shiningdestiny.tether.ModelLayer.DistancingTracker.DomainObjects;

import shiningdestiny.tether.GPS.GPSLocation;

// A domain object for actually computing and tracking points based on GPS information.
// This takes a feed of GPS location inputs and generates a point history by estimating the travel
// between each point and its distance from the set "home base" point.
public class PointsTracker {
    private boolean isTrackingActive; // Whether or not we are currently tracking actively.
    private GPSLocation homeBaseLocation; // The reference location (usually the user's home) to
                                          // compute absement from.
    private double homeRadius; // The radius of the "home" location in meters.

    private long trackBeginMillis; // The UNIX timestamp when we last began tracking.
    private PointsHistory accumulatedHistory; // The accumulated history so far.
    private GPSLocation lastTrackLocation; // The GPS location of the last history point recorded.

    public PointsTracker(GPSLocation homeBaseLocation, double homeRadius) {
        isTrackingActive = false;
        this.homeBaseLocation = homeBaseLocation;
        this.homeRadius = homeRadius;
        trackBeginMillis = -1;
        accumulatedHistory = new PointsHistory();
        lastTrackLocation = null;
    }

    // Start a new tracking session.
    public void startTracking() {
        if(!isTrackingActive) {
            isTrackingActive = true;
            trackBeginMillis = System.currentTimeMillis();
        }
    }

    // Stop the current tracking session.
    public void stopTracking() {
        if(isTrackingActive) {
            isTrackingActive = false;
            trackBeginMillis = -1;
            accumulatedHistory.clear();
        }
    }

    // Inquire if we are tracking now.
    public boolean isTrackingActive() {
        return isTrackingActive;
    }

    // Stimulate the tracker with a single new GPS update.
    public void logNewGpsUpdate(GPSLocation gpsLocation) {
        if(lastTrackLocation != null) {
            // Compute the distance and time between the previous and current location.
            double distance = lastTrackLocation.getDistanceTo(gpsLocation);
            long timeMillis = gpsLocation.getCollectionTimeMillis() -
                    lastTrackLocation.getCollectionTimeMillis();

            // The absement is the product of these two values. The units are meter-kiloseconds or
            // kilometer-seconds; both are the same thing.
            double timeKs = (double)timeMillis / 1_000_000.0;
            double dblNumPoints = distance * timeKs;

            long numPoints = Math.round(dblNumPoints); // This is the number of points gained in
                                                       // this single move.

            // Save the new tally in the history.
            accumulatedHistory.insertNewEntry(gpsLocation.getCollectionTimeMillis(), numPoints);

            // Prepare for the next movement step.
            lastTrackLocation.purgeInformation();
            lastTrackLocation = gpsLocation;
        } else {
            // Record zero points and initialize with this location.
            accumulatedHistory.insertNewEntry(gpsLocation.getCollectionTimeMillis(), 0);
            lastTrackLocation = gpsLocation;
        }
    }

    // Get the total number of points recorded so far.
    public long getTotalAbsencePoints() {
        long totalPts = 0;
        for(int i = 0; i < accumulatedHistory.getNumSamples(); ++i) {
            totalPts += accumulatedHistory.getSample(i).getNumPoints();
        }

        return totalPts;
    }
}
