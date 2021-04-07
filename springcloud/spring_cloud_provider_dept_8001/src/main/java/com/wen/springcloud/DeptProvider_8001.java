package com.wen.springcloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author 7wen
 * @description: TODO
 * @date 2021/4/6 16:37
 */
@SpringBootApplication
@MapperScan("com.wen.springcloud.dao")
@EnableEurekaClient //cs架构 将客户端注册到注册中心中
@EnableDiscoveryClient //配置服务发现
public class DeptProvider_8001 {
    public static void main(String[] args) {
        SpringApplication.run(DeptProvider_8001.class, args);
    }
}
