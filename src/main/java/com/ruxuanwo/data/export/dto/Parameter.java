package com.ruxuanwo.data.export.dto;

/**
 * @Author: chenbin
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
     * 长度校验器长度
     */
    private Integer length;
    /**
     * 河湖库岸别pid
     */
    private String pid;

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

    public Integer getLength() {
        return length;
    }

    public Parameter setLength(Integer length) {
        this.length = length;
        return this;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
