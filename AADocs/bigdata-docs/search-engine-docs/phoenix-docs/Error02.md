# 有关Phoenix 查询大量数据的时候超时的时候的Hbase-site.xml 的相关设置。
```
WARN client.ScannerCallable: Ignore, probably already closed
java.io.IOException: Call to s103/172.18.18.103:16020 failed on
 local exception: org.apache.hadoop.hbase.ipc.CallTimeoutException: Call id=466, 
 waitTime=60001, operationTimeout=60000 expired.
	at org.apache.hadoop.hbase.ipc.AbstractRpcClient.wrapException(AbstractRpcClient.java:292)
	at org.apache.hadoop.hbase.ipc.RpcClientImpl.call(RpcClientImpl.java:1274)
	at org.apache.hadoop.hbase.ipc.AbstractRpcClient.callBlockingMethod(AbstractRpcClient.java:227)
	at org.apache.hadoop.hbase.ipc.AbstractRpcClient$BlockingRpcChannelImplementation.callBlockingMethod(AbstractRpcClient.java:336)
	at org.apache.hadoop.hbase.protobuf.generated.ClientProtos$ClientService$BlockingStub.scan(ClientProtos.java:35396)
	at org.apache.hadoop.hbase.client.ScannerCallable.close(ScannerCallable.java:387)
	at org.apache.hadoop.hbase.client.ScannerCallable.call(ScannerCallable.java:207)
	at org.apache.hadoop.hbase.client.ScannerCallableWithReplicas.call(ScannerCallableWithReplicas.java:145)
	at org.apache.hadoop.hbase.client.ScannerCallableWithReplicas.call(ScannerCallableWithReplicas.java:60)
	at org.apache.hadoop.hbase.client.RpcRetryingCaller.callWithoutRetries(RpcRetryingCaller.java:212)
	at org.apache.hadoop.hbase.client.ClientScanner.call(ClientScanner.java:314)
	at org.apache.hadoop.hbase.client.ClientScanner.closeScanner(ClientScanner.java:241)
	at org.apache.hadoop.hbase.client.ClientScanner.nextScanner(ClientScanner.java:256)
	at org.apache.hadoop.hbase.client.ClientScanner.loadCache(ClientScanner.java:586)
	at org.apache.hadoop.hbase.client.ClientScanner.next(ClientScanner.java:358)
	at org.apache.phoenix.iterate.ScanningResultIterator.next(ScanningResultIterator.java:118)
	at org.apache.phoenix.iterate.TableResultIterator.next(TableResultIterator.java:166)
	at org.apache.phoenix.iterate.LookAheadResultIterator$1.advance(LookAheadResultIterator.java:47)
	at org.apache.phoenix.iterate.LookAheadResultIterator.init(LookAheadResultIterator.java:59)
	at org.apache.phoenix.iterate.LookAheadResultIterator.peek(LookAheadResultIterator.java:73)
	at org.apache.phoenix.iterate.ParallelIterators$1.call(ParallelIterators.java:128)
	at org.apache.phoenix.iterate.ParallelIterators$1.call(ParallelIterators.java:113)
	at java.util.concurrent.FutureTask.run(FutureTask.java:266)
	at org.apache.phoenix.job.JobManager$InstrumentedJobFutureTask.run(JobManager.java:183)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
	at java.lang.Thread.run(Thread.java:748)
Caused by: org.apache.hadoop.hbase.ipc.CallTimeoutException: Call id=466, waitTime=60001, 
operationTimeout=60000 expired.
	at org.apache.hadoop.hbase.ipc.Call.checkAndSetTimeout(Call.java:73)
	at org.apache.hadoop.hbase.ipc.RpcClientImpl.call(RpcClientImpl.java:1248)
	... 25 more

在Hbase 开源服务的安装包中加入如下配置：
    <property>
        <name>mapreduce.task.timeout</name>
        <value>1200000</value>
    </property>
    <property>
        <name>hbase.client.scanner.timeout.period</name>
        <value>600000</value>
    </property>
    <property>
        <name>hbase.rpc.timeout</name>
        <value>600000</value>
    </property>

```
