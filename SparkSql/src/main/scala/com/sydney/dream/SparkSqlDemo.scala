package com.sydney.dream

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{SQLContext, SparkSession}

object SparkSqlDemo {
    case class Person(name: String, age: Long)
    def main(args: Array[String]): Unit = {
//        val sparkSession = SparkSession.builder()
//                            .appName("Spark SQL basic exampel")
//                            .master("localhost")
//                            .getOrCreate()
//        runBasicDataFrameExample(sparkSession)
        val conf = new SparkConf().setAppName("Spark SQL basic exampel").setMaster("local[*]")
        val sc = new SparkContext(conf)
        val sqlContext = new SQLContext(sc)
        val fs = sqlContext.read.json("SparkSql/src/main/resources/person.json")
        fs.show()

        import sqlContext.implicits._
        fs.printSchema()
        fs.select("name").show()
        fs.select($"name", $"age" + 1).show()

//        System.in.read
    }
    private def runBasicDataFrameExample(spark: SparkSession): Unit = {
        val fs = spark.read.json("SparkSql/src/main/resources/person.json")
        fs.show()
    }
}
