```
Exception in thread "main" java.lang.RuntimeException:
 org.apache.hadoop.ipc.RemoteException(org.apache.hadoop.ipc.StandbyException):
 Operation category READ is not supported in state standby
解决办法：
[root@s106 resources]# hdfs haadmin -getServiceState nn1
17/12/04 11:59:56 WARN util.NativeCodeLoader: Unable to load native-hadoop 
library for your platform... using builtin-java classes where applicable
standby
[root@s106 resources]# hdfs haadmin -getServiceState nn2
17/12/04 12:00:02 WARN util.NativeCodeLoader: Unable to load native-hadoop 
library for your platform... using builtin-java classes where applicable
standby

[root@s106 resources]# hdfs haadmin -transitionToActive --forcemanual nn1
You have specified the --forcemanual flag. This flag is dangerous, as it can induce a split-brain scenario that WILL CORRUPT your HDFS namespace, possibly irrecoverably.

It is recommended not to use this flag, but instead to shut down the cluster and disable automatic failover if you prefer to manually manage your HA state.

You may abort safely by answering 'n' or hitting ^C now.

Are you sure you want to continue? (Y or N) y
17/12/04 12:03:23 WARN ha.HAAdmin: Proceeding with manual HA state management even though
automatic failover is enabled for NameNode at s107/172.18.18.107:9000
17/12/04 12:03:24 WARN util.NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
17/12/04 12:03:24 WARN ha.HAAdmin: Proceeding with manual HA state management even though
automatic failover is enabled for NameNode at s106/172.18.18.106:9000
[root@s106 resources]# 

```

