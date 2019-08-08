package com.ruxuanwo.data.export.conversion.impl;

import com.ruxuanwo.data.export.conversion.AbstractConversion;
import com.ruxuanwo.data.export.conversion.Conversion;
import com.ruxuanwo.data.export.core.Information;
import com.ruxuanwo.data.export.core.Parameter;
import org.springframework.stereotype.Component;

/**
 * 性别转换器
 * @author ruxuanwo
 * 2018-10-18 16:36
 */
@Component
public class GenderConversion extends AbstractConversion implements Conversion {
    @Override
    public Information conversion(Parameter parameter) {
        String man = "男";
        String woMan = "女";
        Integer data = 0;
        if (man.equals(parameter.getData())){
            data = 1;
        }else if (woMan.equals(parameter.getData())){
            data = 2;
        }
        return success(parameter.getType(), data);
    }
}
