package com.sydney.dream

import kafka.api.{OffsetRequest, PartitionOffsetRequestInfo, TopicMetadata, TopicMetadataRequest}
import kafka.common.TopicAndPartition
import kafka.consumer.SimpleConsumer

object DemoClassV1 {
    def main(args: Array[String]): Unit = {
        val topicList = List("first")
        val request = new TopicMetadataRequest(topicList, 0)
        val getLeaderConsumer = new SimpleConsumer("172.18.18.100", 9092, 10000, 10000, "OffsetLookup")
        val res = getLeaderConsumer.send(request)
        val topicMetaOption = res.topicsMetadata.headOption
        val partitions = topicMetaOption match {
            case Some(tm) =>
                tm.partitionsMetadata.map(pm => (pm.partitionId, pm.leader.get.host)).toMap[Int, String]
            case None =>
                Map[Int, String]()
        }
        println("=========================" + partitions.get(0).get)

        val topicAndPartition = new TopicAndPartition("first", 0)
        val requetMin = OffsetRequest(Map(topicAndPartition -> PartitionOffsetRequestInfo(OffsetRequest.EarliestTime, 1)))
        val consumerMin = new SimpleConsumer(partitions.get(0).get, 9092, 10000, 10000, "getMinOffset")
        val kafkaCurOffset = consumerMin.getOffsetsBefore(requetMin).partitionErrorAndOffsets(topicAndPartition).offsets


        println(kafkaCurOffset.length.toString + ":" + kafkaCurOffset.head.toString)
    }
}
