package com.sydney.dream


import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

object TextFileStreamDemo {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("TextFileStreamDemo").setMaster("local[*]")
        val sc = new SparkContext(conf)
        val ssc = new StreamingContext(sc, Seconds(2))
        val lines = ssc.textFileStream("C:\\Users\\lenovo\\Desktop\\BigDataLearning\\SparkStreaming\\src\\main\\resources")
        val wordCount = lines.flatMap(_.split(" ")).map(x => (x, 1)).reduceByKey(_ + _)
        wordCount.print()
        ssc.start()
        ssc.awaitTermination()
//         Start the computation
    }
}
