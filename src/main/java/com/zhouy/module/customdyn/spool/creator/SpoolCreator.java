package com.zhouy.module.customdyn.spool.creator;


import com.zhouy.module.customdyn.file.util.FileOperate;
import com.zhouy.module.customdyn.file.util.IFileOperate;
import com.zhouy.module.customdyn.spool.ISpoolCreator;
import com.zhouy.module.customdyn.spool.bean.SpoolText;

import java.text.MessageFormat;
import java.util.UUID;

/**
 * 创建bat脚本
 *
 * @author:zhouy,date:20180110
 * @Version 1.0
 */
public class SpoolCreator implements ISpoolCreator {
    @Override
    public String createSpoolBat(String sql, String jdbc) throws CloneNotSupportedException {
        String cmdPath = SpoolCreator.class.getResource("").getPath()+"temp_"+ UUID.randomUUID().toString();
        SpoolText spoolText = SpoolTextFactory.createSpoolText();
        String batText = MessageFormat.format(spoolText.getBatText(),jdbc);
        String sqlText = MessageFormat.format(spoolText.getSqlText(),sql);
        IFileOperate fileOperate = new FileOperate();
        boolean bflag = fileOperate.createFile("spool.bat",batText,"GBK",cmdPath);
        boolean sflag = fileOperate.createFile("spool.sql",sqlText,"GBK",cmdPath);
        if(bflag == true && sflag==true){
            return cmdPath;
        }
        return null;
    }
}
