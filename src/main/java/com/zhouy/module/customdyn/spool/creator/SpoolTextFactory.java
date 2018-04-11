package com.zhouy.module.customdyn.spool.creator;


import com.zhouy.module.customdyn.file.util.FileOperate;
import com.zhouy.module.customdyn.file.util.IFileOperate;
import com.zhouy.module.customdyn.spool.bean.SpoolText;

/**
 * 获取导出组件工厂类
 *
 * @author:zhouy,date:20170705
 * @Version 1.0
 */
public class SpoolTextFactory {
    private static final SpoolText SPOOL_TEXT ;

    static {
        IFileOperate fileOperate = new FileOperate();
        String batPath = fileOperate.getResourceFilePath("spool/spool.bat");
        String sqlPath = fileOperate.getResourceFilePath("spool/spool.sql");
        String batText = fileOperate.getFileContext(batPath);
        String sqlText = fileOperate.getFileContext(sqlPath);
        SPOOL_TEXT = new SpoolText(batText,sqlText);
        fileOperate = null;//对象销毁
    }

    /**
     * 将spoolText对象复制返回，后面起缓存sql，bat的文本作用
     * @return
     */
    public static SpoolText createSpoolText() throws CloneNotSupportedException {
        return SPOOL_TEXT.clone();
    }
}
