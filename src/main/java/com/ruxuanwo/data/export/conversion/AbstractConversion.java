package com.ruxuanwo.data.export.conversion;

import com.ruxuanwo.data.export.constants.Constant;
import com.ruxuanwo.data.export.core.Information;
import com.ruxuanwo.data.export.enums.RecordStateEnum;

/**
 * @author ruxuanwo
 * @date Created on 2019/3/2
 */
public abstract class AbstractConversion implements Conversion {

    /**
     * 转换成功
     *
     * @param data
     * @return
     */
    public Information success(Object data) {
        Information information = new Information();
        information.setMsg(RecordStateEnum.RIGHT.getName());
        information.setState(RecordStateEnum.RIGHT.getCode());
        information.setData(data);
        return information;
    }

    /**
     * 转换成功
     *
     * @param data
     * @return
     */
    public Information success(Integer type, Object data) {
        Information information = new Information();
        information.setType(type);
        information.setMsg(RecordStateEnum.RIGHT.getName());
        information.setState(RecordStateEnum.RIGHT.getCode());
        information.setData(data);
        return information;
    }

    /**
     * 转换失败
     *
     * @param type
     * @return
     */
    public Information failure(Integer type) {

        return this.failure(type, null);
    }

    /**
     * 转换失败
     *
     * @param type
     * @return
     */
    public Information failure(Integer type, String msg) {
        Information information = new Information();
        information.setMsg(msg == null ? Constant.REGION_NULL : msg);
        information.setState(RecordStateEnum.ERROR.getCode());
        information.setType(type);
        return information;
    }
}
