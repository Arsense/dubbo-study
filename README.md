
## 项目进展
#### 开始于2018.7.10
#### 之前完成进度:
- 4346/161409  2.6%  代码总行数与百分比

### 
- dubbo-config-spring 启动通过Spring加载的配置
- dubbo-demo 就是简单的接口配置使用
- dubbo-rpc 远程调用协议 还有Netty什么的都在里面
- dubbo-remoting 分布式集群的类型 

## 项目学习步骤
- step1 创建demo 有provider和 consumer
- step2 自定义xml 从两者的配置xml中解析配置到相应的配置类中

- 思路 其实是先读取xml中的解析装载Bean基本属性 然后利用Spring的事件函数生成相应的实例并初始化等等 接着通过启动完成的监听事件函数来运行


#### 重构进度
- 18.11.26  0/161409 0%
- 18.12.6   3114/161409 1.46%

2020年1.19重新开始学习
3468 1.19
6887 1.29  平均每天342行 表现30分
9585 2.17
ServiceConfigTest到了ProtocolTest前先ExtensionLoaderTest
## question
1 RPC与HTTP 本质区别 
2 RPC与HTTP不同场景什么时候更适用
3 @SPI 什么时候初始化扫描注解存入 存入的 key-value规则是？
取的时候都是在ExtensionLoader这个类里取的