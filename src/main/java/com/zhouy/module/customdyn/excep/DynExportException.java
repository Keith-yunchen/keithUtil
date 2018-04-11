package com.zhouy.module.customdyn.excep;

/**
 * 导出异常
 * @author:zhouy,date:20180110
 * @Version 1.0
 */
public class DynExportException extends Exception {

    public DynExportException(String message) {
        super(message);
    }

    public DynExportException(Exception e) {
        super(e);
    }
}
