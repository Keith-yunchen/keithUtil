package com.zhouy.module.customdyn.mediator;


import com.zhouy.module.customdyn.excute.IExport;
import com.zhouy.module.customdyn.excute.export.Export;
import com.zhouy.module.customdyn.file.util.FileOperate;
import com.zhouy.module.customdyn.file.zip.ZipOperate;
import com.zhouy.module.customdyn.spool.creator.SpoolCreator;
import com.zhouy.module.customdyn.sql.SqlDirector;
import com.zhouy.module.customdyn.sql.builder.SqlBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * 封装导出操作
 *
 * @author:zhouy,date:20180110
 * @Version 1.0
 */
public class DynExportMediator extends AbstractDynExportMediator {
    private Logger logger = LogManager.getLogger(DynExportMediator.class);

    /** 导出csv文件存放路径*/
    private String csvPath;
    /** 生成zip文件之后的相对路径*/
    private StringBuilder zipRelativePath = new StringBuilder();
    private ConcurrentHashMap<String,String> keyNameMap = new ConcurrentHashMap();

    /**
     * 传入sql的构造器生成sql
     * @param sqlBuilder
     */
    public DynExportMediator(SqlBuilder sqlBuilder, String csvPath) {
        fileOperate = new FileOperate();
        sqldirector = new SqlDirector(sqlBuilder);
        spoolCreator = new SpoolCreator();
        this.csvPath = csvPath;
    }

    public void initCsvPath(){
        String uuid = UUID.randomUUID().toString();
        zipRelativePath.append(uuid);
        csvPath = csvPath+uuid;
    }

    /**
     * 执行导出
     */
    @Override
    public boolean doExport() {
        boolean spoolFlag = true;
        initCsvPath();
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
        CompletionService<Boolean> completionService = new ExecutorCompletionService<Boolean>(executorService);
        //获取所有即将要执行的sql
        Map<String, String> endSql = sqldirector.getEndSql();
        int size = endSql.size();
        CountDownLatch latch = new CountDownLatch(size);
        try {
            int index = 0;
            for (String key : endSql.keySet()) {
                String bsql = endSql.get(key);
                //通过sql生成bat脚本
                String spoolBatPath = spoolCreator.createSpoolBat(bsql,sqldirector.getSpoolJdbc());
                //根据bat脚本路径，csv文件，csv存放路径，计数器
                IExport export = new Export(String.valueOf(index), csvPath, spoolBatPath, latch);
                keyNameMap.put(String.valueOf(index),key);
                index++;
                completionService.submit(export);
            }
            latch.await();
            for (int i = 0; i < size; i++) {
                Future<Boolean> future = completionService.poll();
                if (future != null) {
                    Boolean flag = future.get();//异常重新抛出被捕获
                    if (flag.equals(Boolean.FALSE)) {//若导出出现一个错误，则提示导出失败，
                        spoolFlag = false;
                        break;
                    }
                } else {
                    spoolFlag = false;
                    break;
                }
            }
        } catch (InterruptedException | ExecutionException |CloneNotSupportedException e) {
            logger.error(e);
            spoolFlag = false;
        } finally {
            executorService.shutdown();
            if (spoolFlag == false) {//导出失败 删除文件夹
                fileOperate.deleteDir(new File(csvPath));
            }
        }
        if (spoolFlag == true) {//导出成功，将数据进行压缩处理
            try {
                spoolFlag = exportAfter(csvPath);
            } catch (Exception e) {
                logger.error(e);
                spoolFlag = false;
            }
        }
        return spoolFlag;
    }

    /**
     * 文件生成之后
     */
    private boolean exportAfter(String csvPath) throws Exception {
        boolean flag = true;
        String uuid = UUID.randomUUID().toString();
        zipRelativePath.append(File.separator).append(uuid).append(".zip");
        String zipPath = csvPath + File.separator + uuid + ".zip";//将压缩文件存放的路径
        try {
            rename(csvPath);
        } catch (Exception e) {
            throw new Exception("文件重命名失败");
        }
        ZipOperate zipOperate = new ZipOperate(csvPath, zipPath);
        try {
            zipOperate.compress();
        } catch (Exception e) {
            throw new Exception("文件压缩失败");
        }
        return flag;
    }

    /**
     * 导出文件中文乱码 - 所有文件重命名
     *
     * @param filePath csv文件存放路径
     * @return
     */
    private boolean rename(String filePath) throws Exception {
        boolean flag = true;
        File dirFile = new File(filePath);
        if (dirFile.isDirectory()) {
            File[] files = dirFile.listFiles();
            for (File f : files) {
                if (f.isDirectory()) {
                    rename(f.getPath());
                } else {
                    String newName = keyNameMap.get(f.getName().split("\\.")[0]);
                    if (!fileOperate.renameFile(f, newName)) {
                        flag = false;
                        break;
                    }
                }
            }
        }
        return flag;
    }

    public String getZipPath() {
        return zipRelativePath.toString();
    }
}
