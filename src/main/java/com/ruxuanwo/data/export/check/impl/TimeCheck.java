package com.ruxuanwo.data.export.check.impl;

import com.ruxuanwo.data.export.check.Check;
import com.ruxuanwo.data.export.check.Client;
import com.ruxuanwo.data.export.core.Information;
import com.ruxuanwo.data.export.core.Parameter;
import com.ruxuanwo.data.export.enums.RecordStateEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

/**
 * 时间格式效验校验
 *
 * @author ruxuanwo
 */
@Component("timeCheck")
public class TimeCheck implements Check {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeCheck.class);

    @Override
    public Information validate(Client client, Parameter parameter) {
        Information information = new Information();
        if (StringUtils.isEmpty(parameter.getData()) || checkTimeFormat(parameter.getData())){
            information.setType(parameter.getType());
            information.setState(RecordStateEnum.RIGHT.getCode());
            client.startValidate();
        }else {
            information.setMsg(parameter.getExcelName() + "格式不符合");
            information.setType(parameter.getType());
            information.setState(RecordStateEnum.ERROR.getCode());
        }
        return information;
    }

    private static boolean checkTimeFormat(String time){
        boolean flag = true;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(getFormat(time));
            sdf.setLenient(false);
            sdf.parse(time);
        } catch (Exception e){
            LOGGER.error("时间格式效验异常， time = {}, exception = {}", time, e.getMessage());
            flag = false;
        }
        return flag;
    }

    private static String getFormat(String time){
        //判断年月日还是年月日时分秒的格式
        String[] split = time.split(" ");
        if (split.length > 1){

        }else {

        }
        String timeWithoutChar = time.replaceAll("[/\\-.: ]", "");
        int length = timeWithoutChar.length();
        switch (length){
            //78代表年月日，其余都按年月日时分秒
            case 6:
            case 7:
            case 8:
                return "yyyyMMdd";
            default:
                return "yyyyMMddHHmmss";
        }
    }


}
