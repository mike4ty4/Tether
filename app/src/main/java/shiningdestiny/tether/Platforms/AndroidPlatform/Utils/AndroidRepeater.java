package shiningdestiny.tether.Platforms.AndroidPlatform.Utils;

import android.os.Handler;

import shiningdestiny.tether.Utils.Repeater;

// The repeater implementation for the Android platform.
public class AndroidRepeater implements Repeater {
    private Handler handler;
    private Runnable repeatingTask;
    private Runnable dressedTask;

    private enum TaskState {
        TASK_STOPPED, TASK_RUNNING, TASK_PAUSED
    }

    private TaskState taskState;

    // Construct using a Runnable to specify the repeating task.
    public AndroidRepeater(Runnable runnable) {
        this.handler = null;
        this.repeatingTask = runnable;
        this.taskState = TaskState.TASK_STOPPED;
    }

    // Indicate if the repeating task is running or not.
    @Override
    public boolean isRunning() {
        return (taskState == TaskState.TASK_RUNNING);
    }

    // Start the repeating task.
    @Override
    public void startRepeating(final long repeatIntervalMillis) {
        if(taskState == TaskState.TASK_STOPPED) { // safety
            this.taskState = TaskState.TASK_RUNNING;

            // Create a new handler instance.
            this.handler = new Handler();

            // "Dress" the task with code to repeat it at the set interval.
            this.dressedTask = new Runnable() {
                @Override
                public void run() {
                    repeatingTask.run();
                    if(handler != null) {
                        handler.postDelayed(this, repeatIntervalMillis);
                    }
                }
            };

            // Commit to the system to run the task.
            handler.post(this.dressedTask);
        }
    }

    // Stop the repeating task.
    @Override
    public void stopRepeating() {
        if(this.taskState != TaskState.TASK_STOPPED) { // safety
            this.handler.removeCallbacks(this.dressedTask);
            this.handler = null;

            this.taskState = TaskState.TASK_STOPPED;
        }
    }
}
