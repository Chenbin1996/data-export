package com.ruxuanwo.data.export.conversion.impl;

import com.ruxuanwo.data.export.conversion.AbstractConversion;
import com.ruxuanwo.data.export.conversion.Conversion;
import com.ruxuanwo.data.export.core.Information;
import com.ruxuanwo.data.export.core.Parameter;
import com.ruxuanwo.data.export.exception.ConversionException;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: ruxuanwo
 * @Date: 2018/8/28/0028 8:42
 */
@Component("nameConversion")
public class NameConversion extends AbstractConversion implements Conversion {

    @Override
    public Information conversion(Parameter parameter) {
        return success(parameter.getType(), this.getName(parameter.getData()));
    }

    private final String getName(String name){
        if (name == null || "".equals(name)){
            return "";
        }
        char[] nameChar = name.trim().toCharArray();
        String[] src;
        //设置汉字拼音输出的格式
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);

        String newName = "";
        int len = nameChar.length;
        try {
            for (int i = 0; i < len; i++) {
                //判断是否为汉字字符
                if (Character.toString(nameChar[i]).matches("[\\u4E00-\\u9FA5]+")){
                    src = PinyinHelper.toHanyuPinyinStringArray(nameChar[i], format);
                    newName += src[0];
                }else {
                    // 如果不是汉字字符，直接取出字符并连接到字符串newName后
                    newName += Character.toString(nameChar[i]);
                }
            }
        }catch (BadHanyuPinyinOutputFormatCombination e){
            throw new ConversionException("名称转换失败：" + e.getMessage());
        }
        return newName;
    }

    /**
     * 利用正则表达式判断字符串是否包含数字
     * @param str
     * @return
     */
    public boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]");
        Matcher isNum = pattern.matcher(str);
        return isNum.find();
    }

}
