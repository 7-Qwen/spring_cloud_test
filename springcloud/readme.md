 

# 一般的流程

#1.导入依赖

```
xxx - starter - xxx 导入对应的依赖
```

#2.编写配置文件

#3.开启这个功能

```
  @Enablexxx 开启一个功能
```

#4.配置类

# Feign注意事项

```
- ## Feign使用过程中,映射的Mapping地址一定要和服务提供者的映射Mapping地址一致,否则就会映射失败

- ## Feign的内部使用的就是Ribbon
```

# API模块

```
API模块提供基础的pojo的对象,提供调用Feign的接口,Feign的作用就是与提供者产出的注册过的服务进行映射关联
```



# 提供者模块

```
提供者模块的作用就是controller->service->dao进行业务处理的地方,是接口的核心产出地,该模块要在yml中声明注册中心的地址,以及该模块也就是服务的名称,使用注册中心的注解即可向注册中心注册接口以便调用
```

# 消费者模块

```
消费者模块的作用是调用提供者产出的服务,也是对外提供业务接口的地方,消费者模块怎么调用服务呢?
- 1.使用ribbon的方式,通过拼接URL服务地址进行远程调用,使用RestTemplate提供的接口
- 2.使用Feign的方式,通过接口的方式进行远程调用,该方式需要在API模块中声明Feign的调用Service,然后在消费者的controller中调用FeignService,Feign的注意事项看上面
```





# SpringCloud一些注意事项

### 一、Hystrix之服务熔断&服务降级

```java
服务降级和服务熔断是两个维度考虑
    (1).服务熔断 服务端 单个接口请求异常进行熔断
    (2).服务降级 客户端 整个Feign接口出现服务降级 当某个服务熔断或者关闭之后 服务就不在被调用 此时在客户端,可以准备一个FallbackFactory返回一个缺省值(默认值) 从现象来看 整体的服务水平下降了,但是还能进行请求 比挂掉强

1.服务熔断 面向服务端 某个服务超时或者异常 引起熔断
    例子 请求了一个不存在的id时候会出现熔断,返回客户端熔断的信息,当用户再次请求正常内容的时候能再次访问(面向接口异常)
/**
 * @author 7wen
 * @description: 提供restful服务
 * @date 2021/4/6 16:32
 */
@RestController
public class DeptController {
    @Autowired
    private DeptService deptService;

    @GetMapping("/dept/get/{id}")
    @HystrixCommand(fallbackMethod = "HystrixGet")
    public Dept get(@PathVariable("id") Long id) {
        Dept dept = deptService.queryById(id);
        if (dept == null) {
            throw new RuntimeException("id->" + id + ":不存在该id的用户");
        }
        return dept;
    }
//声明熔断的方法
    public Dept HystrixGet(Long id) {
        return new Dept()
                .setDeptno(id)
                .setDname("该id不存在对应的用户")
                .setDb_source("不存在对应的数据库");
    }
}

2.服务降级 面向客户端	从整体网站请求负载考虑
	例子: 请求了一个不存在的id 服务器返回客户端熔断的异常,此时开启熔断机制,就算是正常请求也无法正常访问(面向类异常)
 	注意: 这里都在API模块中进行定义       
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

//在Feign的service接口中添加注解 fallbackFactory

@FeignClient(value = "SPRINGCLOUD-PROVIDER-DEPT",fallbackFactory = DeptClientServiceFallbackFactory.class)

	
```

### 二、网关zuul 

```yaml
网关的作用就是用来设置路由,用于在访问的时候先通过网关,网关再根据服务名称寻找到真正的接口

#网关的主要配置:

#设置服务器的端口
server:
  port: 9527

#注册名称
spring:
  application:
    name: spring_cloud_zuul

#eureka本身相关的配置(注册到eureka的配置
eureka:
  client:
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka/,http://eureka7002.com:7002/eureka/,http://eureka7003.com:7003/eureka/
  instance:
    instance-id: zuul9527.com #实例化id
    prefer-ip-address: true
    
#点击实例id时显示的内容
info:
  app.name: wen
  company.name: hq

#zuul相关的配置
zuul:
  routes:
  #这里的routes是一个map集合 可以自定义
  #xxx.serviceId: 这里填写要路由到的服务id或者名称
  #xxx.path: 要路由的路径
    mydept.serviceId: springcloud-provider-dept
    mydept.path: /mydept/**
    
    #如果有多个
    city.serviceId: xxx
    city.path: /city/**
  ignored-services: springcloud-provider-dept #不允许从这个服务访问,"*"表示隐藏全部的服务访问
  prefix: /wuhu #设置公共的前缀 localhost:9257/wuhu/xxx/...
```

### 三、springcloud-config

```yml
CS架构 C->S->Git
用户端连接配置服务中的配置 
配置服务端连接git上的配置

#配置服务端相当于一个中转站 修改一个 其他客户端都能获取到修改的东西

#配置服务端
声明远程仓库的地址,连接远程仓库
    server:
      port: 3344

    spring:
      application:
        name: springcloud_config_server
    #连接仓库
      cloud:
        config:
          server:
            git:
              uri: https://github.com/7-Qwen/spring_cloud_test.git

#用户端可以写一个controller  其中声明需要获取的配置的变量,通过注入的方式完成配置的同步
#配置用户端
bootstrap.yml 系统级配置->连接远程(需要配置服务端的uri 配置文件的环境 分支名称 配置的名称
	spring:
		cloud:
			config:
				name: config-dept
				lable: master
				profile: dev
				uri: http://localhost:3344
application.yml 用户级配置 ->声明自己的服务名称
	spring:
		application:
			name: springcloud-config-dept-8801
```

![image-20210409111455247](C:\Users\LENOVO\AppData\Roaming\Typora\typora-user-images\image-20210409111455247.png)

# 学习之路 任重而道远....