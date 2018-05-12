package com.dungit.utility.demoweek2.demo_intent_service_asynctask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dungit.utility.demoweek2.R;

/**
 * Created by nahuy on 5/12/18.
 */

public class DownloadFileActivity extends AppCompatActivity {

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

    private ResponseReceiverDoing responseReceiverDoing;
    private ResponseReceiverComplete responseReceiverComplete;
    private ResponseReceiverUpdateProgress responseReceiverUpdateProgress;

    private FileDownload[] fileDownloads = {
            new FileDownload("https://drive.google.com/uc?export=download&id=0B1rVEnAlVmVvWGxQNmxGRFBXSEU", "icon_android_java.jpg"),
            new FileDownload("https://drive.google.com/uc?export=download&id=0B1rVEnAlVmVvM0tmTlozQzh6N1k", "application_life_cycle.pdf"),
            new FileDownload("https://drive.google.com/uc?export=download&id=0B1rVEnAlVmVvRndfV3RNN1pvOVU", "database_android.pdf") };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_download_file);

        initViews();
        initData();
        registerMyBroadcastReceiver();

    }

    private void registerMyBroadcastReceiver() {
        IntentFilter filterDoing = new IntentFilter(ResponseReceiverDoing.ACTION_RESP_DOING);
        filterDoing.addCategory(Intent.CATEGORY_DEFAULT);
        responseReceiverDoing = new ResponseReceiverDoing();
        registerReceiver(responseReceiverDoing, filterDoing);

        IntentFilter filterComplete = new IntentFilter(ResponseReceiverComplete.ACTION_RESP_COMPLETE);
        filterComplete.addCategory(Intent.CATEGORY_DEFAULT);
        responseReceiverComplete = new ResponseReceiverComplete();
        registerReceiver(responseReceiverComplete, filterComplete);

        IntentFilter filterUpdate = new IntentFilter(ResponseReceiverUpdateProgress.ACTION_RESP_UPDATE);
        filterUpdate.addCategory(Intent.CATEGORY_DEFAULT);
        responseReceiverUpdateProgress = new ResponseReceiverUpdateProgress();
        registerReceiver(responseReceiverUpdateProgress, filterUpdate);
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
                Intent intent = new Intent(DownloadFileActivity.this, DownloadFileService.class);
                intent.putExtra(DownloadFileService.FILE, fileDownloads[0]);
                startService(intent);
            }
        });
        btnDownFile2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDownFile2.setBackgroundResource(R.drawable.download_btn_idle);
                Intent intent = new Intent(DownloadFileActivity.this, DownloadFileService.class);
                intent.putExtra(DownloadFileService.FILE, fileDownloads[1]);
                startService(intent);
            }
        });
        btnDownFile3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDownFile3.setBackgroundResource(R.drawable.download_btn_idle);
                Intent intent = new Intent(DownloadFileActivity.this, DownloadFileService.class);
                intent.putExtra(DownloadFileService.FILE, fileDownloads[2]);
                startService(intent);
            }
        });

    }

    public class ResponseReceiverDoing extends BroadcastReceiver {
        public static final String ACTION_RESP_DOING = "action_resp_doing";

        @Override
        public void onReceive(Context context, Intent intent) {
            FileDownload curFile = (FileDownload)intent.getSerializableExtra(DownloadFileService.BTN_WAIT_TIME);
            int fileLen = intent.getIntExtra(DownloadFileService.FILE_LEN, 0);

            switch (curFile.getFileName()){
                case "icon_android_java.jpg":
                    pbFile1.setMax(fileLen);
                    btnDownFile1.setBackgroundResource(R.drawable.download_btn_doing);
                    break;

                case "application_life_cycle.pdf":
                    pbFile2.setMax(fileLen);
                    pbFile2.setProgress(4000);
                    btnDownFile2.setBackgroundResource(R.drawable.download_btn_doing);
                    break;

                case "database_android.pdf":
                    pbFile3.setMax(fileLen);
                    btnDownFile3.setBackgroundResource(R.drawable.download_btn_doing);
                    break;
            }

        }
    }

    public class ResponseReceiverComplete extends BroadcastReceiver {
        public static final String ACTION_RESP_COMPLETE = "action_resp_complete";

        @Override
        public void onReceive(Context context, Intent intent) {
            FileDownload curFile = (FileDownload)intent.getSerializableExtra(DownloadFileService.BTN_COMPLETE_TIME);
            String urlDownloaded = intent.getStringExtra(DownloadFileService.URL_DOWNLOADED);
            switch (curFile.getFileName()){
                case "icon_android_java.jpg":
                    btnDownFile1.setBackgroundResource(R.drawable.download_btn_finish);
                    tvDisplayUrl1.setText("Your file in: " + urlDownloaded);
                    break;

                case "application_life_cycle.pdf":
                    btnDownFile2.setBackgroundResource(R.drawable.download_btn_finish);
                    tvDisplayUrl2.setText("Your file in: " + urlDownloaded);
                    break;

                case "database_android.pdf":
                    btnDownFile3.setBackgroundResource(R.drawable.download_btn_finish);
                    tvDisplayUrl3.setText("Your file in: " + urlDownloaded);
                    break;
            }

        }
    }

    public class ResponseReceiverUpdateProgress extends BroadcastReceiver {
        public static final String ACTION_RESP_UPDATE = "action_resp_update";

        @Override
        public void onReceive(Context context, Intent intent) {
            FileDownload curFile = (FileDownload)intent.getSerializableExtra(DownloadFileService.FILE_RES);
            int numRead = intent.getIntExtra(DownloadFileService.UPDATE_PROGRESS, 0);
            switch (curFile.getFileName()){
                case "icon_android_java.jpg":
                   pbFile1.setProgress(pbFile1.getProgress() + numRead);
                    break;

                case "application_life_cycle.pdf":
                    pbFile2.setProgress(pbFile2.getProgress() + numRead);
                    break;

                case "database_android.pdf":
                    pbFile3.setProgress(pbFile3.getProgress() + numRead);
                    break;
            }

        }
    }
}
