package com.ruxuanwo.data.export.dto;

import com.ruxuanwo.data.export.domain.EdLog;

/**
 * 数据导入日志表-DTO数据传输对象类
 * @author chenbin on 2018/05/08
 * @version 3.0.0
 */
public class EdLogDTO extends EdLog {
    /**
     * 模板名称
     */
    private String templateName;
    /**
     * 状态名称
     */
    private String stateName;

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    @Override
    public String toString() {
        return "EdLogDTO{" +
                "templateName='" + templateName + '\'' +
                ", stateName='" + stateName + '\'' +
                '}';
    }
}
