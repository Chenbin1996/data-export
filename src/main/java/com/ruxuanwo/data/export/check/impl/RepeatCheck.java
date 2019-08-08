package com.ruxuanwo.data.export.check.impl;

import com.ruxuanwo.data.export.check.Check;
import com.ruxuanwo.data.export.check.Client;
import com.ruxuanwo.data.export.core.Information;
import com.ruxuanwo.data.export.core.Parameter;
import org.springframework.stereotype.Component;

/**
 * @Author: ruxuanwo
 * @Date: 2018/5/17/0017 8:57
 */
@Component("repeatCheck")
public class RepeatCheck implements Check {
    @Override
    public Information validate(Client client, Parameter parameter) {
        //去重效验，待定是否启动，防止特定数据无法重复导入
        return null;
    }

    private static String returnRepeat(String str) {
        int len = str.length();
        int count = 0 , k;
        String data = "";
        char[] c = new char[len];
        for (int i = 0; i < len; i++) {
            c[i] = str.charAt(i);
        }
        for (int i = 0; i < len; i++) {
            k = i + 1;
            while (k < len - count) {
                if (c[i] == c[k]) {
                    for (int j = k; j < len - 1; j++) {
                        //出现重复字母，从k位置开始将数组往前挪位
                        c[j] = c[j + 1];
                    }
                    //重复字母出现的次数
                    count++;
                    k--;
                }
                k++;
            }
        }
        for (int i = 0; i < len - count; i++) {
            data += String.valueOf(c[i]);
        }
        return data;
    }

    public static String strRepeat(String str){
        String firstStr = str.substring(0, 1);
        String subText = str.substring(1);
        int index = subText.indexOf(firstStr);
        String repeat = str.substring(0, index + 1);
        boolean firstRepeat = str.substring(repeat.length()).contains(repeat);
        String data = firstRepeat ? str.substring(repeat.length()) : str;

        String complete = data.substring(0, data.length() - 1);
        String lastStr = data.substring(data.length() - 1, data.length());
        boolean lastRepeat = complete.substring(complete.length() - 1).equals(lastStr);
        return lastRepeat ? data.substring(0, data.length() - 1) : data;
    }


}
