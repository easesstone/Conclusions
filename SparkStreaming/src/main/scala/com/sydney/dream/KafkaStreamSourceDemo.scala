package com.sydney.dream

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Durations, StreamingContext}

object KafkaStreamSourceDemo {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setMaster("local[*]").setAppName("SparkStreamingOnKafkaDirect")
        val ssc = new StreamingContext(conf, Durations.seconds(15))
        ssc.checkpoint("/checkpoint")
        val kafkaParams = Map(
            "metadata.broker.list" -> "172.18.18.100:9092,172.18.18.101:9092,172.18.18.102:9092",
            "group.id" -> "FaceObjectConsumerGroup"
        )

        val topicsSet = "feature".split(",").toSet

//        val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topicsSet)
//        val lines = messages.map(_._2)
//        lines.print()
        ssc.start()
        ssc.awaitTermination()
    }
}
