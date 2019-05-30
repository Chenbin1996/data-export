package com.ruxuanwo.data.export.conversion.impl;


import com.ruxuanwo.data.export.conversion.Conversion;
import com.ruxuanwo.data.export.dto.Information;
import com.ruxuanwo.data.export.dto.Parameter;
import com.ruxuanwo.data.export.enums.RecordStateEnum;
import com.ruxuanwo.data.export.utils.CryptoUtil;
import org.springframework.stereotype.Component;

/**
 * 加密转换
 * @author chenbin
 */
@Component("encryptionConversion")
public class EncryptionConversion implements Conversion {

    @Override
    public Information conversion(Parameter parameter) {
        Information information = new Information();
        String encryptPwd = CryptoUtil.encode(parameter.getData());
        information.setType(parameter.getType());
        information.setState(RecordStateEnum.RIGHT.getCode());
        information.setMsg(RecordStateEnum.RIGHT.getName());
        information.setData(encryptPwd);
        return information;
    }
}
