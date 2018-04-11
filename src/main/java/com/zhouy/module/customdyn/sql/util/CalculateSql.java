package com.zhouy.module.customdyn.sql.util;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 计算sql的单行长度是否超过最大值
 *
 * @author:zhouy,date:20171126
 * @Version 1.0
 */
public class CalculateSql {
    private final int MAX_LEN = 2499;//sql单行的限制只能支持2499个字符
    private final String SPLIT_SYMBOL_SPA = " ";
    private final String SPLIT_SYMBOL_COMMOA = ",";

    private String overLongSql;//需要做计算的sql
    private List<String> lineOfSql = Lists.newArrayList();//计算单行的数据

    public CalculateSql(String _lineOfsql) {
        this.overLongSql = _lineOfsql;
        excuteCalculate(overLongSql);
    }

    private void excuteCalculate(String sql) {
        int length = sql.getBytes().length;
        if(length>MAX_LEN){
            int halfIndex = length % 2 == 0 ? length / 2 : (length - 1) / 2;
            String temp = sql.substring(0, halfIndex);
            String spiltSybol = SPLIT_SYMBOL_SPA;
            if(temp.indexOf(SPLIT_SYMBOL_SPA)==-1){//如果字符串中不存在空格则用逗号切割
                spiltSybol = SPLIT_SYMBOL_COMMOA;
            }
            int halfSpaIndex = temp.lastIndexOf(spiltSybol);
            String halfLine = sql.substring(0, halfSpaIndex+1);//前半sql
            String behindLine = sql.substring(halfSpaIndex+1);//后半sql
            if(halfLine.getBytes().length>MAX_LEN){
                excuteCalculate(halfLine);
            }else {
                lineOfSql.add(halfLine);
            }
            if(behindLine.getBytes().length>MAX_LEN){
                excuteCalculate(behindLine);
            }else {
                lineOfSql.add(behindLine);
            }
        }else{
            lineOfSql.add(sql);
        }
    }

    public String dealOverLengthSql(){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < lineOfSql.size(); i++) {
            String lineSql = lineOfSql.get(i);
            if(lineSql.length()>MAX_LEN){
                continue;
            }
            if(builder.length()>0){
                builder.append("\r\n");
            }
            builder.append(lineSql);
        }
        lineOfSql.clear();
        lineOfSql = null;
        return builder.toString();
    }


}
