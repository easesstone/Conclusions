# What do I Get The Year
1，Spark（Simple）  
2，Elastic(More than Middle)  
3，Linux(Upper)   
4，Hive(Less Than Simple)  
5，Hadoop（More Than Simple）  
6，Drill（More Than Simple）  
7，phoenix（More Than Simple）  
8，Hbase（Middle）  
9, Flume （Simple）  
10，Java（Middle）  
11，Kafka(Simple)   
12，RockMq(Less Than Simple)  
13，Docker（Nothing）  
14，Jenkins(Upper)  
15，Azkaban  
16, OpenStack and CEPH 

# Conclusion in 2018 
分佈式搜索，多線程客戶端搜索，1000萬的10秒內的實時搜索,集群分布式，多小集群模式  
集群扩容（多客户端扩容）
 
# Conclusion in 2017
1，从零开始参与大数据项目架构，提供源码管理，编译，项目发布支撑，方便组员间的相互协作开发，提高项目管理的效率。  
2，主持大数据开源集群搭建脚本规范和自动化部署安装，给大数据服务提供基础组件服务，自动化流程，减少项目部署时间。  
3，参与其中多个大数据服务模块的设计，代码编写，不断提高静态信息库以图搜图效率。  
4，参与大数据各个组件调研，不断优化大集群的项目架构，在elasticsearch、hbase、shell脚本、自动化管理工具方面涉略较深，支撑现阶段的整个大数据服务架构。  
5，处理承担组内脚本编写，代码测试，项目发布遇到的问题，给出合理的不断优化的解决方案。  
6，负责对新入组成员的shell脚本，项目架构，大数据组件，构建和项目管理工具的使用培训，提升整个团队的技能。  
7，多个版本迭代过程中，不断地进行优化代码设计和项目架构，目前整个大数据服务，个人负责的模块性能稳定，效率不断得到提升。  
(备注: 大数据开发工程师，面试题)
（研读代码，总结整个项目架构，）  
use whitoud doubt  
8，前期代码没有设计好，几乎所有的时间都花费在调试和Bug 修复上


# Conclusion in 2016
1，熟悉Linux操作和Shell 脚本。  
2，熟悉jenkins 搭建，项目工程管理。  
3，负责Spark 大数据编译测试环境。  
4，熟练掌握maven 工程的搭建和构建。  
5，熟练掌握git 的基本使用，以及使用git 开发的流程。  
6，熟练掌握svn 工具的使用。  
7，对大数据的基本概念比如hdfs,yarn，spark,hive，zookeeper，hbase等有一定的理解。  


# 集群部署安装
#### 1，[Spark Stanalone HA 模式搭建](AADocs/bigdata-docs/compute-components-docs/sparkbasic-docs/standalone.md)


# 规范代码
1，接口定义，对外接口，对内接口，对外提供统一的接口    
2，方法实现：提取公共实现代码、即工具类，松耦合，单一功能  
3，脚本格式规范，变量名统一命名，放在一处，不要写死  
4，项目模块，模块名定了之后，不要改，涉及到的地方很多，不易于维护  
5，模块分离，集群分离，ETL（Extract-Transform-Load 抽取转换加载）（数据采集集群），计算集群，存储集群，对外服务，负载均衡，成本  
6，避免过于沉重的操作，比如一次性操作很大的数据量，采取分而治之的方法  
7, 項目間的類關係圖，以及項目各個模塊實現的功能，需要大致地進行描述，方便維護和交接  
8，类具有单一职责功能  


#使用技巧
#### 1，[使用技巧-Java编程问题解决流程](AADocs/skill-docs/skill-howtosolvejavaprogrammingbug.md)
#### 2, [概要设计merge模块需求分析和概要设计](AADocs/skill-docs/skill-Outlinedesign.md)
#### 3, [使用技巧-elastic导出导入数据](AADocs/skill-docs/skill-elastic-data-import-and-export.md)
#### 4, [sql 使用小技巧](AADocs/skill-docs/skil-mysql-use.md)

#Sql 
#### [sql 连接的使用](AADocs/skill-docs/skill-mysql-relations.md)


