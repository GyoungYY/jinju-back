package com.aguang.jinjuback.utils;

import java.util.UUID;

public class ConvertUtils {

    public static String getUUID() {
        return  UUID.randomUUID().toString().replace("-", "");
    }
}
