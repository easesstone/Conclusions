# CEPH 架构
CEPH 可以在一个系统中提供文件系统，块存储和对象存储。  
可以优化基础架构，管理海量的数据。
![ceph-architecture](http://docs.ceph.com/docs/master/_images/stack.png)

## CEPH 存储集群  
### 简介
基于RADOS的论文实现的无穷的可扩展的存储集群。  
一个存储集群包含以下两类进程。
OSDs Monitors  
![storage-cluster](http://docs.ceph.com/docs/master/_images/ditaa-4cf6d0983521ea66cd16f98b7ce624e6666eed77.png)  
OSDs 检视维护集群映射的主副本，监听和确保某些守护进程失败时集群的高可用。  
存储集群客户端从Monitor 中检索集群映射的副本。

一个Ceph的OSD守护进程检查自己的状态和其他OSD的状态并报告给监视器。  
集群不同于HDFS,HBASE 等集群，是去中央化的，没有主从架构，
即datanode 和namenode 之间类似的关系。  

### 数据存储流程  
如下图：  
![storage-process](http://docs.ceph.com/docs/master/_images/ditaa-518f1eba573055135eb2f6568f8b69b4bb56b4c8.png)  
接收到客户端传过来的数据（obj）后，CEPH 进行一系列的处理，最终以文件的形式，存储在磁盘中。
（经过CEPH的处理，减少文件的个数，同时存储相应的数据。）

存储以KV 对的形式进行存储。  
类似如下CEPH 文件系统中的文件：  
![kv-value](http://docs.ceph.com/docs/master/_images/ditaa-ae8b394e1d31afd181408bab946ca4a216ca44b7.png)  


### 可扩展和高可用  
1，高可用性  
使用CRUSH 算法，消除传统的集中式元数据管理，例如：NameNode，HMaster等。这种传统的
集中式元数据管理，容易产生单点故障的问题。CEPH 消除了集中式的网关，使客户端可以直
接和和CEPH OSD 守护进程进行交互。  
2，OSD守护进程在其他节点上创建对象副本，一确保数据的安全性和高可用性。
CEPH 还使用了一组监视器来确保高可用性。  
3，CRUSH 算法，可以是集群复制的数据受控，可扩展，分散布局。  

### 集群Map  
CEPH 通过客户端和OSD守护进程理解集群的拓扑结构。包括5个集群映射表：  
1，监视映射器。  
2，OSD映射。  
3，PG映射。  
4，CRUSH映射。  
5，MDS 映射。  
### 高可用性监听器  
```
```
### 高可用性认证
```
```
## 智能守护启用超大规模(消除集中式MetaStore瓶颈问题。)
```
集群处于一个对等的状态：
集群中的每个OSD进程都知道集群中的其他OSD 进程。  
这个的其中的好处：  
1，OSD 直接服务客户端：挺高并发的链接数。  
2，OSD成员和状态。  
3，数据清理：  
4，复制。  
```

## 动态集群管理  
OSD、归置组和对象之间的联系  
![object_storage](http://docs.ceph.com/docs/master/_images/ditaa-c7fd5a4042a21364a7bef1c09e6b019deb4e4feb.png)  
(吧啦吧啦，一大堆，CEPH 对象存储的一些概念和设计思想和算法)

## 等等一些集群架构相关的深入知识，看着费解，先搁着。

## CEPH 客户端  
### 简介
CEPH 包含一系列的服务接口，详见如下：  
块设备：*****  
对象存储：提供了一种Restful 风格的API接口，这些接口兼容Amazon 的S3 和
OpenStack Swift。（这些API，初步选定的是Amazon 的S3 对CEPH 的对象操作的java api。）  
文件系统：*******  

ceph 的经典架构：  
![classical-architecture](http://docs.ceph.com/docs/master/_images/ditaa-a116a4a81d0472ef44d503c262528e6c1ea9d547.png)  



