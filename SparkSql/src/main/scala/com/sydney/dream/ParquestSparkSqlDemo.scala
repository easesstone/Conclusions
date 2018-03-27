package com.sydney.dream

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext

object ParquestSparkSqlDemo {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("Spark SQL basic exampel").setMaster("local")
        val sc = new SparkContext(conf)
        val sqlContext = new SQLContext(sc)
        import sqlContext.implicits._
        val df1 = sc.makeRDD(1 to 5).map(i => (i, i * 2)).toDF("single", "double")
        df1.write.parquet("data/test_table/key=1")
        val df2 = sc.makeRDD(6 to 10).map(i => (i, i * 3)).toDF("single", "triple")
        df2.write.parquet("data/test_table/key=2")

        val df3 = (sqlContext.read.option("mergeSchema", "true")).parquet("data/test_table")
        df3.printSchema()



    }
}
