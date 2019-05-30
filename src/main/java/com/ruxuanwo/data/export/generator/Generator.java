package com.ruxuanwo.data.export.generator;

import com.ruxuanwo.data.export.dto.GeneratorData;

/**
 * @Author: ChenBin
 * @Date: 2018/6/26/0026 16:02
 */
public interface Generator {
    /**
     * 生成
     * @param data
     * @return
     * @throws Exception
     */
    Object generator(GeneratorData data) throws Exception;
}
