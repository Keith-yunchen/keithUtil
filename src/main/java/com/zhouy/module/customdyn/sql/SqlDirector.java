package com.zhouy.module.customdyn.sql;

import com.zhouy.module.customdyn.sql.builder.SqlBuilder;
import com.zhouy.module.customdyn.sql.util.CalculateSql;

import java.util.Map;

/**
 * 封装sql的生成
 *
 * @author:zhouy,date:20180109
 * @Version 1.0
 */
public class SqlDirector {
    private SqlBuilder sqlBuilder;

    public SqlDirector(SqlBuilder sqlBuilder) {
        this.sqlBuilder = sqlBuilder;
    }

    /**
     * 生成SQL的封装
     * @return
     */
    public Map<String,String> getEndSql(){
        Map<String, String> map = sqlBuilder.builderSql();
        if(map!=null){
            //处理提交上来的sql单行过长处理
            for(String key : map.keySet()){
                String tempSql = map.get(key);
                map.put(key,new CalculateSql(tempSql).dealOverLengthSql());
            }
        }
        return map;
    }

    public String getSpoolJdbc(){
        return sqlBuilder.getSpoolJdbc();
    }
}
