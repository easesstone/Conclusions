package com.sydney.dream

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 有如下数据：第一列表示姓名，第二列表示性别，第三列表示上网时间（单位分钟）
  * 统计女性中上网时间超过2个小时的网民信息
  * log1.txt
  * LiuYang,female,20
  * YuanJing,male,10
  * GuoYijun,male,5
  * CaiXuyu,female,50
  * Liyuan,male,20
  * FangBo,female,50
  * LiuYang,female,20
  *  YuanJing,male,10
  * GuoYijun,male,50
  * CaiXuyu,female,50
  * FangBo,female,60
  * log2.txt
  * LiuYang,female,20
  * YuanJing,male,10
  * CaiXuyu,female,50
  * FangBo,female,50
  * GuoYijun,male,5
  * CaiXuyu,female,50
  * Liyuan,male,20
  * CaiXuyu,female,50
  * FangBo,female,50
  * LiuYang,female,20
  * YuanJing,male,10
  * FangBo,female,50
  * uoYijun,male,50
  * CaiXuyu,female,50
  * FangBo,female,60
  */
object FirstDemo {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("FirstDemo").setMaster("local[*]")
        val sc = new SparkContext(conf)
        val path = ClassLoader.getSystemResource("log1.txt").getPath
        val text = sc.textFile(path);
        val data = text.filter(_.contains("female"));
        val femaleData:RDD[(String,Int)] = data.map{line =>
            val t= line.split(',')
            (t(0),t(2).toInt)
        }.reduceByKey(_ + _)
        //筛选出时间大于两个小时的女性网民信息，并输出
        val result = femaleData.filter(line => line._2 > 20)
        result.foreach(println)
        System.in.read
    }
}
