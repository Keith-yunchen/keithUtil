package com.zhouy.module.customdyn.spool;

/**
 * 创建bat脚本
 *
 * @author:zhouy,date:20180110
 * @Version 1.0
 */
public interface ISpoolCreator {
    /**
     * 创建bat脚本之后返回 bat脚本的文件路径
     * @return bat脚本的路径
     */
    public String createSpoolBat(String sql, String jdbc) throws CloneNotSupportedException;
}
