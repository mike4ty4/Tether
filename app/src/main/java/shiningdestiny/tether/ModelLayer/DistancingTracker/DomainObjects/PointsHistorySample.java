package shiningdestiny.tether.ModelLayer.DistancingTracker.DomainObjects;

// A domain object representing a single absence-point accumulation history entry: basically the
// number of absence points together with a timestamp for when it was accrued.
public class PointsHistorySample {
    private long recordTimeMillis; // The UNIX timestamp when these points were recorded, in
                                   // milliseconds.
    private long numPoints; // The number of points accumulated.

    public PointsHistorySample(long recordTimeMillis, long numPoints) {
        this.recordTimeMillis = recordTimeMillis;
        this.numPoints = numPoints;
    }

    public long getRecordTimeMillis() {
        return recordTimeMillis;
    }

    public long getNumPoints() {
        return numPoints;
    }
}
