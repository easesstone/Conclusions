```

快速开始
本教程假定您正在开始新鲜，并且没有现有的Kafka或ZooKeeper数据。
由于Kafka控制台脚本在基于Unix和Windows平台上不同，因此在Windows平台上
使用bin\windows\而不是bin/更改脚本扩展名.bat。

步骤1：下载代码

下载 0.11.0.1版本和un-tar。
1
2
> tar -xzf kafka_2.11-0.11.0.1.tgz
> cd kafka_2.11-0.11.0.1
步骤2：启动服务器

Kafka使用ZooKeeper，所以你需要先启动一个ZooKeeper服务器，
如果你还没有。您可以使用随kafka一起打包的便捷脚本来获取一个快速和脏的单节点ZooKeeper实例。

1
2
3
> bin/zookeeper-server-start.sh config/zookeeper.properties
[2013-04-22 15:01:37,495] INFO Reading configuration from: 
config/zookeeper.properties (org.apache.zookeeper.server.quorum.QuorumPeerConfig)
...
现在启动Kafka服务器：

1
2
3
4
> bin/kafka-server-start.sh config/server.properties
[2013-04-22 15:01:47,028] INFO Verifying properties (kafka.utils.VerifiableProperties)
[2013-04-22 15:01:47,051] INFO Property socket.send.buffer.bytes 
is overridden to 1048576 (kafka.utils.VerifiableProperties)
...
步骤3：创建主题

我们用单个分区创建一个名为“test”的主题，只有一个副本：

1
> bin/kafka-topics.sh --create --zookeeper localhost:2181 -
-replication-factor 1 --partitions 1 --topic test
如果我们运行list topic命令，我们现在可以看到该主题：

1
2
> bin/kafka-topics.sh --list --zookeeper localhost:2181
test
或者，代替手动创建主题，您也可以将经纪人配置为在不存在的主题发布时自动创建主题。

步骤4：发送一些消息

Kafka附带一个命令行客户端，它将从文件或标准输入中输入，
并将其作为消息发送到Kafka集群。默认情况下，每行将作为单独的消息发送。

运行生产者，然后在控制台中输入一些消息以发送到服务器。

1
2
3
> bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test
This is a message
This is another message
步骤5：启动消费者

卡夫卡还有一个命令行消费者将把消息转储到标准输出。

1
2
3
> bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning
This is a message
This is another message
如果您将上述每个命令都运行在不同的终端中，那么您现在应该可以在生产者终端中输入消息，
并看到它们出现在消费者终端中。

所有命令行工具都有其他选项; 运行没有参数的命令将显示更详细的记录它们的使用信息。

步骤6：设置多代理群集

到目前为止，我们一直在运行一个单一的经纪人，但这没有乐趣。
对于Kafka，单个代理只是一个大小为1的集群，所以没有什么改变，除了启动更多的代理实例。
但是为了让它感觉到，让我们将集群扩展到三个节点（仍然在本地机器上）。

首先我们为每个经纪人设置一个配置文件（在Windows上使用copy命令）：

1
2
> cp config/server.properties config/server-1.properties
> cp config/server.properties config/server-2.properties
现在编辑这些新文件并设置以下属性：

1
2
3
4
五
6
7
8
9
config/server-1.properties:
    broker.id=1
    listeners=PLAINTEXT://:9093
    log.dir=/tmp/kafka-logs-1
 
config/server-2.properties:
    broker.id=2
    listeners=PLAINTEXT://:9094
    log.dir=/tmp/kafka-logs-2
该broker.id属性是集群中每个节点的唯一和永久名称。我们必须覆盖端口和日志目录，
只因为我们在同一台机器上运行这些目录，我们希望让经纪人不要在同一个端口上注册或覆盖对方的数据。

我们已经有Zookeeper，我们的单节点启动，所以我们只需要启动两个新节点：

1
2
3
4
> bin/kafka-server-start.sh config/server-1.properties &
...
> bin/kafka-server-start.sh config/server-2.properties &
...
现在创建一个复制因子为三的新主题：

1
> bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 3 
--partitions 1 --topic my-replicated-topic
好的，现在我们有一个集群，我们怎么知道哪个经纪人在做什么呢？要看到运行“describe topics”命令：

1
2
3
> bin/kafka-topics.sh --describe --zookeeper localhost:2181 --topic my-replicated-topic
Topic:my-replicated-topic   PartitionCount:1    ReplicationFactor:3 Configs:
    Topic: my-replicated-topic  Partition: 0    Leader: 1   Replicas: 1,2,0 Isr: 1,2,0
以下是输出的说明。第一行给出了所有分区的摘要，每个附加行提供有关一个分区的信息。
因为这个主题只有一个分区，只有一行。

“leader”是负责给定分区的所有读取和写入的节点。每个节点将成为随机选择的分区部分的引导者。
“replicas”是复制此分区的日志的节点列表，无论它们是领先者还是现在都是活着的。
“isr”是一组“同步”副本。这是副本列表的子集，它目前是生存和追赶领导者的。
请注意，在我的示例中，节点1是主题唯一分区的领导者。

我们可以在我们创建的原始主题上运行相同的命令来查看它的位置：

1
2
3
> bin/kafka-topics.sh --describe --zookeeper localhost:2181 --topic test
Topic:test  PartitionCount:1    ReplicationFactor:1 Configs:
    Topic: test Partition: 0    Leader: 0   Replicas: 0 Isr: 0
所以没有什么惊喜，原来的主题没有复制品，而是在服务器0，我们创建它的集群中唯一的服务器。

让我们发布一些消息到我们的新主题：

1
2
3
4
五
> bin/kafka-console-producer.sh --broker-list localhost:9092 --topic my-replicated-topic
...
my test message 1
my test message 2
^C
现在我们来看看这些消息：

1
2
3
4
五
> bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --from-beginning 
--topic my-replicated-topic
...
my test message 1
my test message 2
^C
现在我们来测试容错。经纪人1作为领导者，所以让我们杀了它：

1
2
3
> ps aux | grep server-1.properties
7564 ttys002    0:15.91 /System/Library/Frameworks/JavaVM.framework/Versions/1.8/Home/bin/java...
> kill -9 7564
在Windows上使用：
1
2
3
> wmic process get processid,caption,commandline | find "java.exe" | find "server-1.properties"
java.exe    java  -Xmx1G -Xms1G -server -XX:+UseG1GC 
... build\libs\kafka_2.11-0.11.0.1.jar"  kafka.Kafka config\server-1.properties    644
> taskkill /pid 644 /f
领导已经切换到其中一个从站，节点1不再处于同步副本集中：

1
2
3
> bin/kafka-topics.sh --describe --zookeeper localhost:2181 --topic my-replicated-topic
Topic:my-replicated-topic   PartitionCount:1    ReplicationFactor:3 Configs:
    Topic: my-replicated-topic  Partition: 0    Leader: 2   Replicas: 1,2,0 Isr: 2,0
不过消息仍然可以用于消费，即使最初采取写作的领导者也是如此：

1
2
3
4
五
> bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --from-beginning --topic my-replicated-topic
...
my test message 1
my test message 2
^C
步骤7：使用Kafka Connect导入/导出数据

从控制台编写数据并将其写回控制台是一个方便的开始的地方，
但您可能希望使用其他来源的数据或将数据从卡夫卡导出到其他系统。
对于许多系统，不用编写自定义集成代码，您可以使用Kafka Connect导入或导出数据。

Kafka Connect是Kafka的一个工具，用于将数据导入和输出到Kafka。
它是一个可扩展的工具，运行 连接器，实现与外部系统交互的自定义​​逻辑。
在这个快速启动中，我们将看到如何使用从文件导入数据到Kafka主题并将数据从Kafka
主题导出到文件的简单连接器运行Kafka Connect。

首先，我们将首先创建一些种子数据进行测试：

1
> echo -e "foo\nbar" > test.txt
接下来，我们将启动以独立模式运行的两个连接器，这意味着它们在单个本地专用进程中运行。
我们提供三个配置文件作为参数。第一个是Kafka Connect进程的配置，包含常见配置
，如连接的Kafka代理和数据的序列化格式。其余的配置文件都指定要创建的连接器。
这些文件包括唯一的连接器名称，要实例化的连接器类以及连接器所需的任何其他配置。

1
> bin/connect-standalone.sh config/connect-standalone.properties 
config/connect-file-source.properties config/connect-file-sink.properties
Kafka附带的这些示例配置文件使用您之前启动的默认本地集群配置，并创建两个连接器：
第一个是源连接器，用于从输入文件读取行，并生成每个到Kafka主题，第
二个是接收器连接器它从Kafka主题读取消息，并将其作为输出文件中的一行生成。

在启动期间，您将看到一些日志消息，其中包括一些表示连接器正在实例化的消息。
一旦Kafka Connect进程开始，源连接器应该开始读取线路test.txt并将其生成到主题
connect-test，并且接头连接器应该开始从主题读取消息connect-test 并将其写入
文件test.sink.txt。我们可以通过检查输出文件的内容来验证数据是否通过整个流水线传递：

1
2
3
> cat test.sink.txt
foo
bar
请注意，数据存储在Kafka主题中connect-test，因此我们还可以运行控制
台消费者来查看主题中的数据（或使用自定义消费者代码来处理它）：

1
2
3
4
> bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 -
-topic connect-test --from-beginning
{"schema":{"type":"string","optional":false},"payload":"foo"}
{"schema":{"type":"string","optional":false},"payload":"bar"}
...
连接器继续处理数据，因此我们可以将数据添加到文件中，并通过管道移动：

1
> echo "Another line" >> test.txt
您应该看到该行显示在控制台消费者输出和接收器文件中。

步骤8：使用Kafka Streams处理数据
Kafka Streams是用于构建关键任务实时应用程序和微服务的客户端库，
其中输入和/或输出数据存储在Kafka群集中。Kafka Streams将客户端的编写简单性和
部署标准Java和Scala应用程序与Kafka服务器端集群技术的优势相结合，
使这些应用程序具有高度可扩展性，可扩展性，容错性，分布式等特点。
此快速入门示例将演示如何运行在此库中编码的流应用程序。

```
