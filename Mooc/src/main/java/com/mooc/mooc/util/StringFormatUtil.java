package com.mooc.mooc.util;

public class StringFormatUtil {
    // 仅对字符串末尾去除空格及空行
    public static String trimEnd(String str) {
        if(str == null) return null;
        int len = str.length();
        int st = 0;
        char[] val = str.toCharArray();
        while ((st < len) && (val[len - 1] <= ' ')) {
            len--;
        }
        return ((st > 0) || (len < str.length())) ? new String(val).substring(st, len) : new String(val);
    }
}
