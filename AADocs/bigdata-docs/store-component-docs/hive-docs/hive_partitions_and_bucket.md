# Hive 分区
```
按照Hive 表格中的某一列或者某几列进行分区，分区可以理解为文件系统上的文件夹。  
分区后，可以在查询的时候加载整张表格。提高查询效率，减少无谓的资源消耗。  

如下是创建分区的例子：（Linux 下的的\t 最好用的是tab键）
hive> create table  ptest(userid int) partitioned by (name string) row \
format delimited fields terminated by '\t';
OK
Time taken: 1.364 seconds
hive> show tables;
OK
mid_table
person_table
ptest
Time taken: 0.046 seconds, Fetched: 3 row(s)
hive> 


从例子中可以看出： 通过partitioned by 指定分区。
如果有多个分区，可以按照如下形式进行指定：
人脸动态库（最终表，存放小文件合并后数据）
------------------------------------------------------------------------------------------------------------------------
|                                                  person_table                                                                       |
------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------
|                                                                                                         | 分区字段   |
------------------------------------------------------------------------------------------------------------------------
|图片地址|特征值 | 性别   | 头发颜色  | 头发类型  | 帽子 | 胡子 | 领带 |  时间段  | 时间戳    | 搜索类型  |日期 |设备id|
------------------------------------------------------------------------------------------------------------------------
| ftpurl |feature| gender | haircolor | hairstyle | hat  | huzi | tie  | timeslot | exacttime | searchtype|date |ipcid |
------------------------------------------------------------------------------------------------------------------------
| string |string |  int   |    int    |     int   | int  |  int | int  |   int    | Timestamp |  string   |string|string|
------------------------------------------------------------------------------------------------------------------------
CREATE EXTERNAL TABLE IF NOT EXISTS default.person_table(
ftpurl        string,
feature       array<float>,
eyeglasses    int,
gender        int,
haircolor     int,
hairstyle     int,
hat           int,
huzi          int,
tie           int,
timeslot      int,
exacttime     Timestamp,
searchtype    string)
partitioned by (date string,ipcid string)
STORED AS PARQUET
LOCATION '/user/hive/warehouse/person_table';


分区数据加载
hive> dfs -ls /user/hive/warehouse/ptest/name=jack
    > ;
Found 1 items
-rwxr-xr-x   3 root supergroup         13 2017-12-20 09:29 /user/hive/warehouse/ptest/name=jack/ptest.txt
hive> dfs -cat /user/hive/warehouse/ptest/name=jack/ptest.txt
    > ;
111    ptest
hive> load data local inpath 'home/test/ptest.txt' into table ptest partition (name = 'jack');
Loading data to table default.ptest partition (name=jack)
OK
Time taken: 0.843 seconds
hive> select * from ptest;
OK
NULL	jack
1111	jack
2222	jack
3333	jack
Time taken: 0.193 seconds, Fetched: 4 row(s)
hive> 


查看分区信息：
hive> show partitions ptest;
OK
name=jack
Time taken: 0.066 seconds, Fetched: 1 row(s)
hive> 

```


