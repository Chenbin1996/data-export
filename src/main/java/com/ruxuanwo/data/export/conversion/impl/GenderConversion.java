package com.ruxuanwo.data.export.conversion.impl;

import com.ruxuanwo.data.export.conversion.Conversion;
import com.ruxuanwo.data.export.dto.Information;
import com.ruxuanwo.data.export.dto.Parameter;
import com.ruxuanwo.data.export.enums.RecordStateEnum;
import org.springframework.stereotype.Component;

/**
 * 性别转换器
 * @author chenbin
 * 2018-10-18 16:36
 */
@Component
public class GenderConversion implements Conversion {
    @Override
    public Information conversion(Parameter parameter) {
        Information information = new Information();
        String man = "男";
        String woMan = "女";
        if (man.equals(parameter.getData())){
            information.setData(1);
        }else if (woMan.equals(parameter.getData())){
            information.setData(2);
        }
        information.setType(parameter.getType());
        information.setMsg(RecordStateEnum.RIGHT.getName());
        information.setState(RecordStateEnum.RIGHT.getCode());
        return information;
    }
}
