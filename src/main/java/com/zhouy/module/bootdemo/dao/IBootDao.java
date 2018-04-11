package com.zhouy.module.bootdemo.dao;

import java.util.List;
import java.util.Map;

/**
 * 数据操作
 *
 * @author:zhouy,date:20180313
 * @Version 1.0
 */
public interface IBootDao {
    /**
     * 测试获取数据
     * @return
     */
    List<Map<String, Object>> getData();
}
