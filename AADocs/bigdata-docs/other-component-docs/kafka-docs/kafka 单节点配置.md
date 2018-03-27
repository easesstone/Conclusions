```
broker.id=0                                   ##
listeners=PLAINTEXT://192.168.59.128:9092     ## 
host.name = 192.168.59.128                    ## 
num.network.threads=3
num.io.threads=8
socket.send.buffer.bytes=102400
socket.receive.buffer.bytes=102400
socket.request.max.bytes=104857600
log.dirs=/tmp/kafka-logs                      ##
num.partitions=1
num.recovery.threads.per.data.dir=1
offsets.topic.replication.factor=1
transaction.state.log.replication.factor=1
transaction.state.log.min.isr=1
log.retention.hours=168
log.segment.bytes=1073741824
log.retention.check.interval.ms=300000
zookeeper.connect=192.168.59.128:2181          ##
zookeeper.connection.timeout.ms=6000
group.initial.rebalance.delay.ms=1000
```
