package com.dungit.utility.demoweek2.demo_thread_handler;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dungit.utility.demoweek2.R;
import com.dungit.utility.demoweek2.demo_intent_service.DownloadFileActivity;
import com.dungit.utility.demoweek2.demo_intent_service.DownloadFileService;
import com.dungit.utility.demoweek2.demo_intent_service.FileDownload;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

public class DownloadFileConsumerActivity extends AppCompatActivity {

    public static final int THREAD_1 = 1;
    public static final int THREAD_2 = 2;
    public static final int THREAD_3 = 3;
    private static final int FINISH = 5;
    private static final int START_DOING = 6;
    private static final int UPDATE_MAX_PROGRESS = 7;
    private static final int UPDATE_PROGRESS = 8;
    private static final String FILE_PATH = "file_path";

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

    private BackgroundThread bgThreadFile1, bgThreadFile2, bgThreadFile3;

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
        TextView tvIntro = findViewById(R.id.tv_intro_activity);
        tvIntro.setText(R.string.intro_thread_handler);

        url1.setText(fileDownloads[0].getFileName());
        url2.setText(fileDownloads[1].getFileName());
        url3.setText(fileDownloads[2].getFileName());


        btnDownFile1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDownFile1.setBackgroundResource(R.drawable.download_btn_idle);

                bgThreadFile1 = new BackgroundThread(fileDownloads[0], THREAD_1);
                bgThreadFile1.start();
            }
        });
        btnDownFile2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDownFile2.setBackgroundResource(R.drawable.download_btn_idle);

                bgThreadFile2 = new BackgroundThread(fileDownloads[1], THREAD_2);
                bgThreadFile2.start();


            }
        });
        btnDownFile3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDownFile3.setBackgroundResource(R.drawable.download_btn_idle);

                bgThreadFile3 = new BackgroundThread(fileDownloads[2], THREAD_3);
                bgThreadFile3.start();

            }
        });

    }


    private final Handler uiHandlerFile = new Handler() {
        private Button getButtonById(int idThread) {
            switch (idThread) {
                case THREAD_1:
                    return btnDownFile1;

                case THREAD_2:
                    return btnDownFile2;

                case THREAD_3:
                    return btnDownFile3;
            }
            return null;
        }

        private ProgressBar getProgressBarById(int idThread) {
            switch (idThread) {
                case THREAD_1:
                    return pbFile1;

                case THREAD_2:
                    return pbFile2;

                case THREAD_3:
                    return pbFile3;
            }
            return null;
        }

        private TextView getTextViewById(int idThread) {
            switch (idThread) {
                case THREAD_1:
                    return tvDisplayUrl1;

                case THREAD_2:
                    return tvDisplayUrl2;

                case THREAD_3:
                    return tvDisplayUrl3;
            }
            return null;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case START_DOING:
                    getButtonById(msg.arg1).setBackgroundResource(R.drawable.download_btn_doing);
                    break;

                case UPDATE_MAX_PROGRESS:
                    getProgressBarById(msg.arg1).setMax(msg.arg2);
                    break;

                case UPDATE_PROGRESS:
                    getProgressBarById(msg.arg1).setProgress(
                            getProgressBarById(msg.arg1).getProgress() + msg.arg2);
                    break;

                case FINISH:
                    getButtonById(msg.arg1).setBackgroundResource(R.drawable.download_btn_finish);
                    getTextViewById(msg.arg1).setText("Your file in: " +
                            msg.getData().getString(FILE_PATH));
                    break;
            }
        }
    };


    class BackgroundThread extends Thread {
        private FileDownload downloadFile;
        private int idThread;
        private File myDownloadFile;

        BackgroundThread(FileDownload downloadFile, int idThread) {
            this.downloadFile = downloadFile;
            this.idThread = idThread;
        }

        @Override
        public void run() {
            Message msg = uiHandlerFile.obtainMessage(START_DOING);
            msg.arg1 = idThread;
            uiHandlerFile.sendMessage(msg);

            downloadFile();

            msg = uiHandlerFile.obtainMessage(FINISH);
            msg.arg1 = idThread;
            Bundle bundle = new Bundle();
            bundle.putString(FILE_PATH, myDownloadFile.getAbsolutePath());
            msg.setData(bundle);
            uiHandlerFile.sendMessage(msg);

        }

        private void downloadFile() {
            FileDownload myFile = downloadFile;

            URLConnection connection = null;
            InputStream inputStream = null;
            OutputStream outputStream = null;

            byte[] buffer = new byte[1024];
            myDownloadFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    myFile.getFileName());
            try {
                connection = new URL(myFile.getUrl()).openConnection();

                int fileLen = connection.getContentLength();
                Message msg = uiHandlerFile.obtainMessage(UPDATE_MAX_PROGRESS);
                msg.arg1 = idThread;
                msg.arg2 = fileLen;
                uiHandlerFile.sendMessage(msg);

                outputStream = new BufferedOutputStream(new FileOutputStream(myDownloadFile));
                inputStream = connection.getInputStream();

                int numRead = -1;
                while ((numRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, numRead);

                    msg = uiHandlerFile.obtainMessage(UPDATE_PROGRESS);
                    msg.arg1 = idThread;
                    msg.arg2 = numRead;
                    uiHandlerFile.sendMessage(msg);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


        }
    }


}
