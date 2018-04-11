package com.zhouy.module.customdyn.file.util;

import java.io.File;
import java.io.InputStream;

/**
 * 文件操作接口
 *
 * @author:zhouy,date:20170706
 * @Version 1.0
 */
public interface IFileOperate {
    /**
     * 获取文本字符串内容
     * @param path
     * @return
     */
    public String getFileContext(String path);

    /**
     * 通过文件名和路径创建文件
     * @param fname
     * @param path
     * @return
     */
    public boolean createFile(String fname, String content, String encoding, String path);


    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    public boolean deleteDir(File dir);

    /**
     * 通过文件路径获取文件 字节流
     * @param path
     * @return
     */
    public InputStream getInputStreamByFilePath(String path) throws Exception;

    /**
     * 文件重命名
     * @param sourceFile 需要重命名文件
     * @param newName 新文件名
     * @return
     */
    public boolean renameFile(File sourceFile, String newName) throws Exception;


    String getResourceFilePath(String fileName);
}
