# Elasticsearch JAVA API
## 1,需要添加的依赖：
```
<dependency>
    <groupId>org.elasticsearch.client</groupId>
    <artifactId>transport</artifactId>
    <version>5.5.0</version>
</dependency>
```
## 2， 日记信息依赖：
```
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-core</artifactId>
    <version>2.8.2</version>
</dependency>

resources 下添加log4j2.properties
appender.console.type = Console
appender.console.name = console
appender.console.layout.type = PatternLayout
rootLogger.level = info
rootLogger.appenderRef.console.ref = console
```
## 3，通过TransportClient和ES集群建立连接
```java
//  不加入到集群里面
//  获取连接Client
TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
    .addTransportAddress(new InetSocketTransportAddress(
            InetAddress.getByName("host1"), 9300))
    .addTransportAddress(new InetSocketTransportAddress(
            InetAddress.getByName("host2"), 9300));
//  shutdown
client.close();
//client 有自动嗅探集群数据节点的功能，默认不开启，
Settings settings = Settings.builder()
    .put("client.transport.sniff", true).build();
TransportClient client = new PreBuiltTransportClient(settings);
```
## 4，简单的API
### 4.1 关于索引的简单api CRUD
#### 4.1.1 创建索引 IndexRespose：
```
首先要生成一个Json 文档，有以下几种办法用来生成Jason 文档：
I，手动构造：
    String json = "{" +
    "\"user\":\"kimchy\"," +
    "\"postDate\":\"2013-01-30\"," +
    "\"message\":\"trying out Elasticsearch\"" +
    "}";

II，使用Map 的形式：
    Map<String, Object> json = new HashMap<String, Object>();
    json.put("user","kimchy");
    json.put("postDate",new Date());
    json.put("message","trying out Elasticsearch");

III， 使用jackson, 第三方jar 包
    import com.fasterxml.jackson.databind.*;
    // instance a json mapper
    ObjectMapper mapper = new ObjectMapper(); // create once, reuse
    // generate json
    byte[] json = mapper.writeValueAsBytes(yourbeaninstance);

IV，使用内部的ES API:(可以直接查看生成的json 内容String json = builder.string();)
    import static org.elasticsearch.common.xcontent.XContentFactory.*;
    XContentBuilder builder = jsonBuilder()
    .startObject()
    .field("user", "kimchy")
    .field("postDate", new Date())
    .field("message", "trying out Elasticsearch")
    .endObject()
以下的例子索引一个json文档到一个名叫twitter 的，类型为tweet 的值为1的索引。
    import static org.elasticsearch.common.xcontent.XContentFactory.*;

    IndexResponse response = client.prepareIndex("twitter", "tweet", "1")
    .setSource(jsonBuilder()
    .startObject()
    .field("user", "kimchy")
    .field("postDate", new Date())
    .field("message", "trying out Elasticsearch")
    .endObject()
    )
    .get();  // 如此便可以把数据索引到ES 中
可以通过如下内容获取索引、类型、id、等
// Index name
String _index = response.getIndex();
// Type name
String _type = response.getType();
// Document ID (generated or not)
String _id = response.getId();
// Version (if it's the first time you index this document, you will get: 1)
long _version = response.getVersion();
// status has stored current instance statement.
通过如下方法来查看是否执行成功。
RestStatus status = response.status();
```
#### 4.1.2, 获取索引以及索引内容GetResponse
```java
GetResponse response = client.prepareGet("twitter", "tweet", "1").get();
通过response.isExists() 判断索引是否存在
```
#### 4.1.3， 删除索引的DeleteResponse
```java
DeleteResponse response = client.prepareDelete("twitter", "tweet", "1").get();
通过response.status 查看运行状态
```
#### 4.1.4， 通过对查询出的结果进行删除
```java
以下的执行时指，在索引persons 下面查找性别为女的，并删除掉
BulkByScrollResponse response =
    DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
    .filter(QueryBuilders.matchQuery("gender", "male"))   // 查询
    .source("persons")     // 索引
    .get();          // 执行操作                                   
long deleted = response.getDeleted();  // 查看其中删除了几条数量    

以下是：进行异步删除的操作，
DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
    .filter(QueryBuilders.matchQuery("gender", "male"))                  
    .source("persons")
    .execute(new ActionListener<BulkIndexByScrollResponse>() {           
        @Override
        public void onResponse(BulkIndexByScrollResponse response) {
            long deleted = response.getDeleted();                        
        }
        @Override
        public void onFailure(Exception e) {
        // Handle the exception
    }
    });
```
#### 4.1.5，更新的API
```java
1， 把UpdateRequest 传入client 进行更新
UpdateRequest updateRequest = new UpdateRequest();
updateRequest.index("index");
updateRequest.type("type");
updateRequest.id("1");
updateRequest.doc(jsonBuilder()
    .startObject()
        .field("gender", "male")
    .endObject());
client.update(updateRequest).get();
等同如下：
UpdateRequest updateRequest = new UpdateRequest("index", "type", "1")
    .doc(jsonBuilder()
    .startObject()
    .field("gender", "male")
    .endObject());
    client.update(updateRequest).get();

UpdateRequest 也可以设置成Script 模式：
UpdateRequest updateRequest = new UpdateRequest("ttl", "doc", "1")
    .script(new Script("ctx._source.gender = \"male\""));
    client.update(updateRequest).get();

2，使用client 的prepareUpdate 方法：
A，client.prepareUpdate("ttl", "doc", "1")
    .setScript(new Script("ctx._source.gender = \"male\""  
    , ScriptService.ScriptType.INLINE, null, null))
    .get();
    // 如果是文件，可以通过设置SriptService.SriptType.File, 来加载Script 文件
B，
client.prepareUpdate("ttl", "doc", "1")
    .setDoc(jsonBuilder()
    .startObject()
    .field("gender", "male")
    .endObject())
    .get();


3，传入一个RequestIndex ，如果没有则不更新
如下如果存在RequestIndex中的Joe Smith, 则会更新成
 Joe Smith（或者是Mac Smith）, female,
如果不存在，则会是插入Request Index 中的人，
IndexRequest indexRequest = new IndexRequest("index", "type", "1")
    .source(jsonBuilder()
    .startObject()
    .field("name", "Joe Smith")
    .field("gender", "male")
    .endObject());
UpdateRequest updateRequest = new UpdateRequest("index", "type", "1")
    .doc(jsonBuilder()
    .startObject()
    .field("gender", "female")
    .endObject())
    .upsert(indexRequest);
    client.update(updateRequest).get();
```
#### 4.1.6, 获取批量的内容：
```java
MultiGetResponse multiGetItemResponses = client.prepareMultiGet()
    .add("twitter", "tweet", "1")           // 获取一个
    .add("twitter", "tweet", "2", "3", "4")   // 获取过个
    .add("another", "type", "foo")          // 其他索引的值
    .get();

    for (MultiGetItemResponse itemResponse : multiGetItemResponses) { 
        GetResponse response = itemResponse.getResponse();
        if (response.isExists()) {                      
        String json = response.getSourceAsString(); 
    }
}
```
#### 4.1.7，批量添加删除（一次请求）
```java
import static org.elasticsearch.common.xcontent.XContentFactory.*;
BulkRequestBuilder bulkRequest = client.prepareBulk();
// either use client#prepare, or use Requests 
//# to directly build index/delete requests
bulkRequest.add(client.prepareIndex("twitter", "tweet", "1")
    .setSource(jsonBuilder()
        .startObject()
        .field("user", "kimchy")
        .field("postDate", new Date())
        .field("message", "trying out Elasticsearch")
        .endObject()
    )
);

bulkRequest.add(client.prepareIndex("twitter", "tweet", "2")
    .setSource(jsonBuilder()
        .startObject()
        .field("user", "kimchy")
        .field("postDate", new Date())
        .field("message", "another post")
        .endObject()
    )
);

BulkResponse bulkResponse = bulkRequest.get();
if (bulkResponse.hasFailures()) {
    // process failures by iterating through each bulk response item
}
```

