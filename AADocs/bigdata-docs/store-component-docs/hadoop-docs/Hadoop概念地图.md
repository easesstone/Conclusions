## Hadoop 发展史及生态圈
1，hadoop 的发展史，基于GFS,MAPREDUCE，BIGDATA---->Hive  
2，hadoop 生态圈。core,hdfs,mapreduce,hive,zookeeper.hbase,kafka，Flume，Sqoop,Mahout,Pig等  
3，hadoop 版本，并行的基线，1.x 和2.X  
## HDFS 
1，分布式文件系统以及与传统型数据库的比较  
2，hdfs 作用  
3，hdfs 原理（把文件分成小的block 存到不同的机器上,即datanode 上，namenode 维护整个文件系统，master slave 机制。）  
4，hdfs 的优缺点  
5，hdfs 架构，datanode,client,secodary nanode   
6，hdfs 读写流程  
7，hdfs shell  
8，hdfs 对外api
## MapReduce 
1，分布式计算框架并行计算模型（核心理念，移动计算而非移动数据，模型和原理，将数据切分成小的数据块集合，由各个节点进行平行处理，最后汇总)  
2，MapReduce 架构（JobTracker和TaskTracker,master/slaves机制）  
3，核心处理函数Map 和Reduce 函数  
4，MapReduce提交Job过程  

