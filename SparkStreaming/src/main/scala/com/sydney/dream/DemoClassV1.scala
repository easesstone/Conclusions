package com.sydney.dream

import kafka.api.{OffsetRequest, PartitionOffsetRequestInfo, TopicMetadata, TopicMetadataRequest}
import kafka.common.TopicAndPartition
import kafka.consumer.SimpleConsumer
import kafka.utils.ZkUtils
import org.apache.kafka.common.protocol.SecurityProtocol
//import org.apache.kafka.common.network.ListenerName
//import org.apache.kafka.common.protocol.SecurityProtocol

object DemoClassV1 {
    def main(args: Array[String]): Unit = {
//        val topicList = List("first")
//        val request = new TopicMetadataRequest(topicList, 0)
//        val getLeaderConsumer = new SimpleConsumer("172.18.18.100", 9092, 10000, 10000, "OffsetLookup")
//        val res = getLeaderConsumer.send(request)
//        val topicMetaOption = res.topicsMetadata.headOption
//        val partitions = topicMetaOption match {
//            case Some(tm) =>
//                tm.partitionsMetadata.map(pm => (pm.partitionId, pm.leader.get.host)).toMap[Int, String]
//            case None =>
//                Map[Int, String]()
//        }
//        println("=========================" + partitions.get(0).get)
//        val topicAndPartition = new TopicAndPartition("first", 0)
//        val requetMin = OffsetRequest(Map(topicAndPartition -> PartitionOffsetRequestInfo(OffsetRequest.EarliestTime, 1)))
//        val consumerMin = new SimpleConsumer(partitions.get(0).get, 9092, 10000, 10000, "getMinOffset")
//        val kafkaCurOffset = consumerMin.getOffsetsBefore(requetMin).partitionErrorAndOffsets(topicAndPartition).offsets
//        println(kafkaCurOffset.length.toString + ":" + kafkaCurOffset.head.toString)

        val topicAndPartition = new TopicAndPartition("first", 0)
        val zkClientAndConnection = ZkUtils.createZkClientAndConnection("172.18.18.100:2181,172.18.18.101:2181,172.18.18.102:2181", 20000, 20000)
        val zkUtils = new ZkUtils(zkClientAndConnection._1, zkClientAndConnection._2, false)
        val brokerId = zkUtils.getLeaderForPartition("first", 0).get
        println("==========" + brokerId)
        val broker = zkUtils.getBrokerInfo(brokerId).get
        val endPoint = broker.getBrokerEndPoint(SecurityProtocol.PLAINTEXT)
        val consumer = new SimpleConsumer(endPoint.host, endPoint.port, 10000, 100000, "getMinOffset")
        println("========" + endPoint.host + ":" +endPoint.port)
        val requetMin = OffsetRequest(Map(topicAndPartition -> PartitionOffsetRequestInfo(OffsetRequest.EarliestTime, 1)))
        val response = consumer.getOffsetsBefore(requetMin)
        println("============" + response.partitionErrorAndOffsets(topicAndPartition).offsets.head)
    }
}
