package com.ruxuanwo.data.export.dto;

/**
 * @Author: ChenBin
 * @Date: 2018/6/26/0026 14:36
 */
public class SelectDbDTO {
    /**
     * 字段名称
     */
    private String fieldName;
    /**
     * 字段类型
     */
    private String fieldType;
    /**
     * 所属表名称
     */
    private String toTable;
    /**
     * 表的导入顺序
     */
    private Integer importSort;
    /**
     * 模版关联表ID
     */
    private String tableId;

    /**
     * 拼接表名称和字段名称
     * @return
     */
    private String tableAndField;

    public SelectDbDTO(String fieldName, String fieldType, String toTable, String tableId, Integer importSort) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.toTable = toTable;
        this.tableId = tableId;
        this.importSort = importSort;
        this.tableAndField = toTable + "." + fieldName;


    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getToTable() {
        return toTable;
    }

    public void setToTable(String toTable) {
        this.toTable = toTable;
    }

    public String getTableAndField() {
        return tableAndField;
    }

    public void setTableAndField(String tableAndField) {
        this.tableAndField = tableAndField;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public Integer getImportSort() {
        return importSort;
    }

    public void setImportSort(Integer importSort) {
        this.importSort = importSort;
    }

    @Override
    public String toString() {
        return "SelectDbDTO{" +
                "fieldName='" + fieldName + '\'' +
                ", fieldType='" + fieldType + '\'' +
                ", toTable='" + toTable + '\'' +
                ", tableId='" + tableId + '\'' +
                ", tableAndField='" + tableAndField + '\'' +
                '}';
    }
}
