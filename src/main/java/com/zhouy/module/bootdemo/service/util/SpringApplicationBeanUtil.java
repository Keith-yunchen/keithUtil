package com.zhouy.module.bootdemo.service.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author:zhouy,date:20180328
 * @Version 1.0
 */
public class SpringApplicationBeanUtil implements ApplicationContextAware{
    public static ApplicationContext applicationContext = null;
    @Override
    public void setApplicationContext(ApplicationContext _applicationContext) throws BeansException {
        SpringApplicationBeanUtil.applicationContext = _applicationContext;
    }

    public static Object getBeanByName(String beanName){
        return SpringApplicationBeanUtil.applicationContext.getBean(beanName);
    }
}
