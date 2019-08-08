package com.ruxuanwo.data.export.enums;

/**
 * 校验工具的类型
 *
 * @author ruxuanwo
 * @date Created on 2018/4/25
 * */
public enum ToolTypeEnum {
    //校验器
    type_check(1,"校验器"),
    //转换器
    tyep_conver(2,"转换器"),
    //生成器
    type_generate(3,"生成器"),
    //导入器
    type_impoer(4,"导入器");

    private Integer code;
    private String name;

    ToolTypeEnum(Integer code, String name) {
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

    public static String findName(Integer code){
        for(ToolTypeEnum typeEnum:values()){
            if(typeEnum.getCode().equals(code)){
                return typeEnum.getName();
            }
        }
        return null;
    }
}
