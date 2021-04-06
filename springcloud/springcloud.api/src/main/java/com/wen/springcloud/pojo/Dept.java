package com.wen.springcloud.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author 7wen
 * @description: 实体类 orm mysql -- dept对应的类  类表关系映射
 * @date 2021/4/6 15:33
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)// 链式写法
public class Dept implements Serializable {
    private Long deptno;
    private String dname;
    /**
     * 数据库存在于那个数据库的字段  一个服务对应一个数据库
     * @author 7wen
     * @date 2021/4/6 15:39
     * @param
     */
    private String db_source;


}
