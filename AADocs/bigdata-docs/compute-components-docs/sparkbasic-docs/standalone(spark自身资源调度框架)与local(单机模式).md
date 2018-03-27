```
1. Local
Local模式即单机模式，如果在命令语句中不加任何配置，则默认是Local模式，
在本地运行。这也是部署、设置最简单的一种模式


2. Standalone
Standalone是Spark自身实现的资源调度框架。如果我们只使用Spark进行大数据计算，
不使用其他的计算框架（如MapReduce或者Storm）时，就采用Standalone模式。
Standalone模式的部署比较繁琐，需要把Spark的部署包安装到每一台节点机器上，
并且部署的目录也必须相同，而且需要Master节点和其他节点实现SSH无密码登录。
启动时，需要先启动Spark的Master和Slave节点。
```