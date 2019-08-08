package com.ruxuanwo.data.export.core;

/**
 * @Author: ruxuanwo
 * @Date: 2018/4/27/0027 19:30s
 * 传入校验器或转换器的参数
 */
public class Parameter {
    /**
     * 数据
     */
    private String data;
    /**
     * 字段名称
     */
    private String excelName;
    /**
     * 出错提示，警告or错误
     */
    private Integer type;
    /**
     * 前端传入参数
     */
    private String other;
    /**
     * 河湖库岸别pid
     */
    private String pid;

    private String regionName;

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getData() {
        return data;
    }

    public Parameter setData(String data) {
        this.data = data;
        return this;
    }

    public String getExcelName() {
        return excelName;
    }

    public Parameter setExcelName(String excelName) {
        this.excelName = excelName;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public Parameter setType(Integer type) {
        this.type = type;
        return this;
    }

    public String getOther() {
        return other;
    }

    public Parameter setOther(String other) {
        this.other = other;
        return this;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
