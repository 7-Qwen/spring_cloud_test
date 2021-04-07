package com.wen.springcloud.controller;

import com.netflix.discovery.converters.Auto;
import com.wen.springcloud.pojo.Dept;
import com.wen.springcloud.service.DeptClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author 7wen
 * @description: 消费者controller
 * @date 2021/4/7 8:51
 */

@RestController
public class DeptConsumerController {
    //使用Feign完成远程过程调用
    @Autowired
    private DeptClientService deptClientService;

    @RequestMapping("/consumer/dept/get/{id}")
    public Dept get(@PathVariable("id") Long id) {
        return this.deptClientService.queryById(id);
    }

    @RequestMapping("/consumer/dept/list")
    public List<Dept> getAll() {
        return this.deptClientService.queryAll();
    }

    @RequestMapping("/consumer/dept/add")
    public boolean add(Dept dept) {
        return this.deptClientService.addDept(dept);
    }

}
