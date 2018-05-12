package com.dungit.utility.demoweek2.demo_intent_service_asynctask;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
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

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public DownloadFileService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (intent != null) {
            FileDownload myFile = (FileDownload) intent.getSerializableExtra(FILE);
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
        }
    }

}
