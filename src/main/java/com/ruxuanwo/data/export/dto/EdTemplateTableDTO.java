package com.ruxuanwo.data.export.dto;
import com.ruxuanwo.data.export.domain.EdTemplateTable;
import com.ruxuanwo.data.export.enums.GeneratorEnum;
import com.ruxuanwo.data.export.enums.ValueTypeEnum;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 模版关联表-DTO数据传输对象类
 * @author ChenBin on 2018/06/22
 */
public class EdTemplateTableDTO extends EdTemplateTable {
    /**
     * 插入的sql
     */
    private StringBuilder insertSql;
    /**
     * 预处理插入sql
     */
    private StringBuilder insertPrepareSql;
    /**
     * 预处理查询sql
     */
    private StringBuilder selectPrepareSql;
    /**
     * 字段数据
     */
    private List<FieldDTO> fieldList;
    /**
     * 存放要保存的所有字段和数据
     */
    private HashMap<String,String> keyAndValue;
    /**
     * 存放需要进行重复性判断的字段和值
     */
    private HashMap<String,String> selectKeyAndValue;
    /**
     * 数据库连接
     */
    private Connection connection;

    /**
     * 放入所有字段和数据
     * @param table   当前表
     * @param dataMap 导入的一行数据
     * @param list    表集合
     */
    public void putAllValue(EdTemplateTableDTO table, Map<String, String> dataMap, List<EdTemplateTableDTO> list) throws Exception {
        if (table.getKenGenerate().equals(GeneratorEnum.UUID.getNum())) {
            table.putKeyAndValue(table.getKeyName(), UUID.randomUUID().toString().replace("-", ""));
        }
        for (FieldDTO field : table.getFieldList()) {
            table.putKeyAndValue(field.getFieldName(), this.getFieldValue(field, dataMap, list));
        }
    }

    /**
     * 获取当前字段的值
     *
     * @param field   当前字段
     * @param dataMap 一行导入的excel数据
     * @param list    当前导入表类集合
     * @return
     */
    private String getFieldValue(FieldDTO field, Map<String, String> dataMap, List<EdTemplateTableDTO> list) throws Exception {
        String value = null;
        if (field.getType().equals(ValueTypeEnum.EXCEL.getCode())) {
            value = dataMap.get(field.getExcelName());
            if(value != null && !"".equals(value)){
                selectKeyAndValue.put(field.getFieldName(), value);
            }else if ("".equals(value)){
                //将空字符串设置为null防止插入数据库失败
                value = null;
            }
        } else if (field.getType().equals(ValueTypeEnum.DEFAULT.getCode())) {
            value = field.getDefaultValue();
            selectKeyAndValue.put(field.getFieldName(), value);
        } else if (field.getType().equals(ValueTypeEnum.GENERATOR.getCode())) {
            value = field.getGenerator().generator(new GeneratorData(this.getTableName(), field.getFieldName(),this.getConnection())).toString();
        } else if (field.getType().equals(ValueTypeEnum.FOREIGN.getCode())) {
            value = this.getForeignValue(list, field);
            selectKeyAndValue.put(field.getFieldName(), value);
        }
        return value;
    }

    /**
     * 获取外键值
     *
     * @param list  当前导入表类集合
     * @param field 当前字段
     * @return
     */
    private String getForeignValue(List<EdTemplateTableDTO> list, FieldDTO field) {
        for (EdTemplateTableDTO table : list) {
            if (table.getTableName().equals(field.getForeignTable())) {
                return table.getKeyAndValue().get(field.getForeignField());
            }
        }
        return null;
    }

    /**
     * 创建插入的sql
     */
    public void createInsertSql(){
        StringBuilder sql = new StringBuilder();
        StringBuilder values = new StringBuilder();
        sql.append("insert into ").append(this.getTableName()).append(" ( ");
        for(String key:keyAndValue.keySet()){
            sql.append(key).append(",");
            values.append("'").append(keyAndValue.get(key)).append("'").append(",");
        }
        sql.deleteCharAt(sql.length() - 1);
        values.deleteCharAt(values.length() - 1);
        sql.append(") ").append("values ( ").append(values).append(")");
        this.insertSql = sql;
    }

    /**
     * 创建预处理sql
     */
    public void createInsertPrepareSql(){
        //if(this.insertPrepareSql != null){
        //    return;
        //}
        StringBuilder sql = new StringBuilder();
        StringBuilder values = new StringBuilder();
        sql.append("REPLACE INTO ").append(this.getTableName()).append(" ( ");
        for(String key : keyAndValue.keySet()){
            sql.append(key).append(",");
            values.append("?").append(",");
        }
        sql.deleteCharAt(sql.length() - 1);
        values.deleteCharAt(values.length() - 1);
        sql.append(") ").append("VALUES ( ").append(values).append(")");
        this.insertPrepareSql = sql;
    }

    /**
     * 创建查询预处理sql
     */
    public void createSelectPrepareSql(){
        //if(this.selectPrepareSql != null){
        //    return;
        //}
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ")
            .append(this.getTableName())
            .append(" WHERE ");
        for(String key: selectKeyAndValue.keySet()){
            sql.append(key).append(" = ").append("?").append(" AND ");
        }
        sql.append(" 1 = 1");
        this.selectPrepareSql = sql;
    }

    /**
     * 往keyAndValue存放数据
     * @param key 字段名称
     * @param value 字段值
     */
    public void putKeyAndValue(String key, String value){
        this.keyAndValue.put(key,value);
    }

    /**
     * 清空存放要保存的字段和数据，创建新map
     */
    public void cleanAndNewMap(){
        this.keyAndValue = null;
        this.keyAndValue = new HashMap<>(16);
        this.selectKeyAndValue = null;
        this.selectKeyAndValue = new HashMap<>(16);
    }

    public HashMap<String, String> getKeyAndValue() {
        return keyAndValue;
    }

    public void setKeyAndValue(HashMap<String, String> keyAndValue) {
        this.keyAndValue = keyAndValue;
    }

    public StringBuilder getInsertSql() {
        return insertSql;
    }

    public void setInsertSql(StringBuilder insertSql) {
        this.insertSql = insertSql;
    }

    public List<FieldDTO> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<FieldDTO> fieldList) {
        this.fieldList = fieldList;
    }

    public StringBuilder getInsertPrepareSql() {
        return insertPrepareSql;
    }

    public void setInsertPrepareSql(StringBuilder insertPrepareSql) {
        this.insertPrepareSql = insertPrepareSql;
    }

    public StringBuilder getSelectPrepareSql() {
        return selectPrepareSql;
    }

    public void setSelectPrepareSql(StringBuilder selectPrepareSql) {
        this.selectPrepareSql = selectPrepareSql;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public HashMap<String, String> getSelectKeyAndValue() {
        return selectKeyAndValue;
    }

    public void setSelectKeyAndValue(HashMap<String, String> selectKeyAndValue) {
        this.selectKeyAndValue = selectKeyAndValue;
    }

    @Override
    public String toString() {
        return "EdTemplateTableDTO{" +
                "insertSql=" + insertSql +
                ", insertPrepareSql=" + insertPrepareSql +
                ", fieldList=" + fieldList +
                ", keyAndValue=" + keyAndValue +
                '}';
    }
}
