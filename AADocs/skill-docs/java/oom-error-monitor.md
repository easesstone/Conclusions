-XX:+HeapDumpOnOutOfMemoryError

该配置会把快照保存在user.dir中，比如你用tomcat启动，那应该是在tomcat的bin目录下

当然，也可以通过XX:HeapDumpPath=./java_pid.hprof来显示指定路径

 此外，OnOutOfMemoryError参数允许用户指定当出现oom时，指定某个脚本来完成一些动作，比如邮件知会。。。

$ java -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp/heapdump.hprof -XX:OnOutOfMemoryError ="sh ~/cleanup.sh" MyApp