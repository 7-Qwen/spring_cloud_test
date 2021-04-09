package com.wen.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author 7wen
 * @description: TODO
 * @date 2021/4/9 8:54
 */
@SpringBootApplication
@EnableZuulProxy //开启zuul的代理
public class ZuulApplication_9527 {
    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication_9527.class,args);
    }
}
