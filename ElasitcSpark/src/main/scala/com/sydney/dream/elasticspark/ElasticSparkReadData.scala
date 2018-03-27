package com.sydney.dream.elasticspark

import org.apache.spark.{SparkConf, SparkContext}
import org.elasticsearch.spark.rdd.EsSpark

/**
  * Created by lenovo on 2017/9/13.
  */
object ElasticSparkReadData {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("ElasticSparkReadData")
            .set("es.index.auto.create", "true")
            .set("es.nodes", "192.168.1.135")
            .set("es.nodes", "192.168.1.136")
            .set("es.nodes", "192.168.1.137")
            .set("es.nodes", "192.168.1.138")
            .set("es.nodes", "192.168.1.139")
            .set("es.ports", "9200")
        val sc = new SparkContext(conf)
        val rdd = EsSpark.esRDD(sc, "objectinfo/person", "?q=_id:0001111362223192401250026")
        val start = System.currentTimeMillis
        println("=============================================== total line is:  " + rdd.count)
        rdd.persist
        println("=============================================== tatal time to getRdd: "
            + (System.currentTimeMillis() - start))
//        rdd.values
        rdd.foreach(line => {
            val key = line._1
            val value = line._2
            println(line)
            println("------------------key:" + key)
//            for (tmp <- value) {
//                val key1 = tmp._1
//                val value1 = tmp._2
//                println("------------------key1:" + key1 + "------------------------" + value1)
//            }
        })
    }
}
