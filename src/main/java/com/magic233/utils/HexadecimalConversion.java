package com.magic233.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: HexadecimalConversion
 * Description:
 * date: 2021/7/26 14:19
 * author: zouyuan
 */
public class HexadecimalConversion {
    public double covert(String content) {
        //将全部的小写转化为大写
        content = content.toUpperCase();
        double number = 0;
        String[] HighLetter = {"A", "B", "C", "D", "E", "F"};
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i <= 9; i++) {
            map.put(i + "", i);
        }
        for (int j = 10; j < HighLetter.length + 10; j++) {
            map.put(HighLetter[j - 10], j);
        }
        String[] str = new String[content.length()];
        for (int i = 0; i < str.length; i++) {
            str[i] = content.substring(i, i + 1);
        }
        for (int i = 0; i < str.length; i++) {
            double tmp = map.get(str[i]) * Math.pow(16, str.length - 1 - i);
            number += tmp;
        }
        return number;
    }
}
