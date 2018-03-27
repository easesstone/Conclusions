# 学习笔记
## 概念
Impala 是一个低延迟和高并发的的分析查询系统，提供交互式的SQL 查询。数据源有HDFS，HBase，Amazon Simple Storage Service(S3).  
Impala不能替代基于MapReduce的批处理框架，如Hive。基于MapReduce的Hive和其他框架最适用于长时间运行的批处理作业，例如涉及批处理Extract，Transform和Load（ETL）类型作业的工作。  
## 优点
提供SQL界面  
分布式查询  
处理apache Hadoop 中的大量数据  
在不同组件中共享数据文件，无需复制和导入导出步骤。
大数据的分析和处理系统，避免昂贵的建模和ETL(提取，转换，加载)
## Impala 组成和查询流程
### 组成
client 用于连接到Impala  
Hive Metastore（可以看成是数据源连接桥）  
Impala 进程（在DataNode 上运行，协调和执行查询）  
HBase 和 HDFS（数据源）  
### 查询大致流程
1，用户应用程序通过ODBC或者JDBC 客户端发送SQL 查询到集群中的任意一个Impala中。这个Impala作为查询的一个协调者。（Coordianting impalad）  
2，Impala解析查询，并且对其进行分析，并确定最佳方案。  
3，诸如HDFS 或者HBase 的实例，由本地的Impala（Native Impala） 进行访问以提供数据。  
4，每个Impalad 向协调Impalad 返回数据，协调Impala 把数据返回给客户端。  
  
 ## 主要特性
 Hive Query Language(HiveQL)  
 HDFS,HBase,S3(等数据源)  
 公共访问接口：ODBC, JDBC, Hue Beeswax, Impala 查询界面  
 Impala shell查询界面  
 Kerberos 身份认证  
 