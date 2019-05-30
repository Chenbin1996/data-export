package com.ruxuanwo.data.export.dto;

import com.ruxuanwo.data.export.enums.RecordStateEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装导入的每个单元格
 * @author chenbin
 */
public class Cell {
    /**
     * excel名称
     */
    private String excelName;
    /**
     * 原始值
     */
    private String value;
    /**
     * 校验信息
     */
    private List<Information> checkInforms;
    /**
     * 转换信息
     */
    private Information converInform;


    public Cell() {
        this.checkInforms = new ArrayList<>();
    }

    public Cell(String excelName, String value) {
        this.checkInforms = new ArrayList<>();
        this.excelName = excelName;
        this.value = value;
        if(value != null && !"".equals(value)){
            this.value = null;
        }
    }

    /**
     * 存放校验信息
     * @return
     */
    public StringBuilder putErrorMsg(){
        StringBuilder msg = new StringBuilder();
        for (Information information : this.getCheckInforms()) {
            if(!information.getState().equals(RecordStateEnum.RIGHT.getCode())){
                msg.append(information.getMsg()).append("|");
            }
        }
        if(this.getConverInform() != null && !this.getConverInform().getState().equals(RecordStateEnum.RIGHT.getCode())){
            msg.append(this.getConverInform().getMsg()).append("|");
        }
        return msg;
    }

    public String getExcelName() {
        return excelName;
    }

    public void setExcelName(String excelName) {
        this.excelName = excelName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Information> getCheckInforms() {
        return checkInforms;
    }

    public void setCheckInforms(List<Information> checkInforms) {
        this.checkInforms = checkInforms;
    }

    public Information getConverInform() {
        return converInform;
    }

    public void setConverInform(Information converInform) {
        this.converInform = converInform;
    }

}
