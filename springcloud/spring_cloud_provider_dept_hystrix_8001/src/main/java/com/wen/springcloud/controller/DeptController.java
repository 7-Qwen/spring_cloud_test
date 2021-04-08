package com.wen.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.wen.springcloud.pojo.Dept;
import com.wen.springcloud.service.DeptService;
import com.wen.springcloud.service.DeptServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 7wen
 * @description: 提供restful服务
 * @date 2021/4/6 16:32
 */
@RestController
public class DeptController {
    @Autowired
    private DeptService deptService;

    @GetMapping("/dept/get/{id}")
    @HystrixCommand(fallbackMethod = "HystrixGet")
    public Dept get(@PathVariable("id") Long id) {
        Dept dept = deptService.queryById(id);
        if (dept == null) {
            throw new RuntimeException("id->" + id + ":不存在该id的用户");
        }
        return dept;
    }
//声明熔断的方法
    public Dept HystrixGet(Long id) {
        return new Dept()
                .setDeptno(id)
                .setDname("该id不存在对应的用户")
                .setDb_source("不存在对应的数据库");
    }
}
