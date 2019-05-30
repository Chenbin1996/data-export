package com.ruxuanwo.data.export.enums;

/**
 * @Author: ChenBin
 * @Date: 2018/6/26/0026 13:45
 */
public enum  ValueTypeEnum {
    /**
     * excel
     */
    EXCEL(1, "excel"),
    /**
     * 默认值
     */
    DEFAULT(2, "默认值"),
    /**
     * 生成器
     */
    GENERATOR(3, "生成器"),
    /**
     * 外键关联
     */
    FOREIGN(4, "外键关联");

    private int code;
    private String name;

    ValueTypeEnum(int code, String name) {
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

    @Override
    public String toString() {
        return "ValueTypeEnum{" +
                "code=" + code +
                ", name='" + name + '\'' +
                '}';
    }
}
