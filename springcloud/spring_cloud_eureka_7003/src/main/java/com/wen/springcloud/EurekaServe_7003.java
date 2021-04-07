package com.wen.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author 7wen
 * @description: TODO
 * @date 2021/4/7 14:01
 */
@SpringBootApplication
@EnableEurekaServer //服务端启动类 允许别人注册进来
public class EurekaServe_7003 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServe_7003.class, args);
    }
}
