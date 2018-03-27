# 基本概念
## 依赖项  
```
<dependency>
    <groupId>org.apache.spark</groupId>
    <artifactId>spark-streaming_2.10</artifactId>
    <version>1.6.1</version>
</dependency>
```
## 数据源依赖项  
```
Kafka	spark-streaming-kafka_2.10
Flume	spark-streaming-flume_2.10
Kinesis	spark-streaming-kinesis-asl_2.10 [Amazon Software License]
Twitter	spark-streaming-twitter_2.10
ZeroMQ	spark-streaming-zeromq_2.10
MQTT	spark-streaming-mqtt_2.10
```
## 初始化操作
```
// 初始化StreamingContext,本地测试用local[*]
val conf = new SparkConf().setMaster("local[*]").setAppName("NetworkWordCount")
// 内部初始化了一个SparkContext ，SparkContext 是所有应用程序
// 的入口，可以通过ssc.sparkContext 来进行访问。
// 还可以基于现存的SparkContext 对象进行创建。
val ssc = new StreamingContext(conf, Seconds(1))                                           
context对象创建后，你还需要如下步骤：
1，创建DStream对象，并定义好输入数据源。
2，基于数据源DStream定义好计算逻辑和输出。
3，调用streamingContext.start() 启动接收并处理数据。
4，调用streamingContext.awaitTermination() 等待流式处理结束
  （不管是手动结束，还是发生异常错误）
5，你可以主动调用 streamingContext.stop() 
   来手动停止处理流程。

需要关注的重点:

一旦streamingContext启动，就不能再对其计算逻辑进行添加或修改。
一旦streamingContext被stop掉，就不能restart。
单个JVM虚机同一时间只能包含一个active的StreamingContext。
StreamingContext.stop() 也会把关联的SparkContext对象stop掉，
如果不想把SparkContext对象也stop掉，
可以将StreamingContext.stop的可选参数 stopSparkContext 设为false。
一个SparkContext对象可以和多个StreamingContext对象关联，
只要先对前一个StreamingContext.stop(sparkContext=false)，
然后再创建新的StreamingContext对象即可。
```
## 数据模型（离散数据流DStream）
```
DStream 为Spark Streaming 之抽象概念，内部由一些列连续的RDD 组成，每个RDD 是不可变的。
每个RDD 代表了特定时间间隔内的一批数据。
  
对DStream 的操作，实际上是转化为了对RDD 的一系列的操作。  

离散流（discretized stream）或DStream：这是Spark Streaming对内部持续的实时数据流
的抽象描述，即我们处理的一个实时数据流，在Spark Streaming中对应于一个DStream 实例。

批数据（batch data）：这是化整为零的第一步，将实时流数据以时间片为单位进行分
批，将流处理转化为时间片数据的批处理。随着持续时间的推移，这些处理结果就形成
了对应的结果数据流了。

时间片或批处理时间间隔（ batch interval）：这是人为地对流数据进行定量的标准
以时间片作为我们拆分流数据的依据。一个时间片的数据对应一个RDD实例。

窗口长度（window length）：一个窗口覆盖的流数据的时间长度。
必须是批处理时间间隔的倍数，

滑动时间间隔：前一个窗口到后一个窗口所经过的时间长度。
必须是批处理时间间隔的倍数

Input DStream :一个input DStream是一个特殊的DStream，
将Spark Streaming连接到一个外部数据源来读取数据。
```

## 输入数据源与接收器
```
1,两种內建的流式数据源。
   一，基础数据源，在StreamingContext API 中可直接使用的源，
   如：文件系统，套接字连接或者Akka actor。
   fileStream
   textFileStream
   actoryStream
   queueStream
   二，高级数据源（Advanced sources）: 
   需要依赖额外工具类的源，如：Kafka、Flume、Kinesis、Twitter等数据源。
   这些数据源都需要增加额外的依赖，详见依赖的第三方jar包。
   另外还可以自定义数据源。（你需要自定义一个Receiver）
 2，接收器
 专门从数据源拉取数据到内存中的对象。  
 Reliable Receiver 
 Unreliable Receiver 
```

## Dstream 对应的算子（transformation 和action）
### transform 算子

