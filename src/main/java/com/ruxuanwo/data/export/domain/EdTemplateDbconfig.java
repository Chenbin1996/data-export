package com.ruxuanwo.data.export.domain;

import javax.persistence.*;
import java.io.Serializable;
/**
 * @author Chenbin
 */
@Table(name = "ed_template_dbconfig")
public class EdTemplateDbconfig implements Serializable {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 连接名称
     */
    private String connectionName;

    /**
     * 模板id
     */
    @Column(name = "template_id")
    private String templateId;

    /**
     * 数据库url
     */
    private String dburl;

    /**
     * 数据库类型:MYSQL、SQL SERVER等
     */
    @Column(name = "dbtype_id")
    private String dbtypeId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 连接密码
     */
    private String password;

    /**
     * 连接端口，缺省使用数据库默认端口
     */
    private Integer port;

    /**
     * 数据库名
     */
    private String dbname;

    /**
     * 表名
     */

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
     * 获取连接名称
     *
     * @return connectionName - 连接名称
     */
    public String getConnectionName() {
        return connectionName;
    }

    /**
     * 设置连接名称
     *
     * @param connectionName 连接名称
     */
    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    /**
     * 获取模板id
     *
     * @return template_id - 模板id
     */
    public String getTemplateId() {
        return templateId;
    }

    /**
     * 设置模板id
     *
     * @param templateId 模板id
     */
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    /**
     * 获取数据库url
     *
     * @return dburl - 数据库url
     */
    public String getDburl() {
        return dburl;
    }

    /**
     * 设置数据库url
     *
     * @param dburl 数据库url
     */
    public void setDburl(String dburl) {
        this.dburl = dburl;
    }

    /**
     * 获取数据库类型:MYSQL、SQL SERVER等
     *
     * @return dbtype_id - 数据库类型:MYSQL、SQL SERVER等
     */
    public String getDbtypeId() {
        return dbtypeId;
    }

    /**
     * 设置数据库类型:MYSQL、SQL SERVER等
     *
     * @param dbtypeId 数据库类型:MYSQL、SQL SERVER等
     */
    public void setDbtypeId(String dbtypeId) {
        this.dbtypeId = dbtypeId;
    }

    /**
     * 获取用户名
     *
     * @return username - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取连接密码
     *
     * @return password - 连接密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置连接密码
     *
     * @param password 连接密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取连接端口，缺省使用数据库默认端口
     *
     * @return port - 连接端口，缺省使用数据库默认端口
     */
    public Integer getPort() {
        return port;
    }

    /**
     * 设置连接端口，缺省使用数据库默认端口
     *
     * @param port 连接端口，缺省使用数据库默认端口
     */
    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     * 获取数据库名
     *
     * @return dbname - 数据库名
     */
    public String getDbname() {
        return dbname;
    }

    /**
     * 设置数据库名
     *
     * @param dbname 数据库名
     */
    public void setDbname(String dbname) {
        this.dbname = dbname;
    }


    @Override
    public String toString() {
        return "EdTemplateDbconfig{" +
                "id='" + id + '\'' +
                ", connectionName='" + connectionName + '\'' +
                ", templateId='" + templateId + '\'' +
                ", dburl='" + dburl + '\'' +
                ", dbtypeId='" + dbtypeId + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", port=" + port +
                ", dbname='" + dbname + '\'' +
                '}';
    }
}