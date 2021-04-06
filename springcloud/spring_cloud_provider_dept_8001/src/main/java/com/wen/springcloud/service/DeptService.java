package com.wen.springcloud.service;

import com.wen.springcloud.pojo.Dept;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 7wen
 * @description: TODO
 * @date 2021/4/6 16:29
 */
public interface DeptService {
    public boolean addDept(Dept dept);

    public Dept queryById(Long id);

    public List<Dept> queryAll();
}
