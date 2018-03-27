# 有关Phoenix 查询大量数据超时的一些设置
```


    <property>
       <name>hbase.client.operation.timeout</name>
      <value>600000</value>
    </property>
    <property>
       <name>hbase.regionserver.lease.period</name>
      <value>600000</value>
    </property>
    <property>
        <name>phoenix.query.timeoutMs</name>
        <value>600000</value>
    </property>
    <property>
        <name>phoenix.query.keepAliveMs</name>
        <value>600000</value>
    </property>
```