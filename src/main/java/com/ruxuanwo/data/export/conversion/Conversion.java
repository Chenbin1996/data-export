package com.ruxuanwo.data.export.conversion;

import com.ruxuanwo.data.export.core.Information;
import com.ruxuanwo.data.export.core.Parameter;

/**
 * 转换器接口
 *
 * @Author: ruxuanwo
 * @Date: 2018/7/5/0005 14:00
 */
public interface Conversion {
    /**
     * 转换方法
     * @param parameter 参数
     * @return 转换信息
     */
    Information conversion(Parameter parameter);
}
