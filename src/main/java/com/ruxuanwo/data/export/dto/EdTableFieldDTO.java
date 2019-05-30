package com.ruxuanwo.data.export.dto;
import com.ruxuanwo.data.export.domain.EdTableField;

/**
 * 导入字段表-DTO数据传输对象类
 * @author ChenBin on 2018/06/22
 */
public class EdTableFieldDTO extends EdTableField {
    /**
     * 字段默认值
     */
    private String value;
    /**
     * 工具ID
     */
    private String toolId;
    /**
     * 工具名称
     */
    private String toolName;
    /**
     * 外键字段名称
     */
    private String foreignField;
    /**
     * 外键所属表
     */
    private String foreignTableName;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getToolId() {
        return toolId;
    }

    public void setToolId(String toolId) {
        this.toolId = toolId;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public String getForeignField() {
        return foreignField;
    }

    public void setForeignField(String foreignField) {
        this.foreignField = foreignField;
    }

    public String getForeignTableName() {
        return foreignTableName;
    }

    public void setForeignTableName(String foreignTableName) {
        this.foreignTableName = foreignTableName;
    }

    @Override
    public String toString() {
        return "EdTableFieldDTO{" +
                "value='" + value + '\'' +
                ", toolId='" + toolId + '\'' +
                ", toolName='" + toolName + '\'' +
                '}';
    }
}
