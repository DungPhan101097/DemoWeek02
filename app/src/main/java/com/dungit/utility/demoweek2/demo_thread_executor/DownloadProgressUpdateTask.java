package com.dungit.utility.demoweek2.demo_thread_executor;

import android.widget.Button;
import android.widget.ProgressBar;

/**
 * Created by DUNGIT on 5/14/2018.
 */

public class DownloadProgressUpdateTask implements Runnable {
    private ProgressBar curProgress;
    private int progress;
    private int totalLength;

    public DownloadProgressUpdateTask(ProgressBar curProgress) {
        this.curProgress = curProgress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void setTotalLength(int totalLength) {
        this.totalLength = totalLength;
    }

    @Override
    public void run() {
        if (curProgress.getProgress() == 0) {
            curProgress.setMax(totalLength);
        }
        curProgress.setProgress(curProgress.getProgress() + progress);
    }
}
