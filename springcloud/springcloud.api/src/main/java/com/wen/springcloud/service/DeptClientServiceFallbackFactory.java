package com.wen.springcloud.service;

import com.wen.springcloud.pojo.Dept;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 7wen
 * @description: TODO
 * @date 2021/4/8 13:38
 */

//降级
@Component
public class DeptClientServiceFallbackFactory implements FallbackFactory {


    @Override
    public DeptClientService create(Throwable throwable) {
        return new DeptClientService() {
            @Override
            public Dept queryById(Long id) {
                return new Dept()
                        .setDeptno(id)
                        .setDname("该id下没有对应的用户信息,客户端提供了服务降级,这个服务现在已经被关闭")
                        .setDb_source("该id没有对应的数据库");
            }
            @Override
            public List<Dept> queryAll() {
                return null;
            }

            @Override
            public boolean addDept(Dept dept) {
                return false;
            }
        };
    }
}
