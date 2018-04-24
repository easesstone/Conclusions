package com.sydney.dream

import kafka.utils.{ZKGroupTopicDirs, ZkUtils}
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.log4j.Logger
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Durations, StreamingContext}
import org.apache.spark.streaming.kafka010._
import org.apache.zookeeper.ZooDefs.{Ids, Perms}
import org.apache.zookeeper.data.ACL

import scala.collection.JavaConversions
import scala.collection.mutable.ListBuffer

/**
  * add partions for a topic
  * ./bin/kafka-topics.sh --zookeeper 172.18.18.100:2181  --alter --topic first  --partitions 8
  */
object DemoClass {
    val LOG : Logger = Logger.getLogger(DemoClass.getClass)
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setMaster("local[*]").setAppName("StremingKafkaRecoverOffset")
        val ssc = new StreamingContext(conf, Durations.seconds(15))
        val zkClientAndConnection = ZkUtils.createZkClientAndConnection("172.18.18.100:2181,172.18.18.101:2181,172.18.18.102:2181", 20000, 20000)
        val zkUtils = new ZkUtils(zkClientAndConnection._1, zkClientAndConnection._2, false)
        val brokers = "172.18.18.100:9092,172.18.18.101:9092,172.18.18.102:9092"
        val groupId = "DemoGroup"
        val kafkaParams = Map[String, Object](
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> brokers,
            ConsumerConfig.GROUP_ID_CONFIG -> groupId,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG -> classOf[StringDeserializer],
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG -> classOf[StringDeserializer])
        val topics = Seq("first")
        val topicPartOffsetMap : Map[TopicPartition, Long] = readOffsets(zkUtils, topics, groupId)
        val inputDStream = KafkaUtils.createDirectStream(ssc, LocationStrategies.PreferConsistent,
                ConsumerStrategies.Subscribe[String, String](topics, kafkaParams, topicPartOffsetMap))
        inputDStream.foreachRDD(rdd => {
            rdd.foreach(row => {
                println("partition:" + row.partition + ", offset: " + row.offset + ", key: " + row.key + ", value: " + row.value + ", topic:" + row.topic)
            })
        })
        inputDStream.foreachRDD(rdd => {
            val offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges.toSeq
            persistOffsets(zkUtils, offsetRanges, groupId, true)
        })
        ssc.start()
        ssc.awaitTermination()

    }

    def persistOffsets(zkUtils: ZkUtils,offsets:Seq[OffsetRange], groupId : String, storeEndOffset : Boolean)={
        offsets.foreach(offset => {
            val zkGroupTopicDirs = new ZKGroupTopicDirs(groupId, offset.topic)
            val acls = new ListBuffer[ACL]
            val acl = new ACL
            acl.setId(Ids.ANYONE_ID_UNSAFE)
            acl.setPerms(Perms.ALL)
            acls +=  acl
            val offsetPath = zkGroupTopicDirs.consumerOffsetDir + "/" + offset.partition
            val offsetVal = if (storeEndOffset) offset.untilOffset else offset.fromOffset
            zkUtils.updatePersistentPath(offsetPath, offsetVal + "", JavaConversions.bufferAsJavaList(acls))
            LOG.warn(s"persisting offset details - topic: ${offset.topic} , partition: ${offset.partition}, " +
                s"offset: ${offset.fromOffset} -> ${offset.untilOffset}, node path: $offsetPath")
        })
    }

    /**
      * 获取GroupId 读取的topics 的各个partitions 的Offsets
      * @param zkUtils kafka.utils.ZKUtils 读取kafka 中的信息的工具类
      * @param topics 需要读取的Topic
      * @param groupId Kafka comsumer 中的消费组
      * @return TopicPartitions 和消费组消费到的Offset
      */
    def readOffsets(zkUtils: ZkUtils, topics : Seq[String], groupId:String) : Map[TopicPartition, Long] = {
        val topicPartOffsetMap = scala.collection.mutable.HashMap.empty[TopicPartition, Long]
        val partitionMap = zkUtils.getPartitionsForTopics(topics)
        partitionMap.foreach(topicPartitions => {
            val zKGroupTopicDirs = new ZKGroupTopicDirs(groupId, topicPartitions._1)
            topicPartitions._2.foreach(partition => {
                val offsetPath = zKGroupTopicDirs.consumerOffsetDir + "/" + partition
                LOG.info("offsetPath:::::" + offsetPath)
                try {
                    val offsetStatTuple = zkUtils.readData(offsetPath)
                    if (offsetPath != null) {
                        LOG.info(s"retrieving offset details - topic: ${topicPartitions._1}," +
                            s" partition: ${partition.toString},node path: $offsetPath, offsetStatTuple._1: ${offsetStatTuple._1}" )
                        topicPartOffsetMap.put(new TopicPartition(topicPartitions._1, Integer.valueOf(partition)),
                            offsetStatTuple._1.toLong)
                    }
                } catch {
                    case e : Exception =>
                        LOG.warn(s"retrieving offset details - no previous node exists:${e.getMessage}, " +
                            s"topic: ${topicPartitions._1}, partition: ${partition.toString}, node path: $offsetPath, offsetStatTuple._1: 0L, ")
                        topicPartOffsetMap.put(new TopicPartition(topicPartitions._1, Integer.valueOf(partition)), 0L)
                }
            })
        })
        topicPartOffsetMap.toMap
    }
}
