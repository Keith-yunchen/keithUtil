package com.zhouy.module.customdyn.sql.builder;

import java.util.Map;

/**
 * sql构建器
 *
 * @author:zhouy,date:20180109
 * @Version 1.0
 */
public abstract class SqlBuilder {
    protected String spoolJdbc;

    public SqlBuilder(String spoolJdbc) {
        this.spoolJdbc = spoolJdbc;
    }

    public String getSpoolJdbc() {
        return spoolJdbc;
    }

    public void setSpoolJdbc(String spoolJdbc) {
        this.spoolJdbc = spoolJdbc;
    }

    /**
     * 生成在spool中执行的sql
     * @return 所有需要导出的
     */
    public abstract Map<String,String> builderSql();
}
