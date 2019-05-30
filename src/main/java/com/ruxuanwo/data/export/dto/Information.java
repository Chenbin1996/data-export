package com.ruxuanwo.data.export.dto;

import java.io.Serializable;

/**
 * 校验器或转换器返回信息
 * @author chenbin
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
}
