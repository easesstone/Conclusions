org.apache.phoenix.schema.FunctionNotFoundException: ERROR 6001 (42F01): Function undefined. functionName=[FACECOMP]
	at org.apache.phoenix.jdbc.PhoenixStatement.throwIfUnallowedUserDefinedFunctions(PhoenixStatement.java:2024)
	at org.apache.phoenix.jdbc.PhoenixStatement.access$800(PhoenixStatement.java:207)
	at org.apache.phoenix.jdbc.PhoenixStatement$ExecutableSelectStatement.compilePlan(PhoenixStatement.java:465)
	at org.apache.phoenix.jdbc.PhoenixStatement$ExecutableSelectStatement.compilePlan(PhoenixStatement.java:442)
	at org.apache.phoenix.jdbc.PhoenixStatement$1.call(PhoenixStatement.java:300)
	at org.apache.phoenix.jdbc.PhoenixStatement$1.call(PhoenixStatement.java:290)
	at org.apache.phoenix.call.CallRunner.run(CallRunner.java:53)
	at org.apache.phoenix.jdbc.PhoenixStatement.executeQuery(PhoenixStatement.java:289)
	at org.apache.phoenix.jdbc.PhoenixStatement.executeQuery(PhoenixStatement.java:283)
	at org.apache.phoenix.jdbc.PhoenixPreparedStatement.executeQuery(PhoenixPreparedStatement.java:186)
	at com.hzgc.service.staticrepo.Demo.main(Demo.java:49)


项目包的hbase-site.xml 中需要包含如下内容
<!-- 新增的配置 -->
<property>
    <name>phoenix.functions.allowUserDefinedFunctions</name>
    <value>true</value>
</property>
<property>
    <name>fs.hdfs.impl</name>
    <value>org.apache.hadoop.hdfs.DistributedFileSystem</value>
</property>
<property>
    <name>hbase.dynamic.jars.dir</name>
    <value>${hbase.rootdir}/lib</value>
    <description>
        The directory from which the custom udf jars can be loaded
        dynamically by the phoenix client/region server without the need to restart. However,
        an already loaded udf class would not be un-loaded. See
        HBASE-1936 for more details.
    </description>
</property>