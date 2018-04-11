package com.zhouy.module.pdf.util;

import java.net.URL;

/**
 * 工具类
 * author:zhouy,date:2017/05/25.
 */

public class PdfUtil {

    /**
     * 通过文件名获取resource中的文件路径
     *
     * @param fileName
     * @return
     */
    public static String getResourceFilePath(String fileName) {
        ClassLoader classLoader = PdfUtil.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);
        return resource.getPath();
    }

    /**
     * 配置的文件的路径
     * @return
     */
    public static String getFilePath(){
        return null;
    }


}

