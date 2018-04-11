package com.zhouy.module.customdyn.excep;

import org.apache.log4j.Logger;

/**
 * 动态导出实现uncatchexception
 * @author:zhouy,date:20170719
 * @Version 1.0
 */
public class DynExportExceptionHandler implements Thread.UncaughtExceptionHandler {
    Logger logger = Logger.getLogger(DynExportExceptionHandler.class);
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        logger.error(e);
    }
}
