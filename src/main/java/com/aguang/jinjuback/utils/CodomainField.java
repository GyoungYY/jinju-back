package com.aguang.jinjuback.utils;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
@Documented
public @interface CodomainField {
  
    /**
     * 字段名称 
     * @return 
     */  
    String name();
}