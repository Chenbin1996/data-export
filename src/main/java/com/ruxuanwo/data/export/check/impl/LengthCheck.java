package com.ruxuanwo.data.export.check.impl;

import com.ruxuanwo.data.export.check.Check;
import com.ruxuanwo.data.export.check.Client;
import com.ruxuanwo.data.export.dto.Information;
import com.ruxuanwo.data.export.dto.Parameter;
import com.ruxuanwo.data.export.enums.RecordStateEnum;
import org.springframework.stereotype.Component;

/**
 * 长度校验器
 *
 * @Author: ChenBin
 * @Date: 2018/4/27/0027 19:30
 */
@Component("lengthCheck")
public class LengthCheck implements Check {


    @Override
    public Information validate(Client client,Parameter parameter) {
        Information information = new Information();
        if (parameter.getData().length() > parameter.getLength()){
            information.setMsg(parameter.getExcelName()+ "超出规定长度");
            information.setState(RecordStateEnum.ERROR.getCode());
        }else {
            information.setType(parameter.getType());
            information.setState(RecordStateEnum.RIGHT.getCode());
            client.startValidate();
        }
        return information;
    }
}