# 分桶（分桶无法Load 数据，需要中间表）
```
分桶是相对分区来说更加细粒度的划分。分桶将整个分区或者表格中的某个列的某个数据数据值进行hash之后得到的值
进行区分。
例如如果对上诉类似的表格中按照name 属性分为3个桶，就是说对name 属性值的hash 值进行对3取模，按照取模结果进行
分桶。
如果模为0的放一个文件，模为1的放一个文件，模为2的放一个文件。

如下是例子：
hive> set hive.enforce.bucketing=true;
hive> set hive.enforce.bucketing;
hive.enforce.bucketing=true
hive> create table  btest ( id int, name string) clustered by(id) into 3 buckets row \
format delimited fields terminated by '\t';
OK


添加数据
[root@s103 /]# hive
SLF4J: Class path contains multiple SLF4J bindings.
SLF4J: Found binding in [jar:file:/opt/hzgc/bigdata/Hive/hive/lib/
log4j-slf4j-impl-2.6.2.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: Found binding in [jar:file:/opt/hzgc/bigdata/Hadoop/hadoop/share/hadoop/
common/lib/slf4j-log4j12-1.7.10.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: See http://www.slf4j.org/codes.html#multiple_bindings for an explanation.
SLF4J: Actual binding is of type [org.apache.logging.slf4j.Log4jLoggerFactory]

Logging initialized using configuration in file:/opt/hzgc/bigdata/Hive/hive/conf/
hive-log4j2.properties Async: true
Hive-on-MR is deprecated in Hive 2 and may not be available in the future versions. 
Consider using a different execution engine (i.e. spark, tez) or using Hive 1.X releases.
hive> insert overwrite table btest select * from ptest;
WARNING: Hive-on-MR is deprecated in Hive 2 and may not be available in the 
future versions. Consider using a different execution engine (i.e. spark, 
tez) or using Hive 1.X releases.
Query ID = root_20171220103731_b7d89b05-42af-46fe-a02e-855ff2378305
Total jobs = 1
Launching Job 1 out of 1
Number of reduce tasks determined at compile time: 3
In order to change the average load for a reducer (in bytes):
  set hive.exec.reducers.bytes.per.reducer=<number>
In order to limit the maximum number of reducers:
  set hive.exec.reducers.max=<number>
In order to set a constant number of reducers:
  set mapreduce.job.reduces=<number>
Starting Job = job_1512618685507_0136, Tracking URL = 
http://s103:8088/proxy/application_1512618685507_0136/
Kill Command = /opt/hzgc/bigdata/Hadoop/hadoop/bin/hadoop job  -kill job_1512618685507_0136
Hadoop job information for Stage-1: number of mappers: 1; number of reducers: 3
2017-12-20 10:39:31,752 Stage-1 map = 0%,  reduce = 0%
2017-12-20 10:39:37,037 Stage-1 map = 100%,  reduce = 0%, Cumulative CPU 1.43 sec
2017-12-20 10:39:43,283 Stage-1 map = 100%,  reduce = 33%, Cumulative CPU 5.05 sec
2017-12-20 10:39:44,320 Stage-1 map = 100%,  reduce = 100%, Cumulative CPU 10.54 sec
MapReduce Total cumulative CPU time: 10 seconds 540 msec
Ended Job = job_1512618685507_0136
Loading data to table default.btest
MapReduce Jobs Launched: 
Stage-Stage-1: Map: 1  Reduce: 3   Cumulative CPU: 10.54 sec   
HDFS Read: 15251 HDFS Write: 314 SUCCESS
Total MapReduce CPU Time Spent: 10 seconds 540 msec
OK
Time taken: 136.588 seconds
hive> dfs -ls /user/hive/warehouse/btest;
Found 3 items
-rwxr-xr-x   3 root supergroup         32 2017-12-20 10:39 /user/hive/warehouse/btest/000000_0
-rwxr-xr-x   3 root supergroup         43 2017-12-20 10:39 /user/hive/warehouse/btest/000001_0
-rwxr-xr-x   3 root supergroup         32 2017-12-20 10:39 /user/hive/warehouse/btest/000002_0
hive> select * from btest;
OK
3333	jack
3333	caier
3333	caier
1111	jack
1111	caier
1111	caier
1111	caier
2222	jack
2222	caier
2222	caier
Time taken: 0.427 seconds, Fetched: 10 row(s)
hive> create table  atest ( id int, name string) clustered by(id,name) into  \
3 buckets row format delimited fields terminated by '\t';
OK
Time taken: 0.688 seconds
hive> show tables;
OK
atest
btest
mid_table
person_table
ptest
Time taken: 0.075 seconds, Fetched: 5 row(s)
hive> 



```