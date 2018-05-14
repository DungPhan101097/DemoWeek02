package com.dungit.utility.demoweek2.demo_thread_executor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dungit.utility.demoweek2.R;
import com.dungit.utility.demoweek2.demo_intent_service.FileDownload;

/**
 * Created by DUNGIT on 5/14/2018.
 */

public class DownloadFileThreadPoolActivity extends AppCompatActivity {

    private Button btnDownFile1;
    private Button btnDownFile2;
    private Button btnDownFile3;
    private TextView url1;
    private TextView url2;
    private TextView url3;
    private ProgressBar pbFile1;
    private ProgressBar pbFile2;
    private ProgressBar pbFile3;
    private TextView tvDisplayUrl1;
    private TextView tvDisplayUrl2;
    private TextView tvDisplayUrl3;

    private FileDownload[] fileDownloads = {
            new FileDownload("https://drive.google.com/uc?export=download&id=0B1rVEnAlVmVvWGxQNmxGRFBXSEU", "icon_android_java.jpg"),
            new FileDownload("https://drive.google.com/uc?export=download&id=0B1rVEnAlVmVvRndfV3RNN1pvOVU", "application_life_cycle.pdf"),
            new FileDownload("https://drive.google.com/uc?export=download&id=0B1rVEnAlVmVvRndfV3RNN1pvOVU", "database_android.pdf")};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_download_file);

        initViews();
        initData();
    }


    private void initViews() {
        btnDownFile1 = findViewById(R.id.btn_downfile_1);
        btnDownFile2 = findViewById(R.id.btn_downfile_2);
        btnDownFile3 = findViewById(R.id.btn_downfile_3);

        url1 = findViewById(R.id.tv_file_name_1);
        url2 = findViewById(R.id.tv_file_name_2);
        url3 = findViewById(R.id.tv_file_name_3);

        pbFile1 = findViewById(R.id.pb_progress_file_1);
        pbFile2 = findViewById(R.id.pb_progress_file_2);
        pbFile3 = findViewById(R.id.pb_progress_file_3);

        tvDisplayUrl1 = findViewById(R.id.tv_display_url_1);
        tvDisplayUrl2 = findViewById(R.id.tv_display_url_2);
        tvDisplayUrl3 = findViewById(R.id.tv_display_url_3);
    }

    private void initData() {
        url1.setText(fileDownloads[0].getFileName());
        url2.setText(fileDownloads[1].getFileName());
        url3.setText(fileDownloads[2].getFileName());

        btnDownFile1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDownFile1.setBackgroundResource(R.drawable.download_btn_idle);
                DownloadTask downloadTask = new DownloadTask(fileDownloads[0],
                        new DownloadDoingUpdateTask(btnDownFile1),
                        new DownloadProgressUpdateTask(pbFile1),
                        new DownloadResultUpdateTask(btnDownFile1, tvDisplayUrl1));
                DownloadManager.getDownloadManager().runDownloadFile(downloadTask);

            }
        });
        btnDownFile2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDownFile2.setBackgroundResource(R.drawable.download_btn_idle);
                DownloadTask downloadTask = new DownloadTask(fileDownloads[1],
                        new DownloadDoingUpdateTask(btnDownFile2),
                        new DownloadProgressUpdateTask(pbFile2),
                        new DownloadResultUpdateTask(btnDownFile2, tvDisplayUrl2));
                DownloadManager.getDownloadManager().runDownloadFile(downloadTask);

            }
        });
        btnDownFile3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDownFile3.setBackgroundResource(R.drawable.download_btn_idle);
                DownloadTask downloadTask = new DownloadTask(fileDownloads[2],
                        new DownloadDoingUpdateTask(btnDownFile3),
                        new DownloadProgressUpdateTask(pbFile3),
                        new DownloadResultUpdateTask(btnDownFile3, tvDisplayUrl3));
                DownloadManager.getDownloadManager().runDownloadFile(downloadTask);

            }
        });

    }
}
