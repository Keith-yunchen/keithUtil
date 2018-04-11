package com.zhouy.module.customdyn.file.util;

import java.io.*;
import java.net.URL;

/**
 * 文件操作类
 *
 * @author:zhouy,date:20170706
 * @Version 1.0
 */
public class FileOperate implements IFileOperate {
    @Override
    public String getFileContext(String path) {
        BufferedReader br = null;
        StringBuilder strBuilder = new StringBuilder();
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            String sLine = "";
            while ((sLine = br.readLine()) != null) {
                strBuilder.append(sLine);
                strBuilder.append("\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return strBuilder.toString();
    }

    @Override
    public boolean createFile(String fname, String content, String encoding, String path) {
        boolean flag = true;
        String filePath = path + File.separator + fname;
        File file = new File(filePath);
        BufferedWriter out = null;
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdir();
            }
            if(file.exists()){
                file.delete();
            }
            file.createNewFile();
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file), encoding));
            out.write(content);
        }catch (Exception e){
            e.printStackTrace();
            flag = false;
        }finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    @Override
    public boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    @Override
    public InputStream getInputStreamByFilePath(String path) throws Exception {
        InputStream input = null;
        File file = new File(path);
        if (file.exists()) {
            input = new FileInputStream(file);
        }
        return input;
    }

    @Override
    public boolean renameFile(File sourceFile, String newName) throws Exception {
        String suf = sourceFile.getName().split("\\.")[1];//文件拓展名
        String newFilePath = sourceFile.getParent()+ File.separator+newName+"."+suf;
        File newFile = new File(newFilePath);
        if (!sourceFile.exists()) {
            throw new Exception("文件不存在");
        }
        return sourceFile.renameTo(newFile);
    }

    /**
     * 通过文件名获取resource中的文件路径
     * @param fileName
     * @return
     */
    @Override
    public String getResourceFilePath(String fileName) {
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        return resource.getPath();
    }
}
