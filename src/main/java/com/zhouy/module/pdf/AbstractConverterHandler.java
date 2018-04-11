package com.zhouy.module.pdf;

import com.zhouy.module.pdf.bean.WordBean;

import java.util.Map;

/**
 * 转换处理器的抽象类
 *
 * @author:zhouy,date:20171011
 * @Version 1.0
 */
public abstract class AbstractConverterHandler {
    /**
     * 生成word参数封装对象
     */
    private WordBean wordBean;
    /**
     * freemark生成word的参数
     */
    private Map wordParams;

    public Map getWordParams() {
        return wordParams;
    }

    public WordBean getWordBean() {
        return wordBean;
    }

    public void setWordBean(WordBean wordBean) {
        this.wordBean = wordBean;
    }

    public void setWordParams(Map wordParams) {
        this.wordParams = wordParams;
    }

    public abstract String excuteConvert() throws Exception;

}
