package com.zhouy.module.customdyn.mediator;


import com.zhouy.module.customdyn.file.util.IFileOperate;
import com.zhouy.module.customdyn.spool.ISpoolCreator;
import com.zhouy.module.customdyn.sql.SqlDirector;

/**
 * 封装导出所有对象
 *
 * @author:zhouy,date:20180109
 * @Version 1.0
 */
public abstract class AbstractDynExportMediator  {
    protected IFileOperate fileOperate;
    protected SqlDirector sqldirector;
    protected ISpoolCreator spoolCreator;

    /**
     * 执行导出操作
     */
    public abstract boolean doExport();
}
