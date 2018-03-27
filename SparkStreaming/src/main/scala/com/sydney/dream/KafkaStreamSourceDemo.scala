package com.sydney.dream

import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka.KafkaUtils

object KafkaStreamSourceDemo {
    def main(args: Array[String]): Unit = {
        val somethings =Array("192.168.59.128:9092", "first")
        val Array(brokers, topics) = somethings
        val sparkConf = new SparkCo


        nf().setAppName("DirectKfkaWordCount").setMaster("local[*]")
        val ssc = new StreamingContext(sparkConf, Seconds(2))
        val topicsSet = topics.split(",").toSet
        val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers)
//        val directKafkaStream = KafkaUtils.createDirectStream[
//              [key class], [value class],
//              [key decoder class],[value decoder class] ]
//              (streamingContext,
//              [map of Kafka parameters],
//              [set of topics to consume])
        val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topicsSet)
        val lines = messages.map(_._2)
        lines.print()
        ssc.start()
        ssc.awaitTermination()
    }
}
