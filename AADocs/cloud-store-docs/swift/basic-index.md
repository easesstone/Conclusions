# 对象存储基础知识点
```
什么是OSD（对象存储设备）？
对象存储的优点是什么（具备高速访问和分布式存储特点）？
存储对象中通过什么对象方式访问对象（kv 对形式，对象唯一标志）？
OSD的主要功能是什么（数据存储，智能分布，元数据管理）？
对象存储文件系统的关键技术是什么？对象存储的数据有否大小限制？是否可以为10G（分布元数据，
并发的数据访问）？
```
## 对象存储概述
```
对象的大小是没有固定的，可以用来保存整个数据接口，比如一个10GB 大小的文件，Swift 以前是5G，
现在没有限制了。
```
## 什么是OSD
```
对象存储(Object-based Storage)是一种新的网络存储架构，基于对象存储技术的设备就是对象
存储设备（Object-based Storage Device） 简称OSD
```
## 对象存储结构（单个实例）
核心： 将数据读写（读写）和控制通路（元数据）分离，并且基于对象存储设备（Object-based Storage Device）
构建存储系统，OSD具有一定智能，可以自动管在其上面的数据分布。  
总的来说，对象存储设备由下面几个部分组成。   
对象，对象存储设备，元数据服务器，对象存储系统的客户端。
## 对象存储架构
### 对象
存储系统中最基本的单位，对象是由文件的数据和属性组合而成。
![hello](http://www.aboutyun.com/data/attachment/forum/201403/11/150649uo6pv6w6m66s66wp.jpg)
![world](http://www.aboutyun.com/data/attachment/forum/201403/11/154521v1rrlnn22srn1b19.jpg)

# 三种存储类型比较-文件、块、对象存储
```
问题：
1.说一下对象存储结构组成部分？
2.对象存储系统中数据存储的基本单位是什么？
3.对象是文件的数据和一组属性信息的组合，这些属性信息如何定义？
4.对象存储设备为什么具有一定的智能？
```
![demo0](http://www.aboutyun.com/data/attachment/forum/201403/11/142734qeib2i2ij8i8i531.jpg)
![demo](http://www.aboutyun.com/data/attachment/forum/201403/11/142735jnvfc1u11u1eyufu.jpg)








