package shiningdestiny.tether.ModelLayer.DistancingTracker.DomainObjects;

import java.util.ArrayList;
import java.util.List;

// A domain object storing a history of absence points accumulated over a period of time.
public class PointsHistory {
    private List<PointsHistorySample> historyRecordTape = new ArrayList<>();

    public int getNumSamples() {
        return historyRecordTape.size();
    }

    // Get an entry from the history.
    public PointsHistorySample getSample(int sampleIndex) {
        return historyRecordTape.get(sampleIndex);
    }

    // Insert a single entry into this history.
    public PointsHistory insertNewEntry(long recordTimeMillis, long numPoints) {
        historyRecordTape.add(new PointsHistorySample(recordTimeMillis, numPoints));
        return this;
    }

    // Concatenate another history list to the end of this one.
    public PointsHistory concatenate(PointsHistory rhs) {
        historyRecordTape.addAll(rhs.historyRecordTape);
        return this;
    }

    // Wipe this history.
    public void clear() {
        historyRecordTape.clear();
    }
}
