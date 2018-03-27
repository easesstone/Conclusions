# Learning & Knowledge
## JMS java message service(Java 消息服务)
 ```
     应用程序接口，类似于JDBC，用于在两个应用平台之间，或者两个应用之间传递消息，是一个消息中间件，或者作为一个消息中间件，
 向分布式系统船体消息，采用异步消息通信。平台无关性。
 ```
 ## Kafka 
 ```
 分布式的消息发布订阅系统，类似JMS 的特性，主要用于活跃的流式数据。
 应用场景：
 消息队列，行为跟踪，运维数据监控，日记收集，流处理，事件溯源，持久化日记等。
 特点：
 高吞吐
 消息持久化到磁盘
 分布式系统易于扩展
 容错性高
 支持online 和offline 场景
 ```
 ## Kafka API 
 ```
 Kafka API : Producer Api（生产一个或者多个Topic） 和Consumer API（订阅一个或者多个Topic），
 Streming API(输入流和输出流的转换)，
 Connecter API（重用生产者和消费者，例如处理关系型数据库中的内容。）
 ```
 
 ## Kafka 概念;
 ```
 Topic 
 Kafka 维护的同一类消息称为一个Topic，每个Topic 由一个键，一个值，和一个时间戳组成。
 Partiction
 每一个Topic 可以分为多个Partiction， 每个Partiction 对应着一个可持续追加的、有序不可变的Log 文件。
Producer
将消息发往Kafka topic 中的角色称为Producer， 可以往Topic 中发布一个或者多个Kafka Topic
Consumer
从Kafka  topic 中获取消息的角色称为Consumer，可以从Topic 中定义一个或者多个Topic。
Broker
Kafka 集群中的每一个基点服务器称为Broker。
 ```
 
 ## 0.11.0.1 API
 ```
 生产者API
 <dependency>
     <groupId>org.apache.kafka</groupId>
     <artifactId>kafka-clients</artifactId>
     <version>0.11.0.1</version>
 </dependency>
 
 消费者API
 <dependency>
     <groupId>org.apache.kafka</groupId>
     <artifactId>kafka-clients</artifactId>
     <version>0.11.0.1</version>
 </dependency>
 
 
 流API
 <dependency>
     <groupId>org.apache.kafka</groupId>
     <artifactId>kafka-streams</artifactId>
     <version>0.11.0.1</version>
 </dependency>
 
 AdminClient API
<dependency>
    <groupId>org.apache.kafka</groupId>
    <artifactId>kafka-clients</artifactId>
    <version>0.11.0.1</version>
</dependency>
 ```
## consumer group
```
各个consumer 可以组成一个组，每个消息（TOPIC）只可以被组中的一个consumer消费，
如果一个消息可以被多个consumer消费，则这些consumer必须处于不同的组。
```
 
 
 
 