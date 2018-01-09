package com.aguang.jinjuback.configuration;

/**
 * 自定义异常类
 */
public class CustomException extends RuntimeException {

    public CustomException() {
        super();
    }

    public CustomException(String message) {
        super(message);
    }
}
