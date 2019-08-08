package com.ruxuanwo.data.export.enums;

/**
 * 导入数据到临时表时数据状态
 *
 * @author ruxuanwo
 * @date Created on 2018/5/8
 */
public enum LogStateEnum {

    //正确
    WAIT(1,"待导入"),
    //警告
    FINISH(2,"导入完成"),
    //错误
    DELETE(3,"删除");

    private int code;
    private String name;

    LogStateEnum(int code, String name) {
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
        for(LogStateEnum stateEnumnum : LogStateEnum.values()){
            if(stateEnumnum.getCode() == code){
                return stateEnumnum.getName();
            }
        }
        return null;
    }

}
