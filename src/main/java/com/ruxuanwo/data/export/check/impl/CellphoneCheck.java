package com.ruxuanwo.data.export.check.impl;

import com.ruxuanwo.data.export.check.Check;
import com.ruxuanwo.data.export.check.Client;
import com.ruxuanwo.data.export.dto.Information;
import com.ruxuanwo.data.export.dto.Parameter;
import com.ruxuanwo.data.export.enums.RecordStateEnum;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * 手机号校验
 *
 * @author chenbin
 */
@Component("cellphoneCheck")
public class CellphoneCheck implements Check {
    @Override
    public Information validate(Client client, Parameter parameter) {
        Information information = new Information();
        if (isPhone(parameter.getData())){
            information.setType(parameter.getType());
            information.setState(RecordStateEnum.RIGHT.getCode());
            client.startValidate();
        }else {
            information.setMsg(parameter.getExcelName() + "不是手机号码");
            information.setType(parameter.getType());
            information.setState(RecordStateEnum.ERROR.getCode());
        }
        return information;
    }

    private boolean isPhone(String src){
        String regex = "(\\+\\d+)?1[34578]\\d{9}$";
        return Pattern.matches(regex, src);
    }

}
