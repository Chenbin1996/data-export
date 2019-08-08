package com.ruxuanwo.data.export.check;


import com.ruxuanwo.data.export.core.Information;
import com.ruxuanwo.data.export.core.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 *  启动效验器
 * @Author: ruxuanwo
 * @Date: 2018/5/2/0002 10:29
 */
public class Client {

    /**
     * 校验工具的对象集
     */
    private List<Check> checks = new ArrayList<>();
    /**
     * 校验工具所需参数集
     */
    private List<Parameter> parameters = new ArrayList<>();
    /**
     * 当前所在校验器索引
     */
    private int index = 0;
    /**
     * 校验信息集合
     */
    private List<Information> informations = new ArrayList<>();

    /**
     * 添加校验方法到校验工具的对象集
     * @param check
     * @return
     */
    public Client addCheck(Check check) {
        checks.add(check);
        return this;
    }

    public void addToParameters(Parameter parameter){
        this.parameters.add(parameter);
    }

    /**
     * 重置校验器索引
     */
    public void reset(){
        this.index = 0;
        this.informations = new ArrayList<>();
    }

    /**
     * 开始执行校验器
     * @return 校验信息集合
     */
    public List<Information> startValidate(){
        if(index == checks.size()){
            return this.informations;
        }
        Check check = checks.get(index);
        index++;
        Information information = check.validate(this ,parameters.get(index - 1));
        this.informations.add(information);
        return this.informations;
    }

    /**
     * 设置Parameter的data属性
     */
    public void setParametersData(String data) {
        for (Parameter parameter : this.parameters) {
            parameter.setData(data);
        }
    }
}
