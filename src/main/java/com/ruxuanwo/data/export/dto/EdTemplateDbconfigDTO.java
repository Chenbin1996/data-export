package com.ruxuanwo.data.export.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.ruxuanwo.data.export.domain.EdTemplateDbconfig;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 模板数据库配置表-DTO数据传输对象类
 * @author ruxuanwo on 2018/04/20
 * @version 3.0.0
 */
public class EdTemplateDbconfigDTO extends EdTemplateDbconfig {


    /**
     * 数据库驱动名称
     */
    private String driverClassName;

    private String templateName;

    private String typeName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String creator;

    private String description;

    private String status;

    private String appId;

    private String moduleId;

    /**
     * 模块名称
     */
    private String moduleName;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateTime() {
        return createTime;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    @Override
    public String toString() {
        return "EdTemplateDbconfigDTO{" +
                "driverClassName='" + driverClassName + '\'' +
                ", templateName='" + templateName + '\'' +
                ", typeName='" + typeName + '\'' +
                ", createTime=" + createTime +
                ", creator='" + creator + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", appId='" + appId + '\'' +
                ", moduleId='" + moduleId + '\'' +
                ", moduleName='" + moduleName + '\'' +
                '}';
    }
}
