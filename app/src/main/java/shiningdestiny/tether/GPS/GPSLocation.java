package shiningdestiny.tether.GPS;

// An interface for objects holding GPS location information. To keep code simple, we make this an
// interface to defer to the platform's GPS handling facilities, instead of a data structure.
public interface GPSLocation {
    // Get the UNIX timestamp at which this location was collected, in milliseconds.
    public long getCollectionTimeMillis();

    // Get the latitude of the location, in degrees in [-90, 90], south to north.
    public double getLatitude();

    // Get the longitude of the location, in degrees in (-180, 180], west to east.
    public double getLongitude();

    // Find the distance (orthodromic, or great circle) between two geographic locations, in
    // meters.
    public double getDistanceTo(GPSLocation far);

    // Purge this location object for security/privacy purposes after it's been used. This is to
    // avoid it remaining in memory for an indefinite period until Java's garbage collector takes
    // it out.
    public void purgeInformation();
}
