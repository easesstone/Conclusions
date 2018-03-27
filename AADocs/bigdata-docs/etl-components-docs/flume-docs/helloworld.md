## Flume 特点
```
分布式的，可靠，高可用的海量日记采集、聚合、传输系统。  
支持各种发送方的日记采集，即各种source。  
对数据进行简单处理，输出到各个数据接收方，即各种存储系统。  
整个过程的数据流由event 贯穿。event 由字节数组和header组成。 
source 获取数据流event， 然后把event 传入channel(缓冲区),
 然后sink 消费channel 中的event。
```
## Flume 可靠性
## Flume 可恢复性
还是靠Channel。推荐使用FileChannel，事件持久化在本地文件系统里(性能较差)。 
## Flume 架构  
```
flume的一些核心概念：
Agent        使用JVM 运行Flume。每台机器运行一个agent，
但是可以在一个agent中包含多个sources和sinks。
Client        生产数据，运行在一个独立的线程。
Source        从Client收集数据，传递给Channel。
Sink        从Channel收集数据，运行在一个独立线程。
Channel        连接 sources 和 sinks ，这个有点像一个队列。
Events        可以是日志记录、 avro 对象等。
```
![图片1](http://www.aboutyun.com/data/attachment/forum/201408/26/015536rufi6pmapcks6vmu.png)
![图片2](http://www.aboutyun.com/data/attachment/forum/201408/26/015537b84jaxujvllxj5ac.png)
![图片3](http://flume.apache.org/_images/UserGuide_image02.png)
