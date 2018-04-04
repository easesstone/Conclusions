# 使用技巧
1, 建立函数  
create function FACECOMP(varchar,varchar) returns decimal as 'com.hzgc.phoenix.FaceCompFunc' 
using jar 'hdfs://172.18.18.135:9000/phoenix/jars/phoenix_function-1.0.jar';

2, 创建表格  
create table "objectinfo"("id" char(25) not null primary key, "person"."name" varchar, "person"."platformid" varchar, "person"."tag" varchar, 
 "person"."pkey" varchar, "person"."idcard" varchar, "person"."sex" integer, "person"."photo" varchar, "person"."feature" varchar, 
 "person"."reason" varchar, "person"."creator" varchar, "person"."cphone" varchar, "person"."createtime" date, "person"."updatetime" date);  
 
select "id",facecomp("feature",'') as "test" from "objectinfo"  where "feature" is not null order by "test" DESC limit 10;  

create view "objectinfo"("id" char(25) not null primary key, "person"."name" varchar, "person"."platformid" varchar, "person"."tag" varchar,
 "person"."pkey" varchar, "person"."idcard" varchar, "person"."sex" integer, "person"."photo" varchar, "person"."feature" varchar, 
 "person"."reason" varchar, "person"."creator" varchar, "person"."cphone" varchar, "person"."createtime" date, "person"."updatetime" date);  

3, 创建函数
create function FACECOMPV1(float[], varchar) returns FLOAT  as 'com.hzgc.phoenix.FaceCompFuncV1' using jar 
'hdfs://hzgc/user/phoenix/udf/funcv1/phoenix-1.0.0.jar';
## [实际项目运用技巧参考](phoenix-skill-for-project.md)


## 可以使用pySQL 进行脚本式操作phoenix


# 知识点
## 简介
1，HBase 适合横向扩展，数据量大，根据rowkey进行查询。不适合一些细致的查询。  
2，Phonenix 根据HBase 的API，封装成了类似SQL 的查询。  
3，相当于结合了传统的关系型数据库的查询，以及非关系型数据库NoSql的数据存储能力，在百万级别的查询下，性能还OK。 
4，在数据量一定的程度上，可以说是实时性的查询。  
5，支持ACID 原子性操作。

