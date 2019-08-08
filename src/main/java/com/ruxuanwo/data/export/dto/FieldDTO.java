package com.ruxuanwo.data.export.dto;

import com.ruxuanwo.data.export.check.Factory;
import com.ruxuanwo.data.export.generator.Generator;

/**
 * 字段信息对象
 * @author ruxuanwo
 */
public class FieldDTO {

    /**
     * 字段名称-数据库
     */
    private String fieldName;
    /**
     * 值的生成方式类型
     */
    private Integer type;
    /**
     * 从excel获取值，excel名称
     */
    private String excelName;
    /**
     * 从默认值获取
     */
    private String defaultValue;
    /**
     * 从生成器获取,生成器类名
     */
    private String generateName;
    /**
     * 生成器
     */
    private Generator generator;
    /**
     * 从外键获取,外键表
     */
    private String foreignTable;
    /**
     * 从外键获取,外键字段
     */
    private String foreignField;

    /**
     * 是否进行重复性校验
     */
    private Integer repeatCheck;

    public Integer getRepeatCheck() {
        return repeatCheck;
    }

    public void setRepeatCheck(Integer repeatCheck) {
        this.repeatCheck = repeatCheck;
    }

    public String getGenerateName() {
        return generateName;
    }

    public void setGenerateName(String generateName) {
        this.generateName = generateName;
        //执行时创建生成器
        if(this.generateName == null){
            return;
        }
        this.generator = Factory.createGenerator(this.generateName);

    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getExcelName() {
        return excelName;
    }

    public void setExcelName(String excelName) {
        this.excelName = excelName;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Generator getGenerator() {
        return generator;
    }

    public void setGenerator(Generator generator) {
        this.generator = generator;
    }

    public String getForeignTable() {
        return foreignTable;
    }

    public void setForeignTable(String foreignTable) {
        this.foreignTable = foreignTable;
    }

    public String getForeignField() {
        return foreignField;
    }

    public void setForeignField(String foreignField) {
        this.foreignField = foreignField;
    }

    @Override
    public String toString() {
        return "FieldDTO{" +
                "fieldName='" + fieldName + '\'' +
                ", type=" + type +
                ", excelName='" + excelName + '\'' +
                ", defaultValue='" + defaultValue + '\'' +
                ", generateName='" + generateName + '\'' +
                ", generator=" + generator +
                ", foreignTable='" + foreignTable + '\'' +
                ", foreignField='" + foreignField + '\'' +
                '}';
    }
}
