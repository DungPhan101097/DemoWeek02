package com.dungit.utility.demoweek2.demo_thread_pool_executor;

import android.widget.ProgressBar;

/**
 * Created by DUNGIT on 5/14/2018.
 */

public class DownloadProgressUpdateTask implements Runnable {
    private ProgressBar curProgress;
    private int progress;
    private int totalLength;
public int id;

    public DownloadProgressUpdateTask(ProgressBar curProgress) {
        this.curProgress = curProgress;
    }

    public void setProgress(int aprogress) {
        this.progress = progress;
        //curProgress.setProgress((curProgress.getProgress() + aprogress));

    }

    public void setTotalLength(int totalLength) {
        this.totalLength = totalLength;
        curProgress.setMax(totalLength);
    }

    @Override
    public void run() {
       // Log.e("Truoc", "Pg " + (id++) + "-" + progress);
        curProgress.setProgress((curProgress.getProgress() + progress));
    }

    private int updateId = 0;
    public DownloadProgressUpdateTask setId(int id) {
        this.id = id;
        this.updateId = id;
        return this;
    }
}
