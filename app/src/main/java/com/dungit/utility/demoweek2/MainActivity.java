package com.dungit.utility.demoweek2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dungit.utility.demoweek2.demo_asyntask.RemoveDuplicateActivity;
import com.dungit.utility.demoweek2.demo_coodinator.CoodinatorActivity;
import com.dungit.utility.demoweek2.demo_intent_service.DownloadFileActivity;
import com.dungit.utility.demoweek2.demo_view_stub.ShowImgActivity;

import java.io.BufferedReader;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 1;
    private Button btnStartViewStubAct;
    private Button btnStartCoodinatorAct;
    private Button btnStartShowAlbumAct;
    private Button btnDownloadFileAct;
    private Button btnStartAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "Please provide permission!", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
            }
        } else {
            initViews();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initViews();
                } else {
                    Toast.makeText(this, "Application can't access any data!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }

        }
    }

    public void initViews(){
        btnStartCoodinatorAct = findViewById(R.id.btn_start_coodinator_act);
        btnStartViewStubAct = findViewById(R.id.btn_start_view_stub_act);
        btnStartShowAlbumAct = findViewById(R.id.btn_start_fragment_act);
        btnDownloadFileAct = findViewById(R.id.btn_download_file);
        btnStartAsyncTask = findViewById(R.id.btn_async_task);

        btnStartViewStubAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowImgActivity.class);
                startActivity(intent);
            }
        });

        btnStartCoodinatorAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CoodinatorActivity.class);
                startActivity(intent);
            }
        });

        btnDownloadFileAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DownloadFileActivity.class);
                startActivity(intent);
            }
        });

        btnStartAsyncTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RemoveDuplicateActivity.class);
                startActivity(intent);
            }
        });
    }
}
