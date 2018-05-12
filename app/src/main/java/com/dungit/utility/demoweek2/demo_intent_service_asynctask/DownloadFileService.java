package com.dungit.utility.demoweek2.demo_intent_service_asynctask;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.Nullable;

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

/**
 * Created by nahuy on 5/12/18.
 */

public class DownloadFileService extends IntentService {

    public static final String FILE = "file";
    public static final String BTN_WAIT_TIME = "btn_wait_time";
    public static final String BTN_COMPLETE_TIME = "btn_complete_time";
    public static final String UPDATE_PROGRESS = "update_progress";


    public DownloadFileService() {
        super("DownloadFileService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            FileDownload myFile = (FileDownload) intent.getSerializableExtra(FILE);

            // Doing
            Intent broadcastIntentDoing = new Intent();
            broadcastIntentDoing.setAction(DownloadFileActivity.ResponseReceiverDoing.ACTION_RESP_DOING);
            broadcastIntentDoing.addCategory(Intent.CATEGORY_DEFAULT);
            broadcastIntentDoing.putExtra(BTN_WAIT_TIME, myFile);
            sendBroadcast(broadcastIntentDoing);

            URLConnection connection = null;
            InputStream inputStream = null;
            OutputStream outputStream = null;

            byte[] buffer = new byte[1024];
            File myDownloadFile = new File (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    myFile.getFileName());
            try {
                connection = new URL(myFile.getUrl()).openConnection();
                outputStream = new BufferedOutputStream(new FileOutputStream(myDownloadFile));
                inputStream = connection.getInputStream();

                int numRead = -1;
                while((numRead = inputStream.read(buffer)) != -1){
                    outputStream.write(buffer, 0, numRead);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
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

            // Complete
            Intent broadcastIntentFinish = new Intent();
            broadcastIntentFinish.setAction(DownloadFileActivity.ResponseReceiverComplete.ACTION_RESP_COMPLETE);
            broadcastIntentFinish.addCategory(Intent.CATEGORY_DEFAULT);
            broadcastIntentFinish.putExtra(BTN_COMPLETE_TIME, myFile);
            sendBroadcast(broadcastIntentFinish);

        }
    }

}
