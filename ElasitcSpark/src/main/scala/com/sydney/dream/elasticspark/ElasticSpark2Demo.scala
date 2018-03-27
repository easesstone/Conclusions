package com.sydney.dream.elasticspark

import org.elasticsearch.spark._
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 需要手动引入org.elasticsearch.spark._
  * 这样使得所有的RDD 都拥有saveToEs 的方法
  */
object ElasticSpark2Demo {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf()
            .setAppName("ElaticSparkFirsDemo")
            .set("es.nodes", "172.18.18.114")
            .set("es.port", "9200")
            .set("es.index.auto.create", "true")
        val sc = new SparkContext(conf)
        val numbers = Map("es.mapping.id" -> "docs01test", "one" -> 1, "two" -> 2, "three" -> 3)
        val airports = Map("es.mapping.id" -> "docs02test", "arrival" -> "Otopeni", "SFO" -> "San Fran")
        sc.makeRDD(Seq(numbers, airports)).saveToEs("spark/docs")
    }
}
