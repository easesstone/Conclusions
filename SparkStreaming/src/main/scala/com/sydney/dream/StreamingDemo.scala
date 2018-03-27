package com.sydney.dream

import org.apache.spark._
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._   // 1.3.1 后不用引入这个。

object StreamingDemo {
    def main(args: Array[String]) {
        if (args.length < 2){
            System.err.print("Usage: NetworkWordCount <hostname> <port>")
            System.exit(1)
        }
        // 初始化StreamingContext
        val conf = new SparkConf().setMaster("local[*]").setAppName("NetworkWordCount")  // 本地测试用local[*]
        val ssc = new StreamingContext(conf, Seconds(1))  // 内部初始化了一个SparkContext ，SparkContext 是所有应用程序的入口
                                                           // 可以通过ssc.sparkContext 来进行访问。
                                                            // 还可以基于现存的SparkContext 对象进行创建。
        val lines = ssc.socketTextStream("localhost", 9999, StorageLevel.MEMORY_AND_DISK)   // 利用ssc 上下文创建一个DStream
        val words = lines.flatMap(_.split(" "))
        val wordCounts = words.map(x => (x, 1)).reduceByKey(_ + _)
        val wordCounts01 = words.map(x => (x, 1)).reduceByKey((x, y) => x + y)
        wordCounts.print()
        ssc.start()
        ssc.awaitTermination()
    }
}
