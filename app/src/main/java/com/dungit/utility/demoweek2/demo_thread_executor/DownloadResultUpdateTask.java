package com.dungit.utility.demoweek2.demo_thread_executor;

import android.widget.Button;
import android.widget.TextView;

import com.dungit.utility.demoweek2.R;

import org.w3c.dom.Text;

/**
 * Created by DUNGIT on 5/14/2018.
 */

public class DownloadResultUpdateTask implements Runnable{
    private String urlDownloaded;
    private Button curBtn;
    private TextView curTv;

    public DownloadResultUpdateTask(Button curBtn, TextView curTv) {
        this.curBtn = curBtn;
        this.curTv = curTv;
    }

    public void setUrlDownloaded(String urlDownloaded){
        this.urlDownloaded = urlDownloaded;
    }

    @Override
    public void run() {
        curBtn.setBackgroundResource(R.drawable.download_btn_finish);
        curTv.setText(urlDownloaded);
    }

}
