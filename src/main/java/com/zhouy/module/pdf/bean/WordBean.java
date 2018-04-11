package com.zhouy.module.pdf.bean;


/**
 * word封装属性对象
 *
 * @author:zhouy,date:20171011
 * @Version 1.0
 */
public class WordBean {
    /** 文件后缀*/
    public static final String WORD_SUFFIX = ".doc";
    /** HTML文件后缀*/
    public static final String HTML_SUFFIX = ".html";
    public static final String RELATIVE_PATH = null;

    /** 文件路径 可以为null*/
    private String filePath;
    /** 文件名字*/
    private String fileName;
    /** 模板名字*/
    private String fbName;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFbName() {
        return fbName;
    }

    public void setFbName(String fbName) {
        this.fbName = fbName;
    }
}
