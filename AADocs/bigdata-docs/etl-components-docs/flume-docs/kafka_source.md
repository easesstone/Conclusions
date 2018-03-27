## 1
从kafka_sink 到kafka 中的数据中读取，存到hdfs 文件上。
```
a1.sources = r1
a1.sinks = k1
a1.channels = c2

a1.sources.r1.type = org.apache.flume.source.kafka.KafkaSource
a1.sources.r1.channels = c2
a1.sources.r1.batchSize = 5000
a1.sources.r1.batchDurationMillis = 2000
a1.sources.r1.kafka.bootstrap.servers = s100:9092
a1.sources.r1.kafka.topics = kafkatest
a1.sources.r1.kafka.consumer.group.id = kafkademo_source

# Use a channel which buffers events in memory
a1.channels.c2.type = memory
a1.channels.c2.capacity = 100000
a1.channels.c2.transactionCapacity = 10000


#设置Kafka接收器
#a1.sinks.k1.type= org.apache.flume.sink.kafka.KafkaSink
#设置Kafka的broker地址和端口号
#a1.sinks.k1.brokerList=s100:9092
#设置Kafka的Topic
#a1.sinks.k1.topic=kafkatest02
#设置序列化方式
#a1.sinks.k1.serializer.class=kafka.serializer.StringEncoder
#a1.sinks.k1.channel=c2

# Describe the sink
#a1.sinks.k1.type = logger
#a1.sinks.k1.channels = c1

a1.sinks.k1.channel = c2
a1.sinks.k1.type = hdfs
a1.sinks.k1.hdfs.path = hdfs://hzgc/data/flume/
a1.sinks.k1.hdfs.filePrefix = event_%y-%m-%d_%H_%M_%S
a1.sinks.k1.hdfs.fileSuffix = .log
a1.sinks.k1.hdfs.rollSize = 1048576
a1.sinks.k1.hdfs.rollCount = 0
a1.sinks.k1.hdfs.batchSize = 15000
a1.sinks.k1.hdfs.round = true
a1.sinks.k1.hdfs.roundUnit = minute
a1.sinks.k1.hdfs.threadsPoolSize = 50
a1.sinks.k1.hdfs.useLocalTimeStamp = true
a1.sinks.k1.hdfs.minBlockReplicas = 1
a1.sinks.k1.fileType = SequenceFile
a1.sinks.k1.writeFormat = TEXT
a1.sinks.k1.rollInterval = 0


#a1.sinks.k1.type = elasticsearch
#a1.sinks.k1.hostNames = 172.18.18.100:9300,172.18.18.101:9300
#a1.sinks.k1.indexName = foo_index
#a1.sinks.k1.indexType = bar_type
#a1.sinks.k1.clusterName = hbase2es-cluster
#a1.sinks.k1.batchSize = 500
#a1.sinks.k1.ttl = 5d
#a1.sinks.k1.serializer = org.apache.flume.sink.elasticsearch.ElasticSearchDynamicSerializer
#a1.sinks.k1.channel = c2

```