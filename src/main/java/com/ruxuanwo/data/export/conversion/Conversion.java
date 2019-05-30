package com.ruxuanwo.data.export.conversion;

import com.ruxuanwo.data.export.dto.Information;
import com.ruxuanwo.data.export.dto.Parameter;

/**
 * 转换器接口
 *
 * @Author: ChenBin
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
