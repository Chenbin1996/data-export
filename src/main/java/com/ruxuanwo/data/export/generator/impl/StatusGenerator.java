package com.ruxuanwo.data.export.generator.impl;

import com.ruxuanwo.data.export.generator.Generator;
import com.ruxuanwo.data.export.core.GeneratorData;
import org.springframework.stereotype.Component;

/**
 * 获取正常记录状态
 */
@Component
public class StatusGenerator implements Generator {

    @Override
    public Object generator(GeneratorData data) throws Exception {
       return 1;
    }

}
