## Standalone HA 搭建配置简单说明
##### 1，首先搭建好Zookeper 和 Hadoop（HA 模式） 以及Hive
##### 2，下载后者自行编译jar 包，编译可以参考如下：
```
1, 配置好maven 编译环境。
2，下载相应代码，比如github 中spark 2.2.2 分支的代码。
   下载好后，进入到代码的dev 目录下，
   编辑 make-distribution
   VERSION=2.2.0
   SCALA_VERSION=2.11.8
   SPARK_HADOOP_VERSION=2.7.2
   SPARK_HIVE=1
3，在Dev 下执行 sh make-distribution.sh --tgz  -Phadoop-2.7.2 -Dhadoop.version=2.7.2  、
 -Pyarn  -Phive -Phive-thriftserver
4，代码根目录下的target 会生成spark-2.2.0-bin-2.7.2.tgz
```
#### 3,配置相应的文件
##### I, spark-env.sh
```bash
SPARK_DAEMON_JAVA_OPTS="-Dspark.deploy.recoveryMode=ZOOKEEPER -Dspark.deploy.zookeeper.url=s106:2181,s107:2181,s108:2181 -Dspark.deploy.zookeeper.dir=/spark"
export JAVA_HOME=/opt/hzgc/bigdata/JDK/jdk
export HADOOP_HOME=/opt/hzgc/bigdata/Hadoop/hadoop
export HADOOP_CONF_DIR=${HADOOP_HOME}/etc/hadoop
export SCALA_HOME=/opt/hzgc/bigdata/Scala/scala
export SPARK_MASTER_IP=172.18.18.106
export SPARK_MASTER_PORT=7077
export SPARK_MASTER_WEBUI_PORT=8080
export SPARK_LOCAL_DIR=/opt/hzgc/bigdata/Spark/spark/data/tmp
export SPARK_HISTORY_OPTS="-Dspark.history.ui.port=18080 -Dspark.history.fs.logDirectory=hdfs://hzgc/sparkJobHistory"
export SPARK_WORKER_MEMORY=4g
export SPARK_EXECUTOR_MEMORY=2g
export SPARK_DRIVER_MEMORY=1g
export SPARK_WORKER_CORES=4
export SPARK_CLASSPATH=$SPARK_CLASSPATH:/opt/hzgc/bigdata/Spark/spark/jars/mysql-connector-java-5.1.44-bin.jar
export SPARK_LOG_DIR=/opt/logs/spark
```
##### II, spark-default-conf
```
#---------------------------------------jobHistory-----------------------------------------#

spark.eventLog.enabled                                          true
spark.eventLog.dir                                              hdfs://hzgc/sparkJobHistory
#JDBC / ODBC Web UI历史记录中保存的SQL客户端会话的数量
spark.sql.thriftserver.ui.retainedSessions                      200

#--------------------------------------动态资源申请（如此模块的内容不）----------------------------------------#

spark.shuffle.service.enabled                                   true
## spark.shuffle.servic.port 在standalone 的时候，会和yarn 的NodeManager 冲突，所以这个端口号可能得修改。
spark.shuffle.service.port                                      7337             
spark.dynamicAllocation.enabled                                 true
spark.dynamicAllocation.minExecutors                            6
spark.dynamicAllocation.maxExecutors                            50
spark.dynamicAllocation.schedulerBacklogTimeout                 1s
spark.dynamicAllocation.sustainedSchedulerBacklogTimeout        5s

#--------------------------------------spark sql相关---------------------------------------#

#是否缓存parquet文件的元数据
spark.sql.parquet.cacheMetadata                                 true
#配置做join操作时被广播变量的表的最大阈值。当执行join操作时，这个表将会广播到所有的worker节点。以字节为单位。设置为 -1 可禁用广播表
spark.sql.autoBroadcastJoinThreshold                            10485760
#创建表是否使用默认格式
spark.sql.hive.convertCTAS                                      false
#hive版本
spark.sql.hive.version                                          2.3.0
#在驱动程序端允许列出文件的路径的最大数量。 #如果在分区发现期间检测到的路径数量超过此值，则会尝试使用另一个Spark分发作业列出文件
spark.sql.sources.parallelPartitionDiscovery.threshold          32
#若为FALSE，分区表会被视作普通表
spark.sql.sources.bucketing.enabled                             true
spark.sql.statistics.fallBackToHdfs                             false
#如果为true，则启用自适应查询执行
spark.sql.adaptive.enabled                                      false
#一个分区的最大 size，单位为字节
spark.sql.files.maxPartitionBytes                               134217728
#分区字段的类型是否自动识别，关闭自动识别后，string类型会用于分区字段。
spark.sql.sources.partitionColumnTypeInference.enabled          true
#动态字节码生成技术。当为true时，特定查询中的表达式求值的代码将会在运行时动态生成
#对于一些拥有复杂表达式的查询，此选项可导致显著速度提升。然而，对于简单的查询，这个选项会减慢查询的执行
spark.sql.codegen                                               false
#用于指定 warehouse 数据库的默认目录
spark.sql.warehouse.dir                                         hdfs://hzgc/user/hive/warehouse
#当为TRUE时，序号被视为选择列表中的位置，当为FALSE时，则忽略按顺序排序的序号
spark.sql.orderByOrdinal                                        true
#当为true时，group by子句中的序数被视为选择列表中的位置,如果为false，将忽略序号
spark.sql.groupByOrdinal                                        true
#当设置为true时，Hive Thrift服务器以异步方式执行SQL查询
spark.sql.hive.thriftServer.async                               true
#目标后置随机输入大小（以字节为单位）
spark.sql.adaptive.shuffle.targetPostShuffleInputSize           67108864b
#当为false时，如果查询包含笛卡尔乘积而没有显式CROSS JOIN语法，则会抛出一个错误
spark.sql.crossJoin.enabled                                     false
#如果为true，则读取存储在HDFS中的数据时，检查表根目录下的所有分区路径
spark.sql.hive.verifyPartitionPath                              false
#优化器和分析器运行的最大迭代次数
spark.sql.optimizer.maxIterations                               100
#InSet转换的设置大小的阈值
spark.sql.optimizer.inSetConversionThreshold                    10
#如果为true，则更倾向通过shuffle hash join对合并连接进行排序
spark.sql.join.preferSortMergeJoin                              true
#如果为true，则尽可能使用基数排序。 基数排序要快得多，但需要预留额外的内存。 
#对非常小的行进行排序时，内存开销可能会很大（在这种情况下，多达50％）
spark.sql.sort.enableRadixSort                                  true
#在执行查询时执行尝试之间的分区数量的最小增长率,较高的值会导致读取更多的分区,更低的值可能会导致更长的执行时间,因为将运行更多的作业
spark.sql.limit.scaleUpFactor                                   4
#若为TRUE，常见的子表达式将被消除
spark.sql.subexpressionElimination.enabled                      true
#查询分析器是否区分大小写。 默认为不区分大小写。 强烈建议打开区分大小写的模式
spark.sql.caseSensitive                                         false
#如果为true，则查询优化器将推断并传播查询计划中的数据约束以优化它们.
#对于某些类型的查询计划（例如具有大量谓词和别名的查询计划），约束传播有时可能在计算上花费很大，这可能会对总体运行时间产生负面影响。
spark.sql.constraintPropagation.enabled                         true
#如果为true，则字符串文字（包括正则表达式模式）在我们的SQL解析器中保持转义状态
#自Spark 2.0以来，默认值是false。 将其设置为true可以恢复Spark 2.0之前的行为
spark.sql.parser.escapedStringLiterals                          false
#如果为true，则为文件源表启用Metastore分区管理,这包括数据源和转换后的Hive表。 
#启用分区管理后，数据源表将分区存储在Hive Metastore中,并在查询计划期间使用Metastore删除分区
spark.sql.hive.manageFilesourcePartitions                       true
#如果为true，则启用使用表元数据的仅限元数据查询优化来生成分区列，而不是表扫描.
#它适用于扫描的所有列都是分区列并且查询具有满足不同语义的聚合运算符的情况。
spark.sql.optimizer.metadataOnly                                true
#如果为true，则在修复表分区时会并行收集快速统计信息（文件数量和所有文件的总大小），以避免在Hive Metastore中顺序列表。
spark.sql.hive.gatherFastStats                                  true
#以递归方式列出路径集合的并行数量，设置数量以防止文件列表产生太多的任务
spark.sql.sources.parallelPartitionDiscovery.parallelism        10000
#是否忽略损坏的文件。 如果为true，则Spark作业将在遇到损坏的文件时继续运行，并且已经读取的内容将仍然返回。
spark.sql.files.ignoreCorruptFiles                              false
#写入单个文件的最大记录数。 如果这个值是零或负数，则没有限制。
spark.sql.files.maxRecordsPerFile                               0

#-----------------------------------------Parquet------------------------------------------#

#当向Hive metastore中读写Parquet表时，Spark SQL将使用Spark SQL自带的Parquet SerDe（SerDe：Serialize/Deserilize的简称,目的是用于序列化和反序列化），
#而不是用Hive的SerDe，Spark SQL自带的SerDe拥有更好的性能。
spark.sql.hive.convertMetastoreParquet                          true
#启用矢量化拼接解码
spark.sql.parquet.enableVectorizedReader                        false
#是否开启Schema合并如果为true，则Parquet数据源合并从所有数据文件收集的模式，否则如果没有摘要文件可用，则从摘要文件或随机数据文件中选取模式。
spark.sql.parquet.mergeSchema                                   false
#该选项让SparkSQL将string安装二进制数据按照字符串处理，以便兼容老系统
spark.sql.parquet.binaryAsString                                false
#一些parquet生成系统（如Impala、Hive），把Timestamp存储成INT96格式，该项用于告诉Spark SQL将INT96数据解释为timestamp，来与提供这些系统的兼容性。
spark.sql.parquet.int96AsTimestamp                              true
#Parquet使用的输出提交类。指定的类需要是org.apache.hadoop.mapreduce.OutputCommitter的子类。
#通常，它也是org.apache.parquet.hadoop.ParquetOutputCommitter的子类。
spark.sql.parquet.output.committer.class                        org.apache.parquet.hadoop.ParquetOutputCommitter
#在将Parquet模式转换为Spark SQL模式时是否遵循Parquet的格式规范，反之亦然。
spark.sql.parquet.writeLegacyFormat                             false
#设置Parquet文件的压缩编码方式，支持 uncompressed，snappy，gzip，lzo.
spark.sql.parquet.compression.codec                             snappy
#启用过滤谓词下推优化，将过滤下推到抽取数据时，取得性能的提升
spark.sql.parquet.filterPushdown                                true
#若设为false，Spark SQL使用Hive SerDe支持对Parquet tables的操作
spark.sql.hive.convertMetastoreParquet                          true

#--------------------------------------性能相关参数----------------------------------------#

#join或者聚合操作shuffle数据时分区的数量
spark.sql.shuffle.partitions                                    200
#executor堆外内存
spark.executor.overhead.memory                                  512m
#executor堆内存
spark.executor.memory                                           2g
#executor拥有的core数
spark.executor.cores                                            1

#----------------------------------------超时设置------------------------------------------#

#进程内等待时间
spark.locality.wait.process                                     3
#节点内等待时间
spark.locality.wait.node                                        3
#机架内等待时间
spark.locality.wait.rack                                        3
#rpc超时时间
spark.rpc.askTimeout                                            10
#广播超时时间
spark.sql.broadcastTimeout                                      300

#--------------------------------------yarn相关参数----------------------------------------#

spark.yarn.historyServer.address                                s106:18080

```
##### 3.配置slaves
##### 4，分发文件
##### 5，启动
```
启动所有，start-all.sh
start-master.sh (单个master 启动)
单个slave 启动 start-slave.sh 
$SPARK_HOME/bin/spark-class org.apache.spark.deploy.worker.Worker spark://master-ip:7077
$SPARK_HOME/sbin/start-slave.sh spark://master-ip:7077



```