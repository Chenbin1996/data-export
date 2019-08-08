package com.ruxuanwo.data.export.core;

import java.util.List;
import java.util.Map;

/**
 * @author ruxuanwo
 * 2019-3-6 9:16
 */
public class ExcelData {

    private String fileName;

    private String title;

    private String[] head;

    private Object[][] body;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String[] getHead() {
        return head;
    }

    public void setHead(String[] head) {
        this.head = head;
    }

    public Object[][] getBody() {
        return body;
    }

    public void setBody(Object[][] body) {
        this.body = body;
    }
}
