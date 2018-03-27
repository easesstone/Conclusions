## 1
找到一个装好kafka 以及zookeeper 集群的节点
## 2 
配置kafka sink：
```
agent1.sources=s1
agent1.channels=c1
agent1.sinks=k1
agent1.sources.s1.type=exec
agent1.sources.s1.command=tail -F /opt/hzgc/bigdata/Flume/flume/kafka.log
agent1.sources.s1.channels=c1
agent1.channels.c1.type=memory
agent1.channels.c1.capacity=10000
agent1.channels.c1.transactionCapacity=100
#设置Kafka接收器
agent1.sinks.k1.type= org.apache.flume.sink.kafka.KafkaSink
#设置Kafka的broker地址和端口号
agent1.sinks.k1.brokerList=s100:9092
#设置Kafka的Topic
agent1.sinks.k1.topic=kafkatest
#设置序列化方式
agent1.sinks.k1.serializer.class=kafka.serializer.StringEncoder
agent1.sinks.k1.channel=c1
```

## 3  
创建Kafka topic  
./bin/kafka-topics.sh --create --zookeeper 172.18.18.101:2181 --replication-factor 1 --partitions 1 --topic kafkatest

## 4 
启动Kafka 消费进程  
./bin/kafka-console-consumer.sh --bootstrap-server s100:9092 --topic kafkatest


## 5 
准备一个kafka.log 文件，/opt/hzgc/bigdata/Flume/flume/kafka.log 往里面写入内容。 
 
## 6
启动Flume agent 从文件中读取内容，往kafka 里面扔数据。  
./bin/flume-ng agent  --conf-file conf/kafka_sink.properties -c conf/ --name a1 -Dflume.root.logger=DEBUG,console

