package com.sydney.dream;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * 生产者的缓冲空间池保留尚未发送到服务器的消息，后台IO线程负责将这些消息
 * 转换成请求发送到集群。如果使用后不关闭生产者，则会泄露这些资源。
 */
public class ProducerDemo {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.59.128:9092");
        // 判断是消息是否发送成功，指定all 会阻塞消息，性能最低，但是最可靠
        props.put("acks", "all");
        // 请求失败，生产者重试次数，这里是0次，如果启用重试，则会有重复消息的可能。
        props.put("retries", 0);
        //生产者)缓存每个分区未发送消息。缓存的大小是通过 batch.size
        // 配置指定的。值较大的话将会产生更大的批。并需要更多的内存（因为每个“活跃”的分区都有1个缓冲区）。
        props.put("batch.size", 16384);
        //默认缓冲可立即发送，即遍缓冲空间还没有满，但是，如果你想减少请求的数量，
        // 可以设置linger.ms大于0。这将指示生产者发送请求之前等待一段时间，
        // 希望更多的消息填补到未满的批中。这类似于TCP的算法，
        // 例如上面的代码段，可能100条消息在一个请求发送，
        // 因为我们设置了linger(逗留)时间为1毫秒，然后，如果我们没有填满缓冲区，
        // 这个设置将增加1毫秒的延迟请求以等待更多的消息。需要注意的是，在高负载下，
        // 相近的时间一般也会组成批，即使是 linger.ms=0。
        // 在不处于高负载的情况下，如果设置比0大，以少量的延迟代价换取更少的，更有效的请求。
        props.put("linger.ms", 1);
        //控制生产者可用的缓存总量，如果消息发送速度比其传输到服务器的快，
        // 将会耗尽这个缓存空间。当缓存空间耗尽，其他发送调用将被阻塞，
        // 阻塞时间的阈值通过max.block.ms设定，之后它将抛出一个TimeoutException
        props.put("buffer.memory", 33554432);
        //key.serializer和value.serializer示例，将用户提供的key和value对象
        // ProducerRecord转换成字节，你可以使用附带的ByteArraySerializaer
        // 或StringSerializer处理简单的string或byte类型。
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        for(int i = 0; i < 10000; i++) {
            // send 方法是异步的，调价消息到缓冲区等待发送，就会立即返回。（当缓冲取大小达到一定程度时，有后台线程）
            // 进行统一的批量发送消息。
            //public Future<RecordMetadata> send(ProducerRecord<K,V> record,Callback callback)
            producer.send(new ProducerRecord<String, String>("first", "demo: "
                    + Integer.toString(i), "value: " + Integer.toString(i) + " yes"));
        }
        producer.close();
    }
}
