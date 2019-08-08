package com.ruxuanwo.data.export.enums;


/**
 * 是否进行重复性校验
 * @author ruxuanwo
 * 2019-1-14 11:26
 */
public enum RepeatCheckEnum {

    YES("进行重复性校验", 1),

    NO("不进行重复性校验", 0);

    private String name;

    private Integer value;

    RepeatCheckEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }

    /**
     * 根据值判断是否进行校验
     * @param value
     * @return
     */
    public static boolean isRepeatCheck(Integer value){
        return RepeatCheckEnum.YES.getValue().equals(value);
    }
}
