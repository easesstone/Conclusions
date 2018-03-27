hive> show tables;  
FAILED: SemanticException org.apache.hadoop.hive.ql.metadata.HiveException:
 java.lang.RuntimeException: Unable to instantiate 
 org.apache.hadoop.hive.ql.metadata.SessionHiveMetaStoreClient
 
 
 启动找不到jar ，在bin/hive-conf.sh 中添加如下内容。
 export JAVA_HOME=/opt/client/jdk
 export HADOOP_HOME=/opt/ldl/BigDataComponent/service/hadoop
 export HIVE_HOME=/opt/ldl/BigDataComponent/service/hive
 
 