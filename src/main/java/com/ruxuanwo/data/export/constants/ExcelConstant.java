package com.ruxuanwo.data.export.constants;

import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * @Author: ruxuanwo
 * @Date: 2018/5/4/0003 11:37
 */
public class ExcelConstant {

    /**
     * 非空校验ID
     */
    public static String NOT_NULL_ID = "53e8fa0381e011e9bf8674d435bc8d5b";

    /**
     * excel字体格式
     */
    public static String FONTNAME = "微软雅黑";

    /**
     * excel字体格式
     */
    public static String SONG_TYPE = "宋体";

    /**
     * excel字体格式
     */
    public static String HUAWENSONG_TYPE = "华文中宋";
    /**
     * excel红颜色
     */
    public static short COLOR_RED = IndexedColors.RED.getIndex();
    /**
     * excel黑颜色
     */
    public static short COLOR_BLACK = IndexedColors.BLACK.getIndex();

    /**
     * 设置默认字号
     */
    public static final short DEFAULT_FONTHEIGHT = 12;

    /**
     * 设置内容字号
     */
    public static final short BODY_FONTHEIGHT = 11;

    /**
     * 设置标题字号
     */
    public static final short HEAD_FONTHEIGHT = 20;



}
