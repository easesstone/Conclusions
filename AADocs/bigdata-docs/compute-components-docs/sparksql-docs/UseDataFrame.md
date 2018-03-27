## DateFrame 的生成
```
Spark-SQL可以以其他RDD对象、parquet文件、json文件、hive表，以及通过JDBC连接
到其他关系型数据库作为数据源来生成DataFrame对象。本文将以MySQL数据库为数据
源，生成DataFrame对象后进行相关的DataFame之上的操作。
例如如下的是从Spark JDBC 中获取数据：
val sparkConf = new SparkConf().setAppName( "Spark SQL DataFrame Operations")
.setMaster( "local[2]" )
val sparkContext = new SparkContext(sparkConf)

val sqlContext = new SQLContext(sparkContext)
val url = "jdbc:mysql://m000:3306/test"

val jdbcDF = sqlContext.read.format( "jdbc" ).options(
  Map( "url" -> url,
    "user" -> "root",
    "password" -> "root",
    "dbtable" -> "spark_sql_test" )).load()
```

## DateFrame 的API 操作
### 打印
```
df.show  (默認顯示20條)
df.show(10) 展示10條數據
df.show(10, true),數據展示全部內容  
```

### 小數據量的情況下轉換成Array 或者List
```
df.collect          Array[Row]
df.collectAsList    util.List[Row]
row 可以有row 的get(i) getAs() 等操作
```
### 其他
```
採用jdbcDF .describe("c1" , "c2", "c4" ).show() 
用於給每一列添加描述，在沒有的情況下

这里列出的四个方法比较类似，其中 
　　（1）first获取第一行记录 
　　（2）head获取第一行记录，head(n: Int)获取前n行记录 
　　（3）take(n: Int)获取前n行数据 
　　（4）takeAsList(n: Int)获取前n行数据，并以List的形式展现 
　　以Row或者Array[Row]的形式返回一行或多行数据。first和head功能相同。 
　　take和takeAsList方法会将获得到的数据返回到Driver端，所以，
使用这两个方法时需要注意数据量，以免Driver发生OutOfMemoryError


以下是兩個相同的作用 where 和filter
jdbcDF .where("id = 1 or c1 = 'b'" ).show()
jdbcDF .filter("id = 1 or c1 = 'b'" ).show()

select 操作
jdbcDF.select( "id" , "c3" ).show( false)


兩個相同的操作
dfDemo.select(dfDemo("ipcid"), dfDemo("ipcid") + 1)

import sparkSession.implicits._
dfDemo.select($"ipcid", $"eyeglasses" + "1").show()


selectExpr：可以对指定字段进行特殊处理 
　　可以直接对指定字段调用UDF函数，或者指定别名等。传入String类型参数，得到DataFrame对象。 
　　示例，查询id字段，c3字段取别名time，c4字段四舍五入：

jdbcDF .selectExpr("id" , "c3 as time" , "round(c4)" ).show(false)
```



