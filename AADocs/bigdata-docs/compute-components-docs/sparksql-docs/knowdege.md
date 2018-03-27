# Spark Sql  
## 概念
Sprk Sql 是Spark 中处理结构化数据的模块。    
API：SQL语句，DataFrame, Dataset。  
## Spark SQL 
SQL语句查询，可以使用最基本的SQL查询，活着HiveQL从Hive中直接对去数据。  
使用方式command-line 或者JDBC/ODBC方式。  
## DataFrame
分布式的数据集合，每一条数据有几个命名字段组成，相当于关系型数据库的表。（内部实现了优化）  
DateFrame数据源：结构化数据文件，Hive表，外部数据，已有RDD。  
## Datesets  
Datasets 把RDD 和SparkSql 的执行引擎结合。利用RDD 的优势。
## SQLContext  