## 安装
首先确保Hadoop 安装好，HBase 安装好。  
然后，下载phoenix 相应的jar 包。    
解压，把phoenix-core-x.x.x-HBase-x.x.jar 拷贝到HBase lib 目录下  
重启HBase 即可。  
## 验证安装
进入到Phoenix 的bin 目录下：${PHOENIX_HOME}/bin  
执行如下命令：  
./sqlline.py 172.18.18.135  或者 ./sqlline.py 172.18.18.135:2181  
(出现的日记大概如下)  
```
LF4J: Class path contains multiple SLF4J bindings.
SLF4J: Found binding in [jar:file:/opt/ldl/BigDataComponent/phoenix/phoenix-4.11.0-HBase-1.3-client.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: Found binding in [jar:file:/opt/ldl/BigDataComponent/hadoop/share/hadoop/common/lib/slf4j-log4j12-1.7.10.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: See http://www.slf4j.org/codes.html#multiple_bindings for an explanation.
17/09/27 10:35:44 WARN util.NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
Connected to: Phoenix (version 4.11)
Driver: PhoenixEmbeddedDriver (version 4.11)
Autocommit status: true
Transaction isolation: TRANSACTION_READ_COMMITTED
Building list of tables and columns for tab-completion (set fastconnect to true to skip)...
92/92 (100%) Done
Done
sqlline version 1.2.0
0: jdbc:phoenix:172.18.18.135> !table
+------------+--------------+-------------+---------------+----------+------------+----------------------------+-----------------+--------------+-----------------+---------------+---------------+-----------------+------------+-------------+----------------+------------+
| TABLE_CAT  | TABLE_SCHEM  | TABLE_NAME  |  TABLE_TYPE   | REMARKS  | TYPE_NAME  | SELF_REFERENCING_COL_NAME  | REF_GENERATION  | INDEX_STATE  | IMMUTABLE_ROWS  | SALT_BUCKETS  | MULTI_TENANT  | VIEW_STATEMENT  | VIEW_TYPE  | INDEX_TYPE  | TRANSACTIONAL  | IS_NAMESPA |
+------------+--------------+-------------+---------------+----------+------------+----------------------------+-----------------+--------------+-----------------+---------------+---------------+-----------------+------------+-------------+----------------+------------+
|            | SYSTEM       | CATALOG     | SYSTEM TABLE  |          |            |                            |                 |              | false           | null          | false         |                 |            |             | false          | false      |
|            | SYSTEM       | FUNCTION    | SYSTEM TABLE  |          |            |                            |                 |              | false           | null          | false         |                 |            |             | false          | false      |
|            | SYSTEM       | SEQUENCE    | SYSTEM TABLE  |          |            |                            |                 |              | false           | null          | false         |                 |            |             | false          | false      |
|            | SYSTEM       | STATS       | SYSTEM TABLE  |          |            |                            |                 |              | false           | null          | false         |                 |            |             | false          | false      |
+------------+--------------+-------------+---------------+----------+------------+----------------------------+-----------------+--------------+-----------------+---------------+---------------+-----------------+------------+-------------+----------------+------------+
0: jdbc:phoenix:172.18.18.135> create table person(id varchar primary key, name varchar);
No rows affected (1.418 seconds)
0: jdbc:phoenix:172.18.18.135> !table
+------------+--------------+-------------+---------------+----------+------------+----------------------------+-----------------+--------------+-----------------+---------------+---------------+-----------------+------------+-------------+----------------+------------+
| TABLE_CAT  | TABLE_SCHEM  | TABLE_NAME  |  TABLE_TYPE   | REMARKS  | TYPE_NAME  | SELF_REFERENCING_COL_NAME  | REF_GENERATION  | INDEX_STATE  | IMMUTABLE_ROWS  | SALT_BUCKETS  | MULTI_TENANT  | VIEW_STATEMENT  | VIEW_TYPE  | INDEX_TYPE  | TRANSACTIONAL  | IS_NAMESPA |
+------------+--------------+-------------+---------------+----------+------------+----------------------------+-----------------+--------------+-----------------+---------------+---------------+-----------------+------------+-------------+----------------+------------+
|            | SYSTEM       | CATALOG     | SYSTEM TABLE  |          |            |                            |                 |              | false           | null          | false         |                 |            |             | false          | false      |
|            | SYSTEM       | FUNCTION    | SYSTEM TABLE  |          |            |                            |                 |              | false           | null          | false         |                 |            |             | false          | false      |
|            | SYSTEM       | SEQUENCE    | SYSTEM TABLE  |          |            |                            |                 |              | false           | null          | false         |                 |            |             | false          | false      |
|            | SYSTEM       | STATS       | SYSTEM TABLE  |          |            |                            |                 |              | false           | null          | false         |                 |            |             | false          | false      |
|            |              | PERSON      | TABLE         |          |            |                            |                 |              | false           | null          | false         |                 |            |             | false          | false      |
+------------+--------------+-------------+---------------+----------+------------+----------------------------+-----------------+--------------+-----------------+---------------+---------------+-----------------+------------+-------------+----------------+------------+
0: jdbc:phoenix:172.18.18.135> 
```
## Phoenix 快速入门：
一，Phoenix 是开源的HBase 的SQL 搜索引擎。使用常规的JDBC API 而不是常规的HBase 客户端API 来创建表，茶树数据和查询数据。  
二，在应用程序和HBase 之间多加了一层类似驱动的东西，是否会是的查询结果变慢。  
这个其实并不会，Phoenix 实现了一个相对于手动调用HBase API 更好的编码，而且代码量更少。  
1，把SQL 语句转换成HBase 本地的Scans 操作。  
2，对Scan 操作，设定的Startkey 和Endkey。  
3，内部实现了并行化的Scan 操作。  
4，移动计算到数据的地方 ，而不是移动数据。  
5，把where子句中的内容，推送到服务器端过滤。  
6，通过协处理器进行聚合查询（夺标查询）。  
另外的是：辅助索引，提高对非行健列的查询性能。均匀分布写入，减少服务器的压力。等等  
为何使用SQL的形式，它都是上世纪70年代的东西了。列举了一大堆的好处。减多少代码量，sql性能优化，整合现有工具等等。  
### Phoenix 安装教程
```
Installation【安装】  
To install a pre-built phoenix, use these directions:  
Download and expand the latest phoenix-[version]-bin.tar.  
Add the phoenix-[version]-server.jar to the classpath of all 
HBase region server and master and remove any previous version.
 An easy way to do this is to copy it into the HBase lib directory 
 (use phoenix-core-[version].jar for Phoenix 3.x)
Restart HBase.  
Add the phoenix-[version]-client.jar to the classpath of any Phoenix client.  

A terminal interface to execute SQL from the command line is now bundled with Phoenix. 
To start it, execute the following from the bin directory:  
$ sqlline.py localhost  
To execute SQL scripts from the command line, you can include a SQL file argument like this:  
$ sqlline.py localhost ../examples/stock_symbol.sql

Other alternatives include:【数据导入和映射】
Using our map-reduce based CSV  【http://phoenix.apache.org/bulk_dataload.html】 
loader for bigger data sets
Mapping an existing HBase table to a Phoenix table and using the UPSERT SELECT 
command to populate a new table. 【http://phoenix.apache.org/language/index.html#upsert_select】
Populating the table through our UPSERT VALUES command.
【http://phoenix.apache.org/language/index.html#upsert_values】


【http://phoenix.apache.org/installation.html  ---安装以及客户端（command line 和图形化界面）的使用】

```
不装客户端，可以体验一下现成的Phoenix 工具。【http://phoenix.apache.org/Phoenix-in-15-minutes-or-less.html】
  



