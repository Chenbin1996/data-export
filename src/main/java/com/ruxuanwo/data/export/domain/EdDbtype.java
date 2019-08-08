package com.ruxuanwo.data.export.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author ruxuanwo
 */
@Table(name = "ed_dbtype")
public class EdDbtype implements Serializable {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 数据库名,如MYSQL、ORACLE等
     */
    private String name;

    /**
     * 数据库驱动
     */
    @Column(name = "driver_class")
    private String driverClass;

    /**
     * int
     */
    private Integer port;

    /**
     * 描述
     */
    private String description;

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
     * 获取数据库名,如MYSQL、ORACLE等
     *
     * @return name - 数据库名,如MYSQL、ORACLE等
     */
    public String getName() {
        return name;
    }

    /**
     * 设置数据库名,如MYSQL、ORACLE等
     *
     * @param name 数据库名,如MYSQL、ORACLE等
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取数据库驱动
     *
     * @return driver_class - 数据库驱动
     */
    public String getDriverClass() {
        return driverClass;
    }

    /**
     * 设置数据库驱动
     *
     * @param driverClass 数据库驱动
     */
    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    /**
     * 获取int
     *
     * @return port - int
     */
    public Integer getPort() {
        return port;
    }

    /**
     * 设置int
     *
     * @param port int
     */
    public void setPort(Integer port) {
        this.port = port;
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

    @Override
    public String toString() {
        return "EdDbtype{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", driverClass='" + driverClass + '\'' +
                ", port=" + port +
                ", description='" + description + '\'' +
                '}';
    }
}