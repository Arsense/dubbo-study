## RPC分布式框架 DUBBO 学习

开始于2018.7.10

参考至《架构探险-从零开始写分布式服务框架》一书

## 工程结构 
### 一、rpc-test 工程
 
功能拆分学习功能
#### (1)自定义读取配置xml功能实现
#### (2)最简单的RPC实现
#### (3)各种输入流学习
目录在 stream 下
- BufferWriter流
- DataStream 流  原生数据的读写，用与网络数据传输
- FileStream
- ObjectStream 常用于Java对象的序列化或者网络数据的读写

#### (4)各种序列化学习
serializer 序列化demo

#### (5) NIO 
- CharBuffer
- Channel 通道
- nio B/C实现
- nio2 Path特性
- netty demoOK
### 二、rpc-mini工程 RPC分布式框架主体

进度 
1. 自定义xml配置文件读取解析
2. Netty的启动配置(部分)
3. zookeeper 完成注册 

### 三 学习笔记
- 一、自定义xml读取到配置文件
1. 关键的两个类 NamespaceHandlerSupport BeanDefinitionParser

-  核心的功能 ServerBean
ApplicationListener
可以监听到所用通过applicationContext.publistEvent(ApplicationEvent event))发布的事件,在spring启动时bean初始化完成时会调用publistEvent发布时间,只要是实现了ApplicationListener接口的类都可以接收到时间并作出响应
BeanNameAware 通过这个Bean可以获取到自己在容器中的名字
- 服务的暴露 是 onApplicationEvent 监听函数


### 四 参考资料

- 服务注册 
https://blog.csdn.net/lz710117239/article/details/74996915

- 简单运行dubbo原工程
https://www.cnblogs.com/bigdataZJ/p/dubbo-hello-world.html