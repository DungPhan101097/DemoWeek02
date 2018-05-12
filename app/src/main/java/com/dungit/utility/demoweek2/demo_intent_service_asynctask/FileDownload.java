package com.dungit.utility.demoweek2.demo_intent_service_asynctask;

import java.io.Serializable;

/**
 * Created by nahuy on 5/12/18.
 */

public class FileDownload implements Serializable {
    public FileDownload(String url, String fileName) {
        this.url = url;
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    private String url;
    private String fileName;

}
