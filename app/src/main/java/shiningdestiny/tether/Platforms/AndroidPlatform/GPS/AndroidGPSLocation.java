package shiningdestiny.tether.Platforms.AndroidPlatform.GPS;

import shiningdestiny.tether.GPS.GPSLocation;

// The GPS location object for the Android platform.
public class AndroidGPSLocation implements GPSLocation {
    // Dummy test code for now.
    public long collectionTimeMillis;
    public double latitude, longitude;

    public AndroidGPSLocation(long collectionTimeMillis, double latitude, double longitude) {
        this.collectionTimeMillis = collectionTimeMillis;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public long getCollectionTimeMillis() {
        return collectionTimeMillis;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    @Override
    public double getDistanceTo(GPSLocation far) {
        double dLat = far.getLatitude() - latitude;
        double dLon = far.getLongitude() - longitude;

        return Math.sqrt(dLat*dLat + dLon*dLon);
    }

    @Override
    public void purgeInformation() {

    }
}
