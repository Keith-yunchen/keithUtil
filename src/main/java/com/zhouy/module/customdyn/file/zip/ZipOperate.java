package com.zhouy.module.customdyn.file.zip;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 压缩文件操作
 *
 * @author:zhouy,date:20170713
 * @Version 1.0
 */
public class ZipOperate {
    private String sourcePath;//需要压缩的目录
    private String zipPath;//压缩后zip全路径

    public ZipOperate(String _sourcePath, String _zipPath){
        this.sourcePath = _sourcePath;
        this.zipPath = _zipPath;
    }

    public void compress() throws Exception {
        byte[] bufs = new byte[1024*10];
        File sourceFile = new File(sourcePath);
        File[] sourceFiles = sourceFile.listFiles();
        OutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            if(sourceFiles == null){
                throw new Exception("导出文件失败");
            }
            fos = new FileOutputStream(zipPath);
            zos = new ZipOutputStream(new BufferedOutputStream(fos));
            for(int i=0;i<sourceFiles.length;i++){
                //创建ZIP实体，并添加进压缩包
                ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());
                zos.putNextEntry(zipEntry);
                InputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    //读取待压缩的文件并写进压缩包里
                    fis = new FileInputStream(sourceFiles[i]);
                    bis = new BufferedInputStream(fis);
                    int line = 0;
                    while((line = bis.read(bufs)) != -1){
                        zos.write(bufs,0,line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(fis != null){
                        fis.close();
                    }
                    if(bis!=null){
                        bis.close();
                    }
                }
            }
            zos.flush();
            fos.flush();
        }catch (Exception e){
            e.printStackTrace();
            throw  e;
        }finally {
            try {
                if(zos != null){
                    zos.close();
                }
                if(fos != null){
                    fos.close();
                }
                if(sourceFile.exists()){
                    File[] files = sourceFile.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        if(files[i].exists() && files[i].getName().endsWith("csv")){
                            files[i].delete();//删除csv文件 只保留zip文件
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getZipPath() {
        return zipPath;
    }

    public void setZipPath(String zipPath) {
        this.zipPath = zipPath;
    }
}


