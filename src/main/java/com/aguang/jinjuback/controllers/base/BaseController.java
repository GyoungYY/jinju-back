package com.aguang.jinjuback.controllers.base;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    /**
     * 获取当前用户Id
     * @return
     */
    public Integer getUserId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        return userId;
    }

}
