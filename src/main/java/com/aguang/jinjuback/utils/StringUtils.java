package com.aguang.jinjuback.utils;

public class StringUtils {

    /**
     * 判断是否为空
     * @param obj
     * @return
     */
    public static boolean isBlank(Object obj) {
        if(obj == null) return true;
        if(obj instanceof String) {
            if("".equals((String)obj)) return true;
        }
        return false;
    }

    /**
     * 判断是否不为空
     * @param obj
     * @return
     */
    public static boolean isNotBlank(Object obj) {
        return !isBlank(obj);
    }

}
