package com.sydney.dream

import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Durations, StreamingContext}
import org.apache.spark.streaming.kafka.KafkaUtils

//import kafka.serializer.StringDecoder
//import org.apache.spark.streaming.kafka.KafkaUtils
//import org.apache.spark.SparkConf
//import org.apache.spark.streaming.{Durations, StreamingContext}

object StreamingV1 {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setMaster("local[*]").setAppName("SparkStreamingOnKafkaDirect")
        val ssc = new StreamingContext(conf, Durations.seconds(15))
        ssc.checkpoint("/checkpoint")
        val kafkaParams = Map(
            "metadata.broker.list" -> "172.18.18.100:9092,172.18.18.101:9092,172.18.18.102:9092",
            "group.id" -> "FaceObjectConsumerGroup"
        )
        val topicsSet = "feaure".split()toSet
        val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topicsSet)
        val messaget = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topicsSet)
        KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topicsSet)
//        val conf = new SparkConf().setMaster("local[*]").setAppName("SparkStreamingOnKafkaDirect")
//        val ssc = new StreamingContext(conf, Durations.seconds(15))
//        ssc.checkpoint("/checkpoint")
//        val kafkaParams = Map(
//            "metadata.broker.list" -> "172.18.18.100:9092,172.18.18.101:9092,172.18.18.102:9092",
//            "group.id" -> "FaceObjectConsumerGroup"
//        )
//
//        val topicsSet = "feature".split(",").toSet
//
//        val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topicsSet)
    }
}
