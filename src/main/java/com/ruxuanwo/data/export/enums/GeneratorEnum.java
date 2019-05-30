package com.ruxuanwo.data.export.enums;

/**
 * 主键生成方式枚举
 * @Author: ChenBin
 * @Date: 2018/6/25/0025 18:26
 */
public enum GeneratorEnum {
    /**
     * 自增ID
     */
    AUTOINCREMENT(1, "自增"),
    /**
     * UUID
     */
    UUID(2, "UUID"),
    /**
     * 自由自定ID
     */
    CUSTOMIZE(3, "自定义主键");

    private Integer num;
    private String name;

    GeneratorEnum(Integer num, String name) {
        this.num = num;
        this.name = name;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "GeneratorEnum{" +
                "num=" + num +
                ", name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
