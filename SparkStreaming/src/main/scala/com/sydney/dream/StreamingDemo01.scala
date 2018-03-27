package com.sydney.dream

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object StreamingDemo01 {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("hello").setMaster("localhost")
        val ssc = new StreamingContext(conf, Seconds(1))
    }
}
