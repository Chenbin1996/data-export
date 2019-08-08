package com.ruxuanwo.data.export.conversion.impl;

import com.ruxuanwo.data.export.conversion.Conversion;
import com.ruxuanwo.data.export.core.Information;
import com.ruxuanwo.data.export.core.Parameter;
import com.ruxuanwo.data.export.enums.RecordStateEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间格式转换器
 *
 * @Author: ruxuanwo
 * @Date: 2018/8/20 14:20
 */
@Component
public class TimeFormatConversion implements Conversion {

    private static final String DATE_1 = "yyyy";
    private static final String DATE_2 = "yyyy.MM";
    private static final String DATE_3 = "yyyy.MM.dd";

    private static final String DATE_REX_1 = "[0-9]{4}";
    private static final String DATE_REX_2 = "[0-9]{4}\\.[0-9]{1,2}";
    private static final String DATE_REX_3 = "[0-9]{4}\\.[0-9]{1,2}\\.[0-9]{1,2}";

    private static final Logger logger = LoggerFactory.getLogger(TimeFormatConversion.class);

    @Override
    public Information conversion(Parameter parameter){
        Information information = new Information();
        String format = "";
        if(parameter.getData().matches(DATE_REX_1)){
            format = DATE_1;
        }else if (parameter.getData().matches(DATE_REX_2)){
            format = DATE_2;
        }else if(parameter.getData().matches(DATE_REX_3)){
            format = DATE_3;
        }else {
            logger.error("时间格式不符合要求");
            information.setMsg("时间格式请传: 年.月.日");
            information.setState(RecordStateEnum.ERROR.getCode());
            information.setData(null);
            return information;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        simpleDateFormat.setLenient(false);
        Date date;
        try {
            date = simpleDateFormat.parse(parameter.getData());
            String newDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
            information.setMsg(RecordStateEnum.RIGHT.getName());
            information.setState(RecordStateEnum.RIGHT.getCode());
            information.setData(newDate);
        } catch (ParseException e) {
            logger.error("时间格式转换出现异常");
            information.setMsg(parameter.getExcelName() + "格式错误");
            information.setState(RecordStateEnum.ERROR.getCode());
            information.setData(null);
        }
        return information;
    }

}
