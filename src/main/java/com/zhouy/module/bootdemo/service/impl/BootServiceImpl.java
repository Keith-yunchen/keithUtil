package com.zhouy.module.bootdemo.service.impl;

import com.zhouy.module.bootdemo.dao.IBootDao;
import com.zhouy.module.bootdemo.service.IBootService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 服务实现
 *
 * @author:zhouy,date:20180313
 * @Version 1.0
 */
@Service("bootService")
public class BootServiceImpl implements IBootService{
    @Resource
    IBootDao bootDao;

    @Override
    public List<Map<String, Object>> getData() {
        return bootDao.getData();
    }
}
