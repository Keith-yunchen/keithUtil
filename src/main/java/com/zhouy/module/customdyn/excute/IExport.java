package com.zhouy.module.customdyn.excute;


import com.zhouy.module.customdyn.excep.DynExportException;

import java.util.concurrent.Callable;

/**
 * 执行导出的接口
 *
 * @author:zhouy,date:20180109
 * @Version 1.0
 */
public interface IExport extends Callable {
    /**
     * 导出操作
     */
    public void export() throws DynExportException;
}
