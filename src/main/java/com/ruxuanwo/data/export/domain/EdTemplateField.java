package com.ruxuanwo.data.export.domain;

import javax.persistence.*;
import java.io.Serializable;
/**
 * @author ruxuanwo
 */
@Table(name = "ed_template_field")
public class EdTemplateField implements Serializable {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 模板字段名称
     */
    private String name;

    /**
     * 所属模板id
     */
    @Column(name = "template_id")
    private String templateId;

    /**
     * 描述
     */
    @Column(name = "default_value")
    private String defaultValue;

    /**
     *
     */
    @Column(name = "sort")
    private Integer sort;

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
     * 获取模板字段名称
     *
     * @return name - 模板字段名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置模板字段名称
     *
     * @param name 模板字段名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取所属模板id
     *
     * @return template_id - 所属模板id
     */
    public String getTemplateId() {
        return templateId;
    }

    /**
     * 设置所属模板id
     *
     * @param templateId 所属模板id
     */
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    /**
     * 获取字段缺省值
     *
     * @return defaultValue - 字段缺省值
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * 设置字段缺省值
     *
     * @param defaultValue 字段缺省值
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "EdTemplateField{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", templateId='" + templateId + '\'' +
                ", defaultValue='" + defaultValue + '\'' +
                '}';
    }
}