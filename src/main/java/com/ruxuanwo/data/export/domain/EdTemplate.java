package com.ruxuanwo.data.export.domain;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
/**
 * @author Chenbin
 */
@Table(name = "ed_template")
public class EdTemplate implements Serializable {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 1.正常，2.禁用，3.删除，4.修改未完成
     */
    private Integer status;

    /**
     * 描述
     */
    private String description;

    /**
     * 平台唯一标识
     */
    private String appId;

    /**
     * 模块唯一标识
     */
    private String moduleId;
    /**
     * 模块名称
     */
    private String moduleName;

    private static final long serialVersionUID = 1L;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取模板名称
     *
     * @return name - 模板名称
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public String getTemplateName() {
        return templateName;
    }

    /**
     * 设置模板名称
     *
     * @param templateName 模板名称
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取创建人
     *
     * @return creator - 创建人
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置创建人
     *
     * @param creator 创建人
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * 获取1.正常，2.禁用，3.删除，4.修改未完成
     *
     * @return status - 1.正常，2.禁用，3.删除，4.修改未完成
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置1.正常，2.禁用，3.删除，4.修改未完成
     *
     * @param status 1.正常，2.禁用，3.删除，4.修改未完成
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取描述
     *
     * @return description - 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述
     *
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
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
        return "EdTemplate{" +
                "id='" + id + '\'' +
                ", templateName='" + templateName + '\'' +
                ", createTime=" + createTime +
                ", creator='" + creator + '\'' +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", appId='" + appId + '\'' +
                ", moduleId='" + moduleId + '\'' +
                ", moduleName='" + moduleName + '\'' +
                '}';
    }
}