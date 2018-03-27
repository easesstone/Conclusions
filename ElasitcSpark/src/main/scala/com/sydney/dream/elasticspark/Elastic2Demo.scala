package com.sydney.dream.elasticspark

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}
import org.elasticsearch.spark.rdd.EsSpark

object Elastic2Demo {
    def main(args: Array[String]): Unit = {
        case class Trip(departure: String, arrival: String)
        val upcommingTrip = Trip("OTP", "SFO")
        val lastWeekTrip = Trip("MUC", "OTP")

        val conf = new SparkConf()
            .setAppName("ElaticSparkFirsDemo")
            .set("es.nodes", "172.18.18.100")
            .set("es.port", "9200")
            .setMaster("local[*]")
            .set("es.index.auto.create", "true")
        val sc = new SparkContext(conf)
        val sparkSql = new SQLContext(sc)


        val rdd = sc.makeRDD(Seq(upcommingTrip, lastWeekTrip))
        EsSpark.saveToEs(rdd, "spark/docs")
    }
}
