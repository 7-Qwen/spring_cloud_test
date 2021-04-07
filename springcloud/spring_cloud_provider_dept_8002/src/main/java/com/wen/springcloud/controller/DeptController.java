package com.wen.springcloud.controller;

import com.wen.springcloud.pojo.Dept;
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
    DeptServiceImpl deptService;
    //获取配置信息,获取微服务清单
    @Autowired
    DiscoveryClient discoveryClient;

    @PostMapping("/dept/add")
    public boolean addDept(Dept dept) {
        return deptService.addDept(dept);
    }

    @GetMapping("/dept/query/{id}")
    public Dept queryById(@PathVariable("id")Long id) {
        return deptService.queryById(id);
    }

    @GetMapping("/dept/queryAll")
    public List<Dept> queryAll() {
        return deptService.queryAll();
    }

    //注册微服务 获取一些信息
    @GetMapping("/dept/discovery")
    public Object discovery() {
        //获取微服务列表的清单
        List<String> services = discoveryClient.getServices();
        System.out.println("discovery:"+services);

        //得到一个具体的微服务信息 通过服务的名称
        List<ServiceInstance> instances = discoveryClient.getInstances("SPRINGCLOUD-PROVIDER-DEPT");
        instances.forEach(i->{
            System.out.println("host:"+i.getHost()+",uri"+i.getUri()+",port"+i.getPort());
        });
        return this.discoveryClient;
    }
}
