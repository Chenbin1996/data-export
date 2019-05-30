package com.ruxuanwo.data.export.dto;

import com.ruxuanwo.data.export.domain.EdTools;

/**
 * 工具表-DTO数据传输对象类
 * @author chenbin on 2018/04/20
 * @version 3.0.0
 */
public class EdToolsDTO extends EdTools {

    private String typeName;
    private String excelName;
    private String other;
    private Integer failType;
    private String userName;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public Integer getFailType() {
        return failType;
    }

    public void setFailType(Integer failType) {
        this.failType = failType;
    }

    public String getExcelName() {
        return excelName;
    }

    public void setExcelName(String excelName) {
        this.excelName = excelName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "EdToolsDTO{" +
                "typeName='" + typeName + '\'' +
                ", excelName='" + excelName + '\'' +
                ", other='" + other + '\'' +
                ", failType=" + failType +
                '}';
    }
}