#### 4.1.8，通过BulkProccessor来设置批量操作。
```java
如设置缓存到多少后提交，以及使之多长时间刷新提交，设置多少个请求后提交等
1，首先创建一个BulkProccessor
// 一般只要设置请求个数，请求达到多少后刷新提交，
// 时间不设置，允许并发数为1， 试着重试单词时间50ms, 8 次，总共5.1 s
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;

BulkProcessor bulkProcessor = BulkProcessor.builder(
    client,   // 客户端
    new BulkProcessor.Listener() {
        @Override
        public void beforeBulk(long executionId,
        BulkRequest request) { 
            request.numberOfActions();//获取请求的个数
        }

        @Override
        public void afterBulk(long executionId,
        BulkRequest request,BulkResponse response) { 
            response.hasFailures(); // 获取多少个请求是失败的
        }

        @Override
        public void afterBulk(long executionId,
        BulkRequest request,Throwable failure) {  
            // 有失败，或者异常的时候的擦欧洲
        }
    })
    .setBulkActions(10000)  // 请求数量
    .setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB))  // 缓存大小 
    .setFlushInterval(TimeValue.timeValueSeconds(5))  // 不管如何，5s 后一定刷新
    .setConcurrentRequests(1) // 值为0 表示指可以单个请求值为1 表示可以有一个并发的请求
    .setBackoffPolicy(
        BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(50), 8))  
         // 请求资源少的情况下如果失败，重复三次，第一次100ms，第二次100*100
    .build();
2, 设置请求
    bulkProcessor.add(new IndexRequest("twitter", "tweet", "1")
    .source(/* your doc here */));
    bulkProcessor.add(new DeleteRequest("twitter", "tweet", "2"));

3，关闭bulkProcessor
    bulkProcessor.awaitClose(10, TimeUnit.MINUTES); // 等待批量请求完成才退出
    bulkProcessor.close();  // 直接退出，不等待批量请求
4，如果是在测试用例中，bulkProcessor 的并发请求应该设置为0
类似如下：
    BulkProcessor bulkProcessor = BulkProcessor.builder(client, 
    new BulkProcessor.Listener() { /* Listener methods */ })
    .setBulkActions(10000)
    .setConcurrentRequests(0)
    .build();
    // Add your requests
    bulkProcessor.add(/* Your requests */);
    // Flush any remaining requests
    bulkProcessor.flush();
    // Or close the bulkProcessor if you don't need it anymore
    bulkProcessor.close();
    // Refresh your indices
    client.admin().indices().prepareRefresh().get();
    // Now you can start searching!
    client.prepareSearch().get();
```
## 5，查询API
### 5.1，使用QueryBuilder 查询的例子
```java
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders.*;

SearchResponse response = client.prepareSearch("index1", "index2")
    .setTypes("type1", "type2")
    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)     // 这是内部实现，不应该有API 来指定
    .setQuery(QueryBuilders.termQuery("multi", "test"))   // Query
    .setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))// Filter
    .setFrom(0).setSize(60).setExplain(true)
    .get();
全量查询：
// MatchAll on the whole cluster with all default options
    SearchResponse response = client.prepareSearch().get();
```
