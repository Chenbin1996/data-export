package com.ruxuanwo.data.export.dto;


import java.sql.Connection;

/**
 * 生成器所需参数,
 * 如有需要，自行扩展
 */
public class GeneratorData {

    /**
     * 表名
     */
    private String tableName;
    /**
     * 字段名称
     */
    private String fieldName;
    /**
     * 数据库连接
     */
    private Connection connection;

    public GeneratorData() {
    }

    public GeneratorData(String tableName, String fieldName, Connection connection) {
        this.tableName = tableName;
        this.fieldName = fieldName;
        this.connection = connection;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
