```
12-01-2018 16:29:32 CST mergeparquet ERROR - 18/01/12 16:29:32 WARN NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
12-01-2018 16:29:32 CST mergeparquet ERROR - Downloading hdfs://hzgc/ to /tmp/tmp2496323795522133784/.
12-01-2018 16:29:33 CST mergeparquet ERROR - Exception in thread "main" java.lang.IllegalArgumentException: Can not create a Path from an empty string
12-01-2018 16:29:33 CST mergeparquet ERROR - 	at org.apache.hadoop.fs.Path.checkPathArg(Path.java:126)
12-01-2018 16:29:33 CST mergeparquet ERROR - 	at org.apache.hadoop.fs.Path.(Path.java:134)
12-01-2018 16:29:33 CST mergeparquet ERROR - 	at org.apache.hadoop.fs.Path.(Path.java:93)
12-01-2018 16:29:33 CST mergeparquet ERROR - 	at org.apache.hadoop.fs.FileUtil.checkDest(FileUtil.java:502)
12-01-2018 16:29:33 CST mergeparquet ERROR - 	at org.apache.hadoop.fs.FileUtil.copy(FileUtil.java:348)
12-01-2018 16:29:33 CST mergeparquet ERROR - 	at org.apache.hadoop.fs.FileUtil.copy(FileUtil.java:338)
12-01-2018 16:29:33 CST mergeparquet ERROR - 	at org.apache.hadoop.fs.FileUtil.copy(FileUtil.java:289)
12-01-2018 16:29:33 CST mergeparquet ERROR - 	at org.apache.hadoop.fs.FileSystem.copyToLocalFile(FileSystem.java:2030)
12-01-2018 16:29:33 CST mergeparquet ERROR - 	at org.apache.hadoop.fs.FileSystem.copyToLocalFile(FileSystem.java:1999)
12-01-2018 16:29:33 CST mergeparquet ERROR - 	at org.apache.hadoop.fs.FileSystem.copyToLocalFile(FileSystem.java:1975)
12-01-2018 16:29:33 CST mergeparquet ERROR - 	at org.apache.spark.deploy.SparkSubmit$.downloadFile(SparkSubmit.scala:870)
12-01-2018 16:29:33 CST mergeparquet ERROR - 	at org.apache.spark.deploy.SparkSubmit$$anonfun$prepareSubmitEnvironment$1.apply(SparkSubmit.scala:316)
12-01-2018 16:29:33 CST mergeparquet ERROR - 	at org.apache.spark.deploy.SparkSubmit$$anonfun$prepareSubmitEnvironment$1.apply(SparkSubmit.scala:316)
12-01-2018 16:29:33 CST mergeparquet ERROR - 	at scala.Option.map(Option.scala:146)
12-01-2018 16:29:33 CST mergeparquet ERROR - 	at org.apache.spark.deploy.SparkSubmit$.prepareSubmitEnvironment(SparkSubmit.scala:316)
12-01-2018 16:29:33 CST mergeparquet ERROR - 	at org.apache.spark.deploy.SparkSubmit$.submit(SparkSubmit.scala:153)
12-01-2018 16:29:33 CST mergeparquet ERROR - 	at org.apache.spark.deploy.SparkSubmit$.main(SparkSubmit.scala:119)
12-01-2018 16:29:33 CST mergeparquet ERROR - 	at org.apache.spark.deploy.SparkSubmit.main(SparkSubmit.scala)
12-01-2018 16:29:33 CST mergeparquet INFO - Process completed unsuccessfully in 2 seconds.
12-01-2018 16:29:33 CST mergeparquet ERROR - Job run failed!
```