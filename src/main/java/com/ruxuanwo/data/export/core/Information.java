package com.ruxuanwo.data.export.core;

import java.io.Serializable;

/**
 * 校验器或转换器返回信息
 * @author ruxuanwo
 */
public class Information implements Serializable {
    /**
     * 正确与否
     */
    private Integer state;
    /**
     * 警告或错误
     */
    private Integer type;
    /**
     * 返回信息
     */
    private String msg;
    /**
     * 返回值
     */
    private Object data;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setValue(Integer state, Integer type, String msg, Object data){
        this.setData(data);
        this.setType(type);
        this.setMsg(msg);
        this.setState(state);
    }

    public void setValue(Integer state, Integer type, String msg){
        setValue(state, type, msg, null);
    }
}
