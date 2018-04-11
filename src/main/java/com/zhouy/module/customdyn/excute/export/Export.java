package com.zhouy.module.customdyn.excute.export;

import com.zhouy.module.customdyn.excep.DynExportException;
import com.zhouy.module.customdyn.excep.DynExportExceptionHandler;
import com.zhouy.module.customdyn.excute.IExport;
import com.zhouy.module.customdyn.file.util.FileOperate;
import com.zhouy.module.customdyn.util.DateUtil;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * 导出的操作实现类
 *
 * @author:zhouy,date:20180109
 * @Version 1.0
 */
public class Export implements IExport {
    Logger logger = Logger.getLogger(Export.class);

    private String csvName;//导出的文件名称
    private String batPath;//存放bat的路径
    private String csvPath;//导出csv路径存放文件夹，由调用者生成传入，可能存在分割文件需要创建父文件夹的场景
    private CountDownLatch latch;

    public Export(String csvName, String csvPath, String batPath, CountDownLatch latch) {
        this.csvName = csvName;
        this.csvPath = csvPath;
        this.batPath = batPath;
        this.latch = latch;
    }

    @Override
    public void export() throws DynExportException {
        Thread.currentThread().setName(csvName);
        if(batPath == null){
            throw new DynExportException("脚本路径不存在");
        }
        File cmdFile = new File(batPath);
        Process ps = null;  //Process可以控制该子进程的执行或获取该子进程的信息。
        ProcessBuilder pb = new ProcessBuilder();
        pb.directory(cmdFile);
        try {
            //调用bat命令传入两个参数 第一个参数是bat调用sql的文件名 第二个是导出的文件全路径
            if (!new File(csvPath).exists()) {
                new File(csvPath).mkdir();
            }
            pb = pb.command("cmd"," /c spool.bat spool.sql "+csvPath+ File.separator+csvName+".csv");
            ps = pb.start();
            dealProcessBuilderMessage(ps);

            int ev = ps.waitFor();  //接收执行完毕的返回值
            if (ev == 0) {// 导出成功处理
                logger.info("date:"+ DateUtil.date2String(new Date(),DateUtil.FORMAT)+"--->导出成功："+csvPath+ File.separator+csvName+".csv");
            } else {
                logger.info("date:"+ DateUtil.date2String(new Date(),DateUtil.FORMAT)+"--->导出失败："+csvPath+ File.separator+csvName+".csv");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            logger.info("date:"+ DateUtil.date2String(new Date(),DateUtil.FORMAT)+"--->导出失败："+ csvPath + File.separator+csvName+".csv");
            Thread.currentThread().interrupt();//终止线程
            throw new DynExportException(e);
        }finally {
            if(cmdFile.exists()){//删除动态生成的脚本
                new FileOperate().deleteDir(cmdFile);
            }
            ps.destroy();  //销毁子进程
            ps = null;
        }
    }

    private void dealProcessBuilderMessage(Process ps) throws IOException {
        logger.info("start dealProcess");
        String line=null;
        StringBuilder infoMsg=new StringBuilder();
        StringBuilder errorMsg=new StringBuilder();
        //读取正确执行的返回流
        BufferedReader info=new BufferedReader(new InputStreamReader(ps.getInputStream(), Charset.forName("GBK")));
        logger.info("end dealProcess");
        while((line=info.readLine())!=null) {
            infoMsg.append(line).append("\n");
        }
        logger.info("执行bat的输出信息"+infoMsg);

        //读取错误执行的返回流
        BufferedReader error=new BufferedReader(new InputStreamReader(ps.getErrorStream(), Charset.forName("GBK")));
        while((line=error.readLine())!=null) {
            errorMsg.append(line).append("\n");
        }
        logger.error("执行bat的错误信息---->"+errorMsg);
    }

    @Override
    public Object call() throws Exception {
        Thread.currentThread().setUncaughtExceptionHandler(new DynExportExceptionHandler());
        boolean flag = true;
        try {
            export();
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            throw e;
        }finally {
            latch.countDown();
        }
        return flag;
    }
}
