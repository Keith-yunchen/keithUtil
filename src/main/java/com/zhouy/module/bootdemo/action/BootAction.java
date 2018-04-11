package com.zhouy.module.bootdemo.action;

import com.zhouy.module.bootdemo.service.IBootService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 测试是否boot正常
 * @author:zhouy,date:20180312
 * @Version 1.0
 */
@RestController
public class BootAction {

    @Resource
    IBootService bootService;


    @GetMapping(value = "/")
    public String testAction(){
        List<Map<String, Object>> data = bootService.getData();
        System.out.println(data.toString());
        return "hello word";
    }
}
