package com.zhouy.module.pdf;


import com.zhouy.module.pdf.bean.WordBean;

import java.util.Map;

/**
 * 执行转换器的封装对象
 *
 * @Copyright 2017 ShenZhen DSE Corporation
 * @Company:深圳东深电子股份有限公司
 * @author:zhouy,date:20171012
 * @Version 1.0
 */
public class ConverteInvoker {
    /** 处理器*/
    private AbstractConverterHandler handler;
    /** 生成word参数封装对象*/
    private WordBean wordBean;
    /** freemark生成word的参数*/
    private Map wordParams;

    public void setHandler(AbstractConverterHandler handler) {
        this.handler = handler;
    }

    /**
     * 执行转换并返回文件路径
     * @return
     */
    public String convert(){
        this.handler.setWordBean(wordBean);
        this.handler.setWordParams(wordParams);
        String result = null;
        try {
            result = this.handler.excuteConvert();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public WordBean getWordBean() {
        return wordBean;
    }

    public void setWordBean(WordBean wordBean) {
        this.wordBean = wordBean;
    }

    public Map getWordParams() {
        return wordParams;
    }

    public void setWordParams(Map wordParams) {
        this.wordParams = wordParams;
    }
}