## Phoenix SQL语法
![http://phoenix.apache.org/language/index.html](http://phoenix.apache.org/language/index.html)
```
可以实现小型查询的毫秒数，或数千万行的秒数。
支持所有的标准SQL 查询，支持DML 操作，支持DDL 操作。
文档：
【http://phoenix.apache.org/language/index.html】

Sql 语法的简单运用：
创建表格：
create table objectinfo(id char(25) not null primary key, person.name varchar, \
person.platformid varchar, person.tag varchar, person.pkey varchar, \
person.idcard varchar, person.sex integer, person.photo varchar, \
person.feature varchar, person.reason varchar, person.creator varchar, \
person.cphone varchar, person.createtime date, person.updatetime date);

更新表格：
alter table objectinfo add related decimal;

全表扫描：
select * from objectinfo;

插入数据：
insert into Person (IDCardNum,Name,Age) values (100,'小明',12);

更新数据：
update test.Person set sex='男' where IDCardNum=100;

where 和 group by 子查询
where + group by:
select sex ,count(sex) as num from test.person where age >20 group by sex;


case when:
select (case name when '小明' then '明明啊' when '小红' then '红红啊' \
else name end) as showname from test.person;

## 数据删除
delete from test.Person where idcardnum=100;

删除表格
drop table test.person;
```

### 表结构描述
```
0: jdbc:phoenix:172.18.18.100:2181> !describe objectinfo;
+------------+--------------+-------------+--------------+------------+------------+--------------+----------------+-----------------+-----------------+-----------+----------+-------------+----------------+-------------------+--------------------+-------------------+--+
| TABLE_CAT  | TABLE_SCHEM  | TABLE_NAME  | COLUMN_NAME  | DATA_TYPE  | TYPE_NAME  | COLUMN_SIZE  | BUFFER_LENGTH  | DECIMAL_DIGITS  | NUM_PREC_RADIX  | NULLABLE  | REMARKS  | COLUMN_DEF  | SQL_DATA_TYPE  | SQL_DATETIME_SUB  | CHAR_OCTET_LENGTH  | ORDINAL_POSITION  |  |
+------------+--------------+-------------+--------------+------------+------------+--------------+----------------+-----------------+-----------------+-----------+----------+-------------+----------------+-------------------+--------------------+-------------------+--+
|            |              | OBJECTINFO  | ID           | 1          | CHAR       | 25           | null           | null            | null            | 0         |          |             | null           | null              | null               | 1                 |  |
|            |              | OBJECTINFO  | NAME         | 12         | VARCHAR    | null         | null           | null            | null            | 1         |          |             | null           | null              | null               | 2                 |  |
|            |              | OBJECTINFO  | PLATFORMID   | 12         | VARCHAR    | null         | null           | null            | null            | 1         |          |             | null           | null              | null               | 3                 |  |
|            |              | OBJECTINFO  | TAG          | 12         | VARCHAR    | null         | null           | null            | null            | 1         |          |             | null           | null              | null               | 4                 |  |
|            |              | OBJECTINFO  | PKEY         | 12         | VARCHAR    | null         | null           | null            | null            | 1         |          |             | null           | null              | null               | 5                 |  |
|            |              | OBJECTINFO  | IDCARD       | 12         | VARCHAR    | null         | null           | null            | null            | 1         |          |             | null           | null              | null               | 6                 |  |
|            |              | OBJECTINFO  | SEX          | 4          | INTEGER    | null         | null           | null            | null            | 1         |          |             | null           | null              | null               | 7                 |  |
|            |              | OBJECTINFO  | PHOTO        | 12         | VARCHAR    | null         | null           | null            | null            | 1         |          |             | null           | null              | null               | 8                 |  |
|            |              | OBJECTINFO  | FEATURE      | 12         | VARCHAR    | null         | null           | null            | null            | 1         |          |             | null           | null              | null               | 9                 |  |
|            |              | OBJECTINFO  | REASON       | 12         | VARCHAR    | null         | null           | null            | null            | 1         |          |             | null           | null              | null               | 10                |  |
|            |              | OBJECTINFO  | CREATOR      | 12         | VARCHAR    | null         | null           | null            | null            | 1         |          |             | null           | null              | null               | 11                |  |
|            |              | OBJECTINFO  | CHONE        | 12         | VARCHAR    | null         | null           | null            | null            | 1         |          |             | null           | null              | null               | 12                |  |
|            |              | OBJECTINFO  | CREATETIME   | 91         | DATE       | null         | null           | null            | null            | 1         |          |             | null           | null              | null               | 13                |  |
|            |              | OBJECTINFO  | UPDATETIME   | 91         | DATE       | null         | null           | null            | null            | 1         |          |             | null           | null              | null               | 14                |  |
+------------+--------------+-------------+--------------+------------+------------+--------------+----------------+-----------------+-----------------+-----------+----------+-------------+----------------+-------------------+--------------------+-------------------+--+
```

###自定义函数：
```
Creates a new function. The function name is uppercased unless they
are double quoted in which case they are case sensitive. 
The function accepts zero or more arguments. 
The class name and jar path should be in single quotes. 
The jar path is optional and if not specified then the class name will 
be loaded from the jars present in directory configured for hbase.dynamic.jars.dir.

Example:

CREATE FUNCTION my_reverse(varchar) returns varchar as 'com.mypackage.MyReverseFunction' \
using jar 'hdfs:/localhost:8080/hbase/lib/myjar.jar'
CREATE FUNCTION my_reverse(varchar) returns varchar as 'com.mypackage.MyReverseFunction'
CREATE FUNCTION my_increment(integer, integer constant defaultvalue='10') returns \ 
integer as 'com.mypackage.MyIncrementFunction' using jar '/hbase/lib/myincrement.jar'
CREATE TEMPORARY FUNCTION my_reverse(varchar) returns varchar as 'com.mypackage.MyReverseFunction'  \ 
using jar 'hdfs:/localhost:8080/hbase/lib/myjar.jar'

```

### Phoenix 视图和表的区别
```
phoenix操作hbase，我们有两种方式，创建表，创建视图。

这两种方式，有区别。

创建表的话，可读可写，就可以对HBase进行插入，查询，删除操作。

视图的话，是只读的，一般就只可以进行查询操作

虽然看起来，表的功能，比视图更强大一些。但是就像是mysql等关系型数据库一样，删除表操作，
会将表删掉。但是删除视图操作，却不会影响原始表的结构。

因为使用phoenix，创建表后，会自动和hbase建立关联映射。
当你使用phoenix删除和hbase之间的关系时，就会将hbase中的表也删掉了

所以用视图，会对原始的HBase表影响小一些。

```





# 常见报错
### 使用自定义函数的时候需要进行添加的选项

```
phoenix 下bin 目录需要把hbase-site.xml 拷贝上,同时Hbase-site.xml 配置文件需要添加如下内容。

Error: ERROR 6003 (42F03): User defined functions are configured to not be allowed. 
To allow configure phoenix.functions.allowUserDefinedFunctions to true. (state=42F03,code=6003)
java.sql.SQLException: ERROR 6003 (42F03): User defined functions are configured to not be allowed.
 To allow configure phoenix.functions.allowUserDefinedFunctions to true.
	at org.apache.phoenix.exception.SQLExceptionCode$Factory$1.newException(SQLExceptionCode.java:483)
	at org.apache.phoenix.exception.SQLExceptionInfo.buildException(SQLExceptionInfo.java:150)
	at org.apache.phoenix.jdbc.PhoenixStatement.
	throwIfUnallowedUserDefinedFunctions(PhoenixStatement.java:1990)
	at org.apache.phoenix.jdbc.PhoenixStatement.access$800(PhoenixStatement.java:206)
	at org.apache.phoenix.jdbc.PhoenixStatement$
	ExecutableCreateFunctionStatement.compilePlan(PhoenixStatement.java:789)
	at org.apache.phoenix.jdbc.PhoenixStatement$ExecutableCreateFunctionStatement.
	compilePlan(PhoenixStatement.java:779)
	at org.apache.phoenix.jdbc.PhoenixStatement$2.call(PhoenixStatement.java:386)
	at org.apache.phoenix.jdbc.PhoenixStatement$2.call(PhoenixStatement.java:376)
	at org.apache.phoenix.call.CallRunner.run(CallRunner.java:53)
	at org.apache.phoenix.jdbc.PhoenixStatement.executeMutation(PhoenixStatement.java:374)
	at org.apache.phoenix.jdbc.PhoenixStatement.executeMutation(PhoenixStatement.java:363)
	at org.apache.phoenix.jdbc.PhoenixStatement.execute(PhoenixStatement.java:1707)
	at sqlline.Commands.execute(Commands.java:822)
	at sqlline.Commands.sql(Commands.java:732)
	at sqlline.SqlLine.dispatch(SqlLine.java:813)
	at sqlline.SqlLine.begin(SqlLine.java:686)
	at sqlline.SqlLine.start(SqlLine.java:398)
	at sqlline.SqlLine.main(SqlLine.java:291)
0: jdbc:phoenix:172.18.18.135> exit

org.apache.phoenix.schema.FunctionNotFoundException: ERROR 6001 (42F01): Function undefined. functionName=[FACECOMP]
	at org.apache.phoenix.jdbc.PhoenixStatement.throwIfUnallowedUserDefinedFunctions(PhoenixStatement.java:2024)
	at org.apache.phoenix.jdbc.PhoenixStatement.access$800(PhoenixStatement.java:207)
	at org.apache.phoenix.jdbc.PhoenixStatement$ExecutableSelectStatement.compilePlan(PhoenixStatement.java:465)
	at org.apache.phoenix.jdbc.PhoenixStatement$ExecutableSelectStatement.compilePlan(PhoenixStatement.java:442)
	at org.apache.phoenix.jdbc.PhoenixStatement$1.call(PhoenixStatement.java:300)
	at org.apache.phoenix.jdbc.PhoenixStatement$1.call(PhoenixStatement.java:290)
	at org.apache.phoenix.call.CallRunner.run(CallRunner.java:53)
	at org.apache.phoenix.jdbc.PhoenixStatement.executeQuery(PhoenixStatement.java:289)
	at org.apache.phoenix.jdbc.PhoenixStatement.executeQuery(PhoenixStatement.java:283)
	at org.apache.phoenix.jdbc.PhoenixPreparedStatement.executeQuery(PhoenixPreparedStatement.java:186)
	at com.hzgc.service.staticrepo.Demo.main(Demo.java:49)


<!-- 新增的配置 -->
<property>
    <name>phoenix.functions.allowUserDefinedFunctions</name>
    <value>true</value>
</property>
<property>
    <name>fs.hdfs.impl</name>
    <value>org.apache.hadoop.hdfs.DistributedFileSystem</value>
</property>
<property>
    <name>hbase.dynamic.jars.dir</name>
    <value>${hbase.rootdir}/lib</value>
    <description>
        The directory from which the custom udf jars can be loaded
        dynamically by the phoenix client/region server without the need to restart. However,
        an already loaded udf class would not be un-loaded. See
        HBASE-1936 for more details.
    </description>
</property>
```
### 有关Phoenix 查询大量数据的时候超时的时候的Hbase-site.xml 的相关设置。
```
WARN client.ScannerCallable: Ignore, probably already closed
java.io.IOException: Call to s103/172.18.18.103:16020 failed on
 local exception: org.apache.hadoop.hbase.ipc.CallTimeoutException: Call id=466, 
 waitTime=60001, operationTimeout=60000 expired.
	at org.apache.hadoop.hbase.ipc.AbstractRpcClient.wrapException(AbstractRpcClient.java:292)
	at org.apache.hadoop.hbase.ipc.RpcClientImpl.call(RpcClientImpl.java:1274)
	at org.apache.hadoop.hbase.ipc.AbstractRpcClient.callBlockingMethod(AbstractRpcClient.java:227)
	at org.apache.hadoop.hbase.ipc.AbstractRpcClient$BlockingRpcChannelImplementation.callBlockingMethod(AbstractRpcClient.java:336)
	at org.apache.hadoop.hbase.protobuf.generated.ClientProtos$ClientService$BlockingStub.scan(ClientProtos.java:35396)
	at org.apache.hadoop.hbase.client.ScannerCallable.close(ScannerCallable.java:387)
	at org.apache.hadoop.hbase.client.ScannerCallable.call(ScannerCallable.java:207)
	at org.apache.hadoop.hbase.client.ScannerCallableWithReplicas.call(ScannerCallableWithReplicas.java:145)
	at org.apache.hadoop.hbase.client.ScannerCallableWithReplicas.call(ScannerCallableWithReplicas.java:60)
	at org.apache.hadoop.hbase.client.RpcRetryingCaller.callWithoutRetries(RpcRetryingCaller.java:212)
	at org.apache.hadoop.hbase.client.ClientScanner.call(ClientScanner.java:314)
	at org.apache.hadoop.hbase.client.ClientScanner.closeScanner(ClientScanner.java:241)
	at org.apache.hadoop.hbase.client.ClientScanner.nextScanner(ClientScanner.java:256)
	at org.apache.hadoop.hbase.client.ClientScanner.loadCache(ClientScanner.java:586)
	at org.apache.hadoop.hbase.client.ClientScanner.next(ClientScanner.java:358)
	at org.apache.phoenix.iterate.ScanningResultIterator.next(ScanningResultIterator.java:118)
	at org.apache.phoenix.iterate.TableResultIterator.next(TableResultIterator.java:166)
	at org.apache.phoenix.iterate.LookAheadResultIterator$1.advance(LookAheadResultIterator.java:47)
	at org.apache.phoenix.iterate.LookAheadResultIterator.init(LookAheadResultIterator.java:59)
	at org.apache.phoenix.iterate.LookAheadResultIterator.peek(LookAheadResultIterator.java:73)
	at org.apache.phoenix.iterate.ParallelIterators$1.call(ParallelIterators.java:128)
	at org.apache.phoenix.iterate.ParallelIterators$1.call(ParallelIterators.java:113)
	at java.util.concurrent.FutureTask.run(FutureTask.java:266)
	at org.apache.phoenix.job.JobManager$InstrumentedJobFutureTask.run(JobManager.java:183)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
	at java.lang.Thread.run(Thread.java:748)
Caused by: org.apache.hadoop.hbase.ipc.CallTimeoutException: Call id=466, waitTime=60001, 
operationTimeout=60000 expired.
	at org.apache.hadoop.hbase.ipc.Call.checkAndSetTimeout(Call.java:73)
	at org.apache.hadoop.hbase.ipc.RpcClientImpl.call(RpcClientImpl.java:1248)
	... 25 more

在Hbase 开源服务的安装包中加入如下配置：
    <property>
        <name>mapreduce.task.timeout</name>
        <value>1200000</value>
    </property>
    <property>
        <name>hbase.client.scanner.timeout.period</name>
        <value>600000</value>
    </property>
    <property>
        <name>hbase.rpc.timeout</name>
        <value>600000</value>
    </property>
    
    <property>
       <name>hbase.client.operation.timeout</name>
      <value>600000</value>
    </property>
    <property>
       <name>hbase.regionserver.lease.period</name>
      <value>600000</value>
    </property>
    <property>
        <name>phoenix.query.timeoutMs</name>
        <value>600000</value>
    </property>
    <property>
        <name>phoenix.query.keepAliveMs</name>
        <value>600000</value>
    </property>

```

