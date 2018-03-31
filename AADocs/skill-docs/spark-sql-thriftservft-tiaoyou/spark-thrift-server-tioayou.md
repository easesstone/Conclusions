```
#!/bin/bash
################################################################################
## Copyright:   HZGOSUN Tech. Co, BigData
## Filename:    thriftServerStart.sh
## Description: 启动Spark sql thriftServer的脚本.
## Version:     1.0
## Author:      qiaokaifeng、mashencai
## Created:     2017-10-24
################################################################################

#set -x

cd `dirname $0`
## 脚本所在目录
BIN_DIR=`pwd`
cd ..
## 安装包根目录
ROOT_HOME=`pwd`
## 配置文件目录
CONF_DIR=${ROOT_HOME}/conf
## 安装日记目录
LOG_DIR=${ROOT_HOME}/logs
## 安装日记目录
LOG_FILE=${LOG_DIR}/thriftServerStart.log
## 最终安装的根目录，所有bigdata 相关的根目录
INSTALL_HOME=$(grep Install_HomeDir ${CONF_DIR}/cluster_conf.properties|cut -d '=' -f2)
## SPARK_INSTALL_HOME spark 安装目录
SPARK_INSTALL_HOME=${INSTALL_HOME}/Spark
## SPARK_HOME  spark 根目录
SPARK_HOME=${INSTALL_HOME}/Spark/spark

echo "It's running the  thriftserver, it calls $SPARK_HOME/sbin/start-thriftserver.sh"
if [[ $# != 6 && $# != 0 ]] ; then
echo "=================================================================================="
echo " Default parameters of master is yarn."
echo " USAGE: $0 driver_memory executor_memory driver_cores executor_cores num_executors"
echo " e.g.: $0 1g 2g 5 5 5"
echo "=================================================================================="
exit 1;
fi


CORES=$(cat /proc/cpuinfo| grep "processor"| wc -l)
DRIVER_MEMORY=${1:-8g}
EXECUTOR_MEMORY=${2:-6g}
DRIVER_CORES=${3:-6}
EXECUTOR_CORES=${4:-6}
num_excutors=$(echo `echo "(252-6)/6"|bc`  | awk -F "." '{print $1}')


#$SPARK_HOME/sbin/start-thriftserver.sh --master yarn --driver-memory $1  --executor-memory $2   --driver-cores $3  --executor-cores $4  --num-executors $5



set +x




DRIVER_MEN=${1:-"8g"}
EXECUTOR_MEN=${2:-"4g"}
DRIVER_CORES=${3:-"4"}
EXECUTOR_CORES=${4:-"4"}
DEFAULT_EXECUTOR=$(echo "(${Tatal_CORES}-${DRIVER_CORES})/${EXECUTOR_CORES}" | bc)
NUM_EXCUTORS=${5:-$DEFAULT_EXECUTOR}
bingxing=${6:-"140"}

$SPARK_HOME/sbin/start-thriftserver.sh --master yarn --driver-memory ${DRIVER_MEN}  --executor-memory ${EXECUTOR_MEN}
 --driver-cores ${DRIVER_CORES}  --executor-cores ${EXECUTOR_CORES}  --num-executors ${NUM_EXCUTORS}
 --conf spark.sql.files.maxPartitionBytes=67108864 --conf spark.default.parallelism=${bingxing} --conf  parquet.block.size=33554432

Spark Command: /opt/hzgc/bigdata/JDK/jdk/bin/java -cp /opt/hzgc/bigdata/Spark/spark/conf/:/opt/hzgc/bigdata/Spark/spark/jars/
*:/opt/hzgc/bigdata/Hadoop/hadoop/etc/hadoop/ -Dspark.deploy.recoveryMode=ZOOKEEPER -Dspark.deploy.zookeeper.url=
s105:2181,s106:2181,s107:2181 -Dspark.deploy.zookeeper.dir=/spark -Xmx10g org.apache.spark.deploy.SparkSubmit
--master yarn --conf spark.driver.memory=10g --conf spark.default.parallelism=350
--conf spark.sql.files.maxPartitionBytes=67108864 --conf parquet.block.size=33554432 -
-class org.apache.spark.sql.hive.thriftserver.HiveThriftServer2 --name Thrift JDBC/ODBC Server -
-executor-memory 7g --driver-cores 6 --executor-cores 4 --num-executors 35 spark-internal


Spark Command: /opt/hzgc/bigdata/JDK/jdk/bin/java -cp /opt/hzgc/bigdata/Spark/spark/conf/:/opt/hzgc/bigdata/Spark/spark/jars/*:/opt/hzgc/bigdata/Hadoop/hadoop/etc/hadoop/ -Dspark.deploy.recoveryMode=ZOOKEEPER -Dspark.deploy.zookeeper.url=s105:2181,s106:2181,s107:2181 -Dspark.deploy.zookeeper.dir=/spark -Xmx10g org.apache.spark.deploy.SparkSubmit --master yarn --conf spark.driver.memory=10g --conf spark.default.parallelism=350 --conf spark.sql.files.maxPartitionBytes=67108864 --conf parquet.block.size=33554432 --class org.apache.spark.sql.hive.thriftserver.HiveThriftServer2 --name Thrift JDBC/ODBC Server --executor-memory 7g --driver-cores 6 --executor-cores 4 --num-executors 35 spark-internal


105 集群上的配置


结论1，
spark 读取parquet 生成task 时的快大小和 hadoop 的dfs 的块大小没有多大关系。
与生成parquet 文件时的parquet.block.size 有关系。

结论2：
spark.sql.shuffle.partitions  的三个节点集群，设置成3000 比较好



1000 集群正确配置
Spark Command: /opt/hzgc/bigdata/JDK/jdk/bin/java -cp /opt/hzgc/bigdata/Spark/spark/conf/:
/opt/hzgc/bigdata/Spark/spark/jars/*:/opt/hzgc/bigdata/Hadoop/hadoop/etc/hadoop/ -Dspark.deploy.recoveryMode=ZOOKEEPER
-Dspark.deploy.zookeeper.url=s100:2181,s101:2181,s102:2181 -Dspark.deploy.zookeeper.dir=/spark -Xmx8g org.apache.spark.deploy.SparkSubmit
--master yarn --conf spark.driver.memory=8g --conf spark.default.parallelism=204 --conf spark.sql.files.maxPartitionBytes=67108864
 --class org.apache.spark.sql.hive.thriftserver.HiveThriftServer2 --name Thrift JDBC/ODBC Server --executor-memory 7g
  --driver-cores 6 --executor-cores 4 --num-executors 25 spark-internal






  spark.storage.memoryFraction , 0.6->0.5->0.4->0.3(视实际情况)

$SPARK_HOME/sbin/start-thriftserver.sh --master yarn --driver-memory $DRIVER_MEMORY  --executor-memory $EXECUTOR_MEMORY   --driver-cores $DRIVER_CORES  --executor-cores $EXECUTOR_CORES  --num-executors $NUM_EXECUTORS  --conf spark.default.parallelism=$PARALLELISM --conf spark.storage.memoryFraction=0.2



摘要
　　1.num-executors
　　2.executor-memory
　　3.executor-cores
　　4.driver-memory
　　5.spark.default.parallelism
　　6.spark.storage.memoryFraction
　　7.spark.shuffle.memoryFraction
　　8.total-executor-cores
　　9.资源参数参考示例
内容

1.num-executors
参数说明：该参数用于设置Spark作业总共要用多少个Executor进程来执行。Driver在向YARN集群管理器申请资源时，YARN集群管理器会尽可能按照你的设置来在集群的各个工作节点上，启动相应数量的Executor进程。这个参数非常之重要，如果不设置的话，默认只会给你启动少量的Executor进程，此时你的Spark作业的运行速度是非常慢的。
参数调优建议：每个Spark作业的运行一般设置50~100个左右的Executor进程比较合适，设置太少或太多的Executor进程都不好。设置的太少，无法充分利用集群资源；设置的太多的话，大部分队列可能无法给予充分的资源。
2.executor-memory
参数说明：该参数用于设置每个Executor进程的内存。Executor内存的大小，很多时候直接决定了Spark作业的性能，而且跟常见的JVM OOM异常，也有直接的关联。
参数调优建议：每个Executor进程的内存设置4G~8G较为合适。但是这只是一个参考值，具体的设置还是得根据不同部门的资源队列来定。可以看看自己团队的资源队列的最大内存限制是多少，num-executors乘以executor-memory，是不能超过队列的最大内存量的。此外，如果你是跟团队里其他人共享这个资源队列，那么申请的内存量最好不要超过资源队列最大总内存的1/3~1/2，避免你自己的Spark作业占用了队列所有的资源，导致别的同学的作业无法运行。
3.executor-cores
参数说明：该参数用于设置每个Executor进程的CPU core数量。这个参数决定了每个Executor进程并行执行task线程的能力。因为每个CPU core同一时间只能执行一个task线程，因此每个Executor进程的CPU core数量越多，越能够快速地执行完分配给自己的所有task线程。
参数调优建议：Executor的CPU core数量设置为2~4个较为合适。同样得根据不同部门的资源队列来定，可以看看自己的资源队列的最大CPU core限制是多少，再依据设置的Executor数量，来决定每个Executor进程可以分配到几个CPU core。同样建议，如果是跟他人共享这个队列，那么num-executors * executor-cores不要超过队列总CPU core的1/3~1/2左右比较合适，也是避免影响其他同学的作业运行。
4.driver-memory
参数说明：该参数用于设置Driver进程的内存。
参数调优建议：Driver的内存通常来说不设置，或者设置1G左右应该就够了。唯一需要注意的一点是，如果需要使用collect算子将RDD的数据全部拉取到Driver上进行处理，那么必须确保Driver的内存足够大，否则会出现OOM内存溢出的问题。
5.spark.default.parallelism
参数说明：该参数用于设置每个stage的默认task数量。这个参数极为重要，如果不设置可能会直接影响你的Spark作业性能。
参数调优建议：Spark作业的默认task数量为500~1000个较为合适。很多同学常犯的一个错误就是不去设置这个参数，那么此时就会导致Spark自己根据底层HDFS的block数量来设置task的数量，默认是一个HDFS block对应一个task。通常来说，Spark默认设置的数量是偏少的（比如就几十个task），如果task数量偏少的话，就会导致你前面设置好的Executor的参数都前功尽弃。试想一下，无论你的Executor进程有多少个，内存和CPU有多大，但是task只有1个或者10个，那么90%的Executor进程可能根本就没有task执行，也就是白白浪费了资源！因此Spark官网建议的设置原则是，设置该参数为num-executors * executor-cores的2~3倍较为合适，比如Executor的总CPU core数量为300个，那么设置1000个task是可以的，此时可以充分地利用Spark集群的资源。
6.spark.storage.memoryFraction
参数说明：该参数用于设置RDD持久化数据在Executor内存中能占的比例，默认是0.6。也就是说，默认Executor 60%的内存，可以用来保存持久化的RDD数据。根据你选择的不同的持久化策略，如果内存不够时，可能数据就不会持久化，或者数据会写入磁盘。
参数调优建议：如果Spark作业中，有较多的RDD持久化操作，该参数的值可以适当提高一些，保证持久化的数据能够容纳在内存中。避免内存不够缓存所有的数据，导致数据只能写入磁盘中，降低了性能。但是如果Spark作业中的shuffle类操作比较多，而持久化操作比较少，那么这个参数的值适当降低一些比较合适。此外，如果发现作业由于频繁的gc导致运行缓慢（通过spark web ui可以观察到作业的gc耗时），意味着task执行用户代码的内存不够用，那么同样建议调低这个参数的值。
7.spark.shuffle.memoryFraction
参数说明：该参数用于设置shuffle过程中一个task拉取到上个stage的task的输出后，进行聚合操作时能够使用的Executor内存的比例，默认是0.2。也就是说，Executor默认只有20%的内存用来进行该操作。shuffle操作在进行聚合时，如果发现使用的内存超出了这个20%的限制，那么多余的数据就会溢写到磁盘文件中去，此时就会极大地降低性能。
参数调优建议：如果Spark作业中的RDD持久化操作较少，shuffle操作较多时，建议降低持久化操作的内存占比，提高shuffle操作的内存占比比例，避免shuffle过程中数据过多时内存不够用，必须溢写到磁盘上，降低了性能。此外，如果发现作业由于频繁的gc导致运行缓慢，意味着task执行用户代码的内存不够用，那么同样建议调低这个参数的值。
8.total-executor-cores
参数说明：Total cores for all executors.
9.资源参数参考示例
以下是一份spark-submit命令的示例：

./bin/spark-submit \
  --master spark://192.168.1.1:7077 \
  --num-executors 100 \
  --executor-memory 6G \
  --executor-cores 4 \
　--total-executor-cores 400 \ ##standalone default all cores
  --driver-memory 1G \
  --conf spark.default.parallelism=1000 \
  --conf spark.storage.memoryFraction=0.5 \
  --conf spark.shuffle.memoryFraction=0.3 \
```
