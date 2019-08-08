package com.ruxuanwo.data.export.conversion.impl;

import com.ruxuanwo.data.export.conversion.AbstractConversion;
import com.ruxuanwo.data.export.conversion.Conversion;
import com.ruxuanwo.data.export.core.Information;
import com.ruxuanwo.data.export.core.Parameter;
import com.ruxuanwo.data.export.utils.CryptoUtil;
import org.springframework.stereotype.Component;

/**
 * 加密转换
 * @author ruxuanwo
 */
@Component("encryptionConversion")
public class EncryptionConversion extends AbstractConversion implements Conversion {

    @Override
    public Information conversion(Parameter parameter) {
        return super.success(parameter.getType(), CryptoUtil.encode(parameter.getData()));
    }
}
