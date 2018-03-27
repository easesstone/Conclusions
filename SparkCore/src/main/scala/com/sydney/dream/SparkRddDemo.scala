package com.sydney.dream

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 推荐一个实用的Spark编程参考网站
  */
object SparkRddDemo {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("SparkRddDemo").setMaster("local[*]")
        val sc = new SparkContext(conf)
        val data = Array(1, 2, 3, 4, 5)
        //1，RDD 创建，从数据集合中创建
        val dataRdd = sc.parallelize(data)
        //累加
        val dataTotal = dataRdd.reduce((x, y) => x + y)
        //遍历打印Rdd
        dataRdd.foreach(println)
        //输出总和
        println(dataTotal)
        //2，RDD 创建，从数据源如HDFS 或者本地文件系统创建
        val path = ClassLoader.getSystemResource("log1.txt").getPath;
        val text = sc.textFile(path);
        //过滤出含有female 的行。
        val data_log1 = text.filter(_.contains("female"));
        // 过滤得出数据中的第一列和第三列
        val femaleData:RDD[(String,Int)] = data_log1.map{line =>
            val t= line.split(',')
            (t(0),t(2).toInt)
        }.reduceByKey(_ + _)
        //筛选出时间大于两个小时的女性网民信息，并输出
        val result = femaleData.filter(line => line._2 > 20)
        //筛选当天时间超过20分钟的人
        result.foreach(println)

        //RDD算子(transformation 和 action)
//        val rddOfFunc1 = text.map(SparkRddDemo.func1())
//        rddOfFunc1.foreach(println)
        System.in.read
    }

    def func1(s: String): String = {
        s
    }
}
