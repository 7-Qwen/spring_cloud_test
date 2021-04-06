package com.wen.springcloud.controller;

import com.wen.springcloud.pojo.Dept;
import com.wen.springcloud.service.DeptService;
import com.wen.springcloud.service.DeptServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
}
