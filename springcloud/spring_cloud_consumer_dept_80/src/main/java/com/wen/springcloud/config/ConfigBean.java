package com.wen.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author 7wen
 * @description: 配置类
 * @date 2021/4/7 8:55
 */
/**
 * Configuration -> springboot注解 相当于 spring中的applicationContext.xml
 */
@Configuration
public class ConfigBean {
    /**
     * 相当于 <bean ...>
     * 托管于spring中,供别处autowired调用
     */
    //配置负载均衡实现RestTemplate
    @Bean
    @LoadBalanced // Ribbon
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}
