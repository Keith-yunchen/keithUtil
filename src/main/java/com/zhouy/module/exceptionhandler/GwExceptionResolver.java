package com.zhouy.module.exceptionhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常统一处理handler
 * @author:zhouy,date:20180104
 * @Version 1.0
 */
@Component
public class GwExceptionResolver implements HandlerExceptionResolver {
    Logger logger = LoggerFactory.getLogger(GwExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object handler, Exception ex) {
        logger.error("{}",new String[]{handler.toString(),ex.getMessage()},ex);
        ModelAndView modelAndView = new ModelAndView("forward:/xxx/error.do");
        return modelAndView;
    }
}
