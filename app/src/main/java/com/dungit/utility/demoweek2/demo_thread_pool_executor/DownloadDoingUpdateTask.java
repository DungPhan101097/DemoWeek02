package com.dungit.utility.demoweek2.demo_thread_pool_executor;

import android.widget.Button;

import com.dungit.utility.demoweek2.R;

/**
 * Created by DUNGIT on 5/14/2018.
 */

public class DownloadDoingUpdateTask implements Runnable {
    private Button cutBtn;

    public DownloadDoingUpdateTask(Button cutBtn) {
        this.cutBtn = cutBtn;
    }

    @Override
    public void run() {
        cutBtn.setBackgroundResource(R.drawable.download_btn_doing);
    }
}
