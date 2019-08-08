package com.ruxuanwo.data.export.core;

/**
 * 导入器导入时所需参数对象
 * 可自行继承扩展
 */
public class Data {

    /**
     * 模板id
     */
    private String templateId;

    /**
     * 日志id
     */
    private String logId;

    public Data() {
    }

    public Data(String templateId, String logId) {
        this.templateId = templateId;
        this.logId = logId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }
}
