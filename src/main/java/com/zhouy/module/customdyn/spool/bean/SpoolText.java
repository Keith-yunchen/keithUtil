package com.zhouy.module.customdyn.spool.bean;

/**
 * 导出命令文本对象
 *
 * @author:zhouy,date:20170705
 * @Version 1.0
 */
public class SpoolText implements Cloneable {
    private String batText;
    private String sqlText;

    public SpoolText(String _batText, String _sqlText){
        this.batText=_batText;
        this.sqlText=_sqlText;
    }

    @Override
    public SpoolText clone() throws CloneNotSupportedException {
        SpoolText prototypeClass = null;
        try {
            prototypeClass = (SpoolText) super.clone();
        } catch (CloneNotSupportedException e) {
            //获取导出组件失败
            e.printStackTrace();
            throw e;
        }
        return prototypeClass;
    }

    public String getBatText() {
        return batText;
    }

    public String getSqlText() {
        return sqlText;
    }

    public void setBatText(String batText) {
        this.batText = batText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }
}
