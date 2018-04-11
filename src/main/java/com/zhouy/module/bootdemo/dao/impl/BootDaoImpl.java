package com.zhouy.module.bootdemo.dao.impl;

import com.zhouy.module.bootdemo.dao.IBootDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 数据操作实现
 *
 * @author:zhouy,date:20180313
 * @Version 1.0
 */
@Repository("bootDao")
public class BootDaoImpl implements IBootDao {
    @Resource
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, Object>> getData() {
        String sql = "select * from wr_wpc_m m where m.wpc_cd like '4401002017%'";
        return jdbcTemplate.queryForList(sql);
    }
}
