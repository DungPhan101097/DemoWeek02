package com.dungit.utility.demoweek2.demo_thread_executor;

/**
 * Created by DUNGIT on 5/14/2018.
 */
import android.os.Environment;

import com.dungit.utility.demoweek2.demo_intent_service.*;

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

public class DownloadTask implements Runnable{
    private FileDownload fileDownload;
    private DownloadResultUpdateTask resultUpdateTask;
    private DownloadDoingUpdateTask doingUpdateTask;
    private DownloadProgressUpdateTask progressUpdateTask;
    private File myDownloadFile;

    public DownloadTask(FileDownload fileDownload, DownloadDoingUpdateTask doingUpdateTask,
                        DownloadProgressUpdateTask progressUpdateTask,
                        DownloadResultUpdateTask resultUpdateTask){
        this.fileDownload = fileDownload;
        this.doingUpdateTask = doingUpdateTask;
        this.progressUpdateTask = progressUpdateTask;
        this.resultUpdateTask = resultUpdateTask;
    }

    @Override
    public void run() {
        //doing
        DownloadManager.getDownloadManager().getMainThreadExecutor()
                .execute(doingUpdateTask);

        String msg;
        if(downloadFile()){
            msg = "Your file in:  "+ myDownloadFile.getAbsolutePath();
        }else{
            msg = "Failed to download the file ";
        }

        //finish
        resultUpdateTask.setUrlDownloaded(msg);
        DownloadManager.getDownloadManager().getMainThreadExecutor()
                .execute(resultUpdateTask);
    }

    public boolean downloadFile(){
        FileDownload myFile = fileDownload;

        URLConnection connection = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;

        byte[] buffer = new byte[1024];
        myDownloadFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                myFile.getFileName());
        try {
            connection = new URL(myFile.getUrl()).openConnection();
            int fileLen = connection.getContentLength();

            outputStream = new BufferedOutputStream(new FileOutputStream(myDownloadFile));
            inputStream = connection.getInputStream();

            int numRead = -1;
            while ((numRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, numRead);

                progressUpdateTask.setProgress(numRead);
                progressUpdateTask.setTotalLength(fileLen);
                DownloadManager.getDownloadManager().getMainThreadExecutor()
                        .execute(progressUpdateTask);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
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
        return true;
    }
}