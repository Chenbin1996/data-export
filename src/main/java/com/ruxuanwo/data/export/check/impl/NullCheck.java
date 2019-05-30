package com.ruxuanwo.data.export.check.impl;

import com.ruxuanwo.data.export.check.Check;
import com.ruxuanwo.data.export.check.Client;
import com.ruxuanwo.data.export.dto.Information;
import com.ruxuanwo.data.export.dto.Parameter;
import com.ruxuanwo.data.export.enums.RecordStateEnum;
import org.springframework.stereotype.Component;

/**
 * 非空校验器
 *
 * @Author: ChenBin
 * @Date: 2018/4/27/0027 18:58
 */
@Component("nullCheck")
public class NullCheck implements Check {


    @Override
    public Information validate(Client client,Parameter parameter) {
        Information information = new Information();
        if (parameter.getData() != null && !"".equals(parameter.getData())) {
            information.setMsg(RecordStateEnum.RIGHT.getName());
            information.setState(RecordStateEnum.RIGHT.getCode());
            client.startValidate();
        } else {
            information.setMsg(parameter.getExcelName() + "不能为空");
            information.setType(parameter.getType());
            information.setState(RecordStateEnum.ERROR.getCode());
        }
        return information;
    }


}
