package com.ruxuanwo.data.export.domain;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
/**
 * @author Chenbin
 */
@Table(name = "ed_tools")
public class EdTools implements Serializable {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 1.校验器，2.转换器
     */
    private Integer type;

    /**
     * 校验器或转换器类名,后台根据这个反射
     */
    @Column(name = "class_name")
    private String className;

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
     * 描述
     */
    private String description;

    /**
     * 顺序
     */
    @Column(name = "sort_order")
    private Integer sortOrder;

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
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取1.校验器，2.转换器
     *
     * @return type - 1.校验器，2.转换器
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置1.校验器，2.转换器
     *
     * @param type 1.校验器，2.转换器
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取校验器或转换器类名,后台根据这个反射
     *
     * @return class_name - 校验器或转换器类名,后台根据这个反射
     */
    public String getClassName() {
        return className;
    }

    /**
     * 设置校验器或转换器类名,后台根据这个反射
     *
     * @param className 校验器或转换器类名,后台根据这个反射
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
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

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public String toString() {
        return "EdTools{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", className='" + className + '\'' +
                ", createTime=" + createTime +
                ", creator='" + creator + '\'' +
                ", description='" + description + '\'' +
                ", sortOrder=" + sortOrder +
                '}';
    }
}