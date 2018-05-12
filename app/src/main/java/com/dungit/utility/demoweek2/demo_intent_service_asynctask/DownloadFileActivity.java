package com.dungit.utility.demoweek2.demo_intent_service_asynctask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.NetworkOnMainThreadException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dungit.utility.demoweek2.R;

import org.w3c.dom.Text;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

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

    private FileDownload[] fileDownloads = {
            new FileDownload("https://drive.google.com/uc?export=download&id=0B1rVEnAlVmVvOFdfQk9Lb0pwMWs", "head_first_java.pdf"),
            new FileDownload("https://drive.google.com/uc?export=download&id=0B1rVEnAlVmVvUzhnNVNTLVFkWEE", "pro_android.pdf"),
            new FileDownload("https://drive.google.com/uc?export=download&id=0B1rVEnAlVmVvZXZCUTFjaTAwTms", "learn_opengl.pdf") };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_download_file);

        btnDownFile1 = findViewById(R.id.btn_downfile_1);
        btnDownFile1 = findViewById(R.id.btn_downfile_2);
        btnDownFile1 = findViewById(R.id.btn_downfile_3);

        url1 = findViewById(R.id.tv_file_name_1);
        url1 = findViewById(R.id.tv_file_name_2);
        url1 = findViewById(R.id.tv_file_name_3);

        pbFile1 = findViewById(R.id.pb_progress_file_1);
        pbFile1 = findViewById(R.id.pb_progress_file_2);
        pbFile1 = findViewById(R.id.pb_progress_file_3);

        initData();

    }

    private void initData() {
        url1.setText(fileDownloads[0].getFileName());
        url2.setText(fileDownloads[1].getFileName());
        url3.setText(fileDownloads[2].getFileName());

        btnDownFile1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DownloadFileActivity.this, DownloadFileService.class);
                intent.putExtra(DownloadFileService.FILE, fileDownloads[0]);
                startService(intent);
            }
        });
        btnDownFile2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DownloadFileActivity.this, DownloadFileService.class);
                intent.putExtra(DownloadFileService.FILE, fileDownloads[1]);
                startService(intent);
            }
        });
        btnDownFile2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DownloadFileActivity.this, DownloadFileService.class);
                intent.putExtra(DownloadFileService.FILE, fileDownloads[2]);
                startService(intent);
            }
        });

    }
}
