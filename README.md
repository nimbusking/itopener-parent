# itopener-parent
整理spring boot的一些封装和demo(转载)

![](https://img.shields.io/badge/license-GPL-blueviolet.svg)



## 注意
源项目核心结构转自：https://gitee.com/itopener/springboot

**由于原作者并未在项目中引用任何开源许可证明，故而商业使用请联系原作者。**
本转载继承GPL（不提供闭源选择），本项目转载仅用于学习所用，目前维护也是仅为自己个人维护。
后续计划：升级springboot 2.0，修复相关BUG等日常维护扩充

### 代码结构说明
- **itopener-parent**：代码根目录，pom里统一定义所有module用到的jar包的版本（具体依赖哪些jar包由module自己定义，根pom只定义版本）
    - **demo-parent**：spring boot和spring cloud一些功能的示例代码；以及自己封装的spring boot starter和工具的使用示例
    - **itopener-framework**：基础框架模块。用于定义一些通用的类，比如：登录拦截器、权限拦截器（具体逻辑由自己项目内实现）、http请求的返回对象等
    - **itopener-static-parent**：前端静态资源模块
        - **itopener-amazeui**：amazeui前端静态资源，修改了一些间距和字体等样式，使页面更紧凑一些
        - **itopener-layui**：layui前端静态资源
    - **itopener-utils**：工具模块，一些公共的工具类。比如：时间操作、加密操作等
    - **spring-boot-starters-parent**：封装starter的根目录
        - **cache-redis-caffeine-spring-boot-starter-parent**：spring boot + spring cache两级缓存的封装（redis + caffeine）
        - **druid-spring-boot-starter-parent**：封装druid的starter
        - **druidstat-spring-boot-starter-parent**：封装druid监控统计的starter
        - **elasticjob-spring-boot-starter-parent**：封装elasticjob的starter。主要配置zk、简单任务、流任务、开启数据库存储
        - **eureka-spring-boot-starter-parent**：封装eureka的starter，可通过http请求设置应用的状态，达到停用启用应用节点的目的
        - **hadoop-spring-boot-starter-parent**：封装hbase操作的starter，添加依赖后注入HbaseTemplate即可使用
        - **lock-redis-spring-boot-starter-parent**：封装基于redis的分布式锁，可使用注解的方式。已解决一些极端情况下可能会出现锁不释放的问题
        - **lock-redisson-spring-boot-starter-parent**：封装redis分布式锁，基于redisson客户端工具包，使用注解的方式
        - **lock-zk-spring-boot-starter-parent**：封装基于zookeeper的分布式锁，可使用注解的方式
        - **ratelimiter-spring-boot-starter-parent**：封装基于guava RateLimiter的限流starter，包含查看和修改限流值的endpoint
        - **redisson-spring-boot-starter-parent**：封装redis的redisson客户端工具包的starter，包含spring cache的整合
        - **sequence-spring-boot-starter-parent**：封装分布式序列号的生成器，基于snowflake思想的实现，需要自定义实现IWorker的实现，以便获取应用节点的id
        - **stock-spring-boot-starter-parent**：库存扣减的starter
        - **tbschedule-spring-boot-starter-parent**：封装tbschedule的starter，在properties按照spring boot的格式配置zk即可
        - **zk-spring-boot-starter-parent**：封装zookeeper客户端的starter，使用curator客户端工具包
        - **zuul-ratelimiter-spring-boot-starter-parent**：spring cloud zuul网关路由限流配置
        - **zuul-route-spring-boot-starter-parent**：封装zuul动态路由的starter，包含基于redis、db、zk的三种方式
    - **tools-parent**：自定义工具的根目录
        - **tools-eureka-admin-parent**：eureka的应用节点管理工具，配合eureka-spring-boot-starter-parent使用，可以动态停用启用应用节点
        - **tools-generator**：基于mysql的自动生成model、mybatis mapper xml、dao的工具，用起来不是太方便，并且只适合特定的代码结构
        - **tools-log-appender-parent**：封装日志appender，目前只包含基于kafka的日志
        - **tools-redis-parent**：redis查看工具。可以查询redis集群信息、节点信息、连接终端等，也可以查询redis里的key，支持hash结构数据的查询
        - **tools-zookeeper-parent**：zookeeper查看工具，可以对zookeeper里的数据进行增删改查
        - **tools-zuul-ratelimiter-admin-parent**：Spring Cloud Zuul + RateLimiter网关限流的管理工具。可以通过页面对zuul限流配置进行增删改查操作，并刷新指定网关的限流配置
        - **tools-zuul-route-admin-parent**：zuul动态路由管理工具。配合zuul-route-spring-boot-starter-parent使用，可以动态配置路由规则，达到动态切流（分流）的效果

# LICENSE
Copyright © 2021-present nimbusking. This project is [GPL](https://github.com/nimbusking/itopener-parent) licensed.
backbone project is copy from [itopener](https://gitee.com/itopener/springboot)