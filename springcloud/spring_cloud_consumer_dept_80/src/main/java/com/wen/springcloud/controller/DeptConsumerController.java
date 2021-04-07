package com.wen.springcloud.controller;

import com.wen.springcloud.config.ConfigBean;
import com.wen.springcloud.pojo.Dept;
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
    //消费者该怎么调用生产者的服务呢?
    //注册的作用就在此
    //RestFul风格 -> 在springboot中都有一个template模板,对应的请求 -> RestTemplate直接调用(restTemplate的简单请求模板
    //注册就使用过RestTemplate
    //使用RestTemplate有三个参数  (url Class<T> responseType)
    @Autowired
    RestTemplate restTemplate;

    //ribbon -> 通过服务名来访问
//    private static final String REST_URL_PREFIX = "http://localhost:8001";
    private static final String REST_URL_PREFIX = "http://SPRINGCLOUD-PROVIDER-DEPT";

    //参数请求可以带在url中可以单独参数
    @RequestMapping("/consumer/dept/get/{id}")
    public Dept get(@PathVariable("id") Long id) {
        return restTemplate.getForObject(REST_URL_PREFIX + "/dept/query/" + id, Dept.class);
    }

    @RequestMapping("/consumer/dept/getAll")
    public List<Dept> getAll() {
        return restTemplate.getForObject(REST_URL_PREFIX + "/dept/queryAll", List.class);
    }

    @RequestMapping("/consumer/dept/add")
    public boolean add(Dept dept) {
        return restTemplate.postForObject(REST_URL_PREFIX + "/dept/add", dept,Boolean.class);
    }

}
