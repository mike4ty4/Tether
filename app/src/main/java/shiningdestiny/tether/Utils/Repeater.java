package shiningdestiny.tether.Utils;

// An interface for an object which executes a piece of code at some regular interval.
public interface Repeater {
    public boolean isRunning();

    public void startRepeating(long repeatIntervalMillis);
    public void stopRepeating();
}
