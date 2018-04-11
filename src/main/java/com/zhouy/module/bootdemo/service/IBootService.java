package com.zhouy.module.bootdemo.service;

import java.util.List;
import java.util.Map;

/**
 * boot测试服务接口
 *
 * @author:zhouy,date:20180313
 * @Version 1.0
 */
public interface IBootService {
    /** 测试从数据获取数据*/
    List<Map<String, Object>> getData();
}
