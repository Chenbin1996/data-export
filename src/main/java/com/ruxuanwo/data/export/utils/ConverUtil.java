package com.ruxuanwo.data.export.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author ruxuanwo
 * 2019-4-2 14:02
 */
public class ConverUtil {

    /**
     * 自定义转译
     */
    public static final String ESCAPE_WORD = "/";

    /**
     * mysql需要转译的字符
     */
    public static final String[] MYSQL_CONVER = { "%", "_"};

    /**
     * mysql转译
     * @param data
     * @return
     */
    public static String mysqlEscape(String data){
        if (StringUtils.isEmpty(data)){
            return data;
        }
        for (String conver : MYSQL_CONVER) {
            data = data.replaceAll(conver, ESCAPE_WORD + conver);
        }
        return data;
    }

}
