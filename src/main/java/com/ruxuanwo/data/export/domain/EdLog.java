package com.ruxuanwo.data.export.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author ruxuanwo on 2018/05/08
 * @version 3.0.0
 */
@Table(name = "ed_log")
public class EdLog implements Serializable {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 模板id
     */
    @Column(name = "template_id")
    private String templateId;

    /**
     * 状态:1.待导入、2.导入完成、3.删除
     */
    private Integer status;

    /**
     * 导入人id
     */
    @Column(name = "creator_id")
    private String creatorId;

    /**
     * 关联临时表
     */
    @Column(name = "foreign_table")
    private String foreignTable;

    /**
     * 导入时间，为实际导入到正式表中的时间
     */
    @Column(name = "expoort_time")
    private Date expoortTime;

    /**
     * 数据库url
     */
    private String dburl;

    /**
     * 连接端口，缺省使用数据库默认端口
     */
    private Integer port;

    /**
     * 数据库名
     */
    private String dbname;

    /**
     * 表名，多张表以逗号隔开
     */
    @Column(name = "table_name")
    private String tableName;

    /**
     * 导入记录数
     */
    @Column(name = "total_count")
    private Long totalCount;

    /**
     * 排序
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
     * 获取状态:1.待导入、2.导入完成、3.删除
     *
     * @return status - 状态:1.待导入、2.导入完成、3.删除
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态:1.待导入、2.导入完成、3.删除
     *
     * @param status 状态:1.待导入、2.导入完成、3.删除
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取导入人id
     *
     * @return creator_id - 导入人id
     */
    public String getCreatorId() {
        return creatorId;
    }

    /**
     * 设置导入人id
     *
     * @param creatorId 导入人id
     */
    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * 获取关联临时表
     *
     * @return foreign_table - 关联临时表
     */
    public String getForeignTable() {
        return foreignTable;
    }

    /**
     * 设置关联临时表
     *
     * @param foreignTable 关联临时表
     */
    public void setForeignTable(String foreignTable) {
        this.foreignTable = foreignTable;
    }

    /**
     * 获取导入时间，为实际导入到正式表中的时间
     *
     * @return expoort_time - 导入时间，为实际导入到正式表中的时间
     */
    public Date getExpoortTime() {
        return expoortTime;
    }

    /**
     * 设置导入时间，为实际导入到正式表中的时间
     *
     * @param expoortTime 导入时间，为实际导入到正式表中的时间
     */
    public void setExpoortTime(Date expoortTime) {
        this.expoortTime = expoortTime;
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

    /**
     * 获取表名，多张表以逗号隔开
     *
     * @return table_name - 表名，多张表以逗号隔开
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * 设置表名，多张表以逗号隔开
     *
     * @param tableName 表名，多张表以逗号隔开
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * 获取导入记录数
     *
     * @return total_count - 导入记录数
     */
    public Long getTotalCount() {
        return totalCount;
    }

    /**
     * 设置导入记录数
     *
     * @param totalCount 导入记录数
     */
    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}