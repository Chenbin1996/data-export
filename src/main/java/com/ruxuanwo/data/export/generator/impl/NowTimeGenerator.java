package com.ruxuanwo.data.export.generator.impl;

import com.ruxuanwo.data.export.generator.Generator;
import com.ruxuanwo.data.export.core.GeneratorData;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间生成器
 * @author ruxuanwo
 */
@Component("nowTimeGenerator")
public class NowTimeGenerator implements Generator {

    @Override
    public Object generator(GeneratorData data) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}
