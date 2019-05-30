package com.ruxuanwo.data.export.check;

import com.ruxuanwo.data.export.conversion.Conversion;
import com.ruxuanwo.data.export.generator.Generator;
import com.ruxuanwo.data.export.utils.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @Author: ChenBin
 * @Date: 2018/5/2/0002 9:38
 */
public class Factory {

    private static Logger logger = LoggerFactory.getLogger(Factory.class);

    public static Check createCheck (String className){
        return (Check)newInstance(className);
    }

    public static Conversion createConversion (String className){
        return (Conversion)newInstance(className);
    }

    public static Generator createGenerator(String className){
        return (Generator)newInstance(className);
    }

    public static Object newInstance(String className){
        try {
            return SpringContextUtil.getBean(Class.forName(className));
        } catch (ReflectiveOperationException e) {
            logger.error("fail to get bean", e);
        }
        return null;
    }
}
