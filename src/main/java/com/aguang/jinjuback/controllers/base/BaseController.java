package com.aguang.jinjuback.controllers.base;

import com.aguang.jinjuback.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取当前用户Id
     * @return
     */
    public Integer getUserId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if(StringUtils.isBlank(userId)) {
            userId = 333;
        }
        return userId;
    }

}
