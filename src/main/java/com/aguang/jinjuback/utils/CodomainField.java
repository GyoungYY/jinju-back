package com.aguang.jinjuback.utils;

import java.lang.annotation.*;

/**
 * "值域"注解
 */
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