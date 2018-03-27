### Hbase 可以通过RestFul api 的形式对Web 提供数据。

### HBase 导入导出数据
导出  
hbase org.apache.hadoop.hbase.mapreduce.Export objectinfo hdfs://hzgc/home/ldl/objectinfo/  
导入  
hbase org.apache.hadoop.hbase.mapreduce.Import objectinfo_demo03 /home/ldl/objectinfo/  