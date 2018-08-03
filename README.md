# 自我学习dubbo 模仿写一个吧  临时笔记
开始于2018.7.10

参考至《架构探险-从零开始写分布式服务框架》一书

### Rpc-test 工程
1. 先简单写了一下RMI rpc框架的方法 写了一个调用样例

### rpc-demo 一些基础的功能编写
1. [+]demo中schema 自定义xml文件 
2. 其他的是一个简单的RPC框架



简单运行原工程

https://www.cnblogs.com/bigdataZJ/p/dubbo-hello-world.html

##
dubbo 有点复杂 不是很理解 换个简单的先
rpc-mini
## rpc-shrowd 正式开始 
dubbo 全都被替换成 shrowd

### 核心的功能 ServerBean
ApplicationListener
可以监听到所用通过applicationContext.publistEvent(ApplicationEvent event))发布的事件,在spring启动时bean初始化完成时会调用publistEvent发布时间,只要是实现了ApplicationListener接口的类都可以接收到时间并作出响应

BeanNameAware 通过这个Bean可以获取到自己在容器中的名字
### 服务的暴露 是 onApplicationEvent 监听函数


## 整个学习的流程
### 一、自定义xml读取到配置文件
1. 关键的两个类 NamespaceHandlerSupport BeanDefinitionParser

### 二、




https://blog.csdn.net/lz710117239/article/details/74996915 服务注册