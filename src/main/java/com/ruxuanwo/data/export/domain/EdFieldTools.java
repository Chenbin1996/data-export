package com.ruxuanwo.data.export.domain;

import javax.persistence.*;
import java.io.Serializable;
/**
 * @author Chenbin
 */
@Table(name = "ed_field_tools")
public class EdFieldTools implements Serializable {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 工具id
     */
    @Column(name = "tool_id")
    private String toolId;

    /**
     * 校验或转换失败类型:1.警告,2.错误
     */
    private Integer type;

    /**
     * 模板字段id
     */
    @Column(name = "field_id")
    private String fieldId;

    @Column(name = "other")
    private String other;

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
     * 获取工具id
     *
     * @return tool_id - 工具id
     */
    public String getToolId() {
        return toolId;
    }

    /**
     * 设置工具id
     *
     * @param toolId 工具id
     */
    public void setToolId(String toolId) {
        this.toolId = toolId;
    }

    /**
     * 获取校验或转换失败类型:1.警告,2.错误
     *
     * @return type - 校验或转换失败类型:1.警告,2.错误
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置校验或转换失败类型:1.警告,2.错误
     *
     * @param type 校验或转换失败类型:1.警告,2.错误
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取模板字段id
     *
     * @return field_id - 模板字段id
     */
    public String getFieldId() {
        return fieldId;
    }

    /**
     * 设置模板字段id
     *
     * @param fieldId 模板字段id
     */
    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    /**
     * 获取关于字段信息
     * @return
     */
    public String getOther() {
        return other;
    }

    /**
     * 设置关于字段信息
     * @param other
     */
    public void setOther(String other) {
        this.other = other;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "EdFieldTools{" +
                "id='" + id + '\'' +
                ", toolId='" + toolId + '\'' +
                ", type=" + type +
                ", fieldId='" + fieldId + '\'' +
                ", other='" + other + '\'' +
                '}';
    }
}