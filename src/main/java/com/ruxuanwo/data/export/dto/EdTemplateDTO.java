package com.ruxuanwo.data.export.dto;

import com.ruxuanwo.data.export.domain.EdTemplate;

/**
 * 数据导入模板表-DTO数据传输对象类
 * @author chenbin on 2018/04/20
 * @version 3.0.0
 */
public class EdTemplateDTO extends EdTemplate {
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return super.toString() + "EdTemplateDTO{}";
    }
}
