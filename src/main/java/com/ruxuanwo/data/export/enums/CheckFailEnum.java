package com.ruxuanwo.data.export.enums;

/**
 * 校验字段失败时提示状态
 *
 * @author chenbin
 * @date Created on 2018/4/25
 */
public enum CheckFailEnum {
    //警告
    fail_warning(1,"警告"),
    //错误
    fail_error(2,"错误");
    private Integer code;
    private String name;

    CheckFailEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CheckFailEnum{" +
                "code=" + code +
                ", name='" + name + '\'' +
                '}';
    }

    public static String findName(Integer code){
        for(CheckFailEnum failEnum:values()){
            if(failEnum.getCode().equals(code)){
                return failEnum.getName();
            }
        }
        return null;
    }


}
