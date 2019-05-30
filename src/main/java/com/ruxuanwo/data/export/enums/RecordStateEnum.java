package com.ruxuanwo.data.export.enums;

/**
 * 导入数据到临时表时数据状态
 *
 * @author chenbin
 * @date Created on 2018/5/8
 */
public enum RecordStateEnum {

    //正确
    RIGHT(1,"正确"),
    //警告
    WARNING(2,"警告"),
    //错误
    ERROR(3,"错误");

    private int code;
    private String name;

    RecordStateEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String findByCode(int code){
        for(RecordStateEnum recordStateEnum : RecordStateEnum.values()){
            if(recordStateEnum.getCode() == code){
                return recordStateEnum.getName();
            }
        }
        return null;
    }

}
