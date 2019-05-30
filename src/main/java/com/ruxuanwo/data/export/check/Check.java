package com.ruxuanwo.data.export.check;

import com.ruxuanwo.data.export.dto.Information;
import com.ruxuanwo.data.export.dto.Parameter;

/**
 * @Author: ChenBin
 * @Date: 2018/4/27/0027 18:53
 */
public interface Check {

    /**
     * 校验方法
     * @param client 校验器链
     * @param parameter 参数
     * @return 校验结果
     */
    Information validate(Client client,Parameter parameter);

}
