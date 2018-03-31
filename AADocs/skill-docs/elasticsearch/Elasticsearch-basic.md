
# 一， Elastric 基础知识点

## 1 背景简介
### 1.1,HBase 现状分析 和Elasticsearch 简介
```
HBase 的查询，虽然说是可以实现PB 级别的数目处理，实现千亿行的数据秒回查询，
但是HBase 的查询，是强依赖于rowKey 的设置的，一般上rowKey 的设计一般是这样的，
rowKey 尽可能的把相关的信息都包含在rowKey 中，所以，查询速度就比较快，但是，
如果是一些类似关系型数据库上的十分精确复杂的查询，只依赖HBase ，这是做不到的。
在我看来，Hbase 的一个最大的有点是，解决了结构和半结构化数据存储中，日益增长的
数据量的问题，由TP向PB 扩张的问题。

Elasticsearch
Elasticsearch 是一个分布式、可扩展、实时的搜索与数据分析引擎。
很多的公司都在用Elaticsearch 来实现秒回查询，比如githup 上面的代码的查询，
号称是12个PB 以上的数据，底层是HBase 数据库，
```

### 1.2，提高HBase 基于条件的查询
```
关键点在于理解Elastic 和HBase 知识架构和使用，
HBase 和Elasticsearch 之间建立索引
```
## 2，Elastic 安装和简单使用
### 2.1，Elasticsearch 的安装
```
(Elastic 是java 写的搜索和分析引起，依赖jdk安装)
单节点安装：
I， 到Elasticsearch 官网上，把安装包下载到本地，
II， 解压tar包，
III， 把tar 包的文件和目录改成非root 用户。
IV， 启动ElasticSearch  
./bin/elasticsearch 或者./bin/elasticsearch -d

elastic 的配置文档如下参考如下：
单节点elasticsearch.yml 配置
cluster.name: my-cluser
node.name: s100
network.host: 192.168.1.132
http.port: 9200
#http.cors.enabled: true
#http.cors.allow-origin: "*"
#node.master: true
#node.data: true
#discovery.zen.ping.unicast.hosts: ["s100", "s101","s102"]
bootstrap.system_call_filter: false

集群模式：
cluster.name: my-cluser
node.name: s100
network.host: 192.168.1.132
http.port: 9200
http.cors.enabled: true
http.cors.allow-origin: "*"
node.master: true
node.data: true
discovery.zen.ping.unicast.hosts: ["s100", "s101","s102"]
bootstrap.system_call_filter: false

```
### 2.2，Elastic 的一些简单命令和概念
```
ES shell 格式
一个 Elasticsearch 请求和任何 HTTP 请求一样由若干相同的部件组成：

curl -X<VERB> '<PROTOCOL>://<HOST>:<PORT>/<PATH>?<QUERY_STRING>' -d '<BODY>'
被 < > 标记的部件：
VERB
适当的 HTTP 方法 或 谓词 : GET`、 `POST`、 `PUT`、 `HEAD 或者 `DELETE`。
PROTOCOL
http 或者 https`（如果你在 Elasticsearch 前面有一个 `https 代理）
HOST
Elasticsearch 集群中任意节点的主机名，或者用 localhost 代表本地机器上的节点。
PORT
运行 Elasticsearch HTTP 服务的端口号，默认是 9200 。
PATH
API 的终端路径（例如 _count 将返回集群中文档数量）。Path 可能包含多个组件，
例如：_cluster/stats 和 _nodes/stats/jvm 。
QUERY_STRING
任意可选的查询字符串参数 (例如 ?pretty 将格式化地输出 JSON 返回值，使其更容易阅读)
BODY
一个 JSON 格式的请求体 (如果请求需要的话)


通过 curl 'http://localhost:9200/?pretty' 查看Elasticsearch  是否启动成功。
返回集群中文档的数量：
(可以加I 显示头信息)
curl -XGET 'http://localhost:9200/_count?pretty' -d '
{
"query": {
"match_all": {}
}
}

查询集群中文档的个数：
curl -XGET 'http://s101:9200/_count?pretty' -d '
{
    "query": {
        "match_all": {}
    }
}
'


```
### 2.3，举例：
```
一个雇员管理系统：（雇员目录）
需求：
支持包含多值标签、数值、以及全文本的数据
检索任一雇员的完整信息
允许结构化搜索，比如查询 30 岁以上的员工
允许简单的全文搜索以及较复杂的短语搜索
支持在匹配文档内容中高亮显示搜索片段
支持基于数据创建和管理分析仪表盘
A，添加一个雇员信息：
curl -XPUT 'localhost:9200/megacorp/employee/1?pretty' -H 'Content-Type: application/json' -d'
{
"first_name" : "John",
"last_name" :  "Smith",
"age" :        25,
"about" :      "I love to go rock climbing",
"interests": [ "sports", "music" ]
}
'


添加第二个雇员信息：
curl -XPUT 'localhost:9200/megacorp/employee/2?pretty' -H 'Content-Type: application/json' -d'
{
"first_name" :  "Jane",
"last_name" :   "Smith",
"age" :         32,
"about" :       "I like to collect rock albums",
"interests":  [ "music" ]
}
'

添加第三个雇员信息：
curl -XPUT 'localhost:9200/megacorp/employee/3?pretty' -H 'Content-Type: application/json' -d'
{
"first_name" :  "Douglas",
"last_name" :   "Fir",
"age" :         35,
"about":        "I like to build cabinets",
"interests":  [ "forestry" ]
}
'

B, 获取一个雇员的信息
curl -XGET 'localhost:9200/megacorp/employee/1?pretty'
将 HTTP 命令由 PUT 改为 GET 可以用来检索文档，同样的，
可以使用 DELETE 命令来删除文档，以及使用 HEAD 指令来检查文档是否存在。
如果想更新已存在的文档，只需再次 PUT

C，轻量搜索：
curl -XGET 'localhost:9200/_search?pretty'
单个雇员搜索：
curl -XGET 'localhost:9200/megacorp/employee/1?pretty'  （）
搜索last_name为Smith 的人
curl -XGET 'localhost:9200/megacorp/employee/_search?q=last_name:Smith&pretty'

D，查询表达式搜索：(Query-string)
curl -XGET 'localhost:9200/megacorp/employee/_search?pretty' -H 'Content-Type: application/json' -d'
{
"query" : {
"match" : {
"last_name" : "Smith"
}
}
}
'

E, 复杂搜索
相同部分省略curl -X[PUT|GET] ''localhost:9200/megacorp/employee/_search?pretty' 
-H 'Content-Type: application/json' -d' **** '
curl -XGET 'localhost:9200/megacorp/employee/_search?pretty' -H 'Content-Type: application/json' -d'
{
"query" : {
"bool": {
"must": {
"match" : {
"last_name" : "smith" 
}
},
"filter": {
"range" : {
"age" : { "gt" : 30 } 
}
}
}
}
}
'


F,全文搜索（相关的都会找出来 只要带有rock 或者climbing）
curl -XGET 'localhost:9200/megacorp/employee/_search?pretty' -H 'Content-Type: application/json' -d'
{
"query" : {
"match" : {
"about" : "rock climbing"
}
}
}
'

G，短语搜索（相对于全文搜索）
curl -XGET 'localhost:9200/megacorp/employee/_search?pretty' -H 'Content-Type: application/json' -d'
{
"query" : {
"match_phrase" : {
"about" : "rock climbing"
}
}
}
'
H，高亮搜索
curl -XGET 'localhost:9200/megacorp/employee/_search?pretty' -H 'Content-Type: application/json' -d'
{
"query" : {
"match_phrase" : {
"about" : "rock climbing"
}
},
"highlight": {
"fields" : {
"about" : {}
}
}
}
'

I，分析处理（统计）
对兴趣爱好汇总
curl -XGET 'localhost:9200/megacorp/employee/_search?pretty' -H 'Content-Type: application/json' -d'
{
"aggs": {
"all_interests": {
"terms": { "field": "interests" }
}
}
}
'

对多条件搜索
查找姓氏是Smith 的人的兴趣爱好，并汇总
curl -XGET 'localhost:9200/megacorp/employee/_search?pretty' -H 'Content-Type: application/json' -d'
{
"query": {
"match": {
"last_name": "Smith"
}
},
"aggs": {
"all_interests": {
"terms": {
"field": "interests"
}
}
}
}
'
查询特定兴趣爱好的人的平均年龄。
curl -XGET 'localhost:9200/megacorp/employee/_search?pretty' -H 'Content-Type: application/json' -d'
{
"aggs" : {
"all_interests" : {
"terms" : { "field" : "interests" },
"aggs" : {
"avg_age" : {
"avg" : { "field" : "age" }
}
}
}
}
}
'
```



## 3，ElasticSearch 集群内原理(基本简介)
### 3.1 集群内的原理
```
节点： 一个Elasticsearch 实例为一个节点，通常一个节点只有一个Elasticsearch 实例，
集群： 由具有相同的cluster.name 的节点组成。


Elastic 检索流程：
1，把请求发送到任何一个节点，因为每个节点都知道数据存在哪个位置，即存在哪个节点上，
2，收到请求的节点会把请求直接发送到数据存在的节点，
3，数据节点处理得到结果后，会直接经由第一个被请求的节点，把数据返回


集群健康检查
curl -XGET 'localhost:9200/_cluster/health?pretty'


索引：
保存数据的地方，只想一个或者多个物理分片的逻辑命名空间。

分片：
底层的一个工作单元，仅仅保存了数据的一部分内容，其也就是一个Lucence 实例，即一个完整的搜索引擎。

Elasticsearch 把数据，即对象转化为json 格式，（通用的NoSql 数据交换的格式），
把对象转化成文档格式，分布式的存储

如下为添加一个索引，
curl -XPOST 'localhost:9200/website/blog/?pretty' -H 'Content-Type: application/json' -d'
{
"title": "My second blog entry",
"text":  "Still trying this out...",
"date":  "2014/01/01"
}
'


curl -XPUT 'localhost:9200/website/blog/123?pretty' -H 'Content-Type: application/json' -d'
{
"title": "My first blog entry",
"text":  "Just trying this out...",
"date":  "2014/01/01"
}
'


取出一条文档，即取出一条数据：
curl -XGET 'localhost:9200/website/blog/123?pretty&pretty'
curl -i -XGET 'http://localhost:9200/website/blog/124?pretty'
curl -i -XGET http://localhost:9200/website/blog/124?pretty

返回文档的部分内容：
curl -XGET 'localhost:9200/website/blog/123?_source=title,text&pretty'


只返回_source的内容：
curl -XGET 'localhost:9200/website/blog/123/_source?pretty'

检索文档是否存在：
curl -i -XHEAD http://localhost:9200/website/blog/123

修改文档：从新PUT
curl -XPUT 'localhost:9200/website/blog/123?pretty' -H 'Content-Type: application/json' -d'
{
"title": "My first blog entry",
"text":  "I am starting to get the hang of this...",
"date":  "2014/01/02"
}
'

过程：
1，从旧文档构建 JSON
2，更改该 JSON
3，删除旧文档
4，索引一个新文档

仅仅需要讲一个客户端请求，不需要我们来做get和index 操作。


创建新文档，会先检查有没有，没有则报错，有则返回。
PUT /website/blog/123?op_type=create
PUT /website/blog/123/_create

会先检查有没有，没有则报错，有则返回
DELETE /website/blog/123

并发控制：
不加锁，
先获取数据版本号，修改的时候，把获取的版本号也传到服务器，如果失败，可以针对失败进行处理，如重新提交。

更新部分内容：
curl -XPOST 'localhost:9200/website/blog/1/_update?pretty' -H 'Content-Type: application/json' -d'
{
"doc" : {
"tags" : [ "testing" ],
"views": 0
}
}
'
使用脚本更新部分内容：
curl -XPOST 'localhost:9200/website/blog/1/_update?pretty' -H 'Content-Type: application/json' -d'
{
"script" : "ctx._source.views+=1"
}
'

脚本：外部传参：params
curl -XPOST 'localhost:9200/website/blog/1/_update?pretty' -H 'Content-Type: application/json' -d'
{
"script" : "ctx._source.tags+=new_tag",
"params" : {
"new_tag" : "search"
}
}
'

curl -XPOST 'localhost:9200/website/blog/1/_update?pretty' -H '
Content-Type: application/json' -d'
{
"script" : "ctx.op = ctx._source.views == count ? \u0027delete\u0027 : \u0027none\u0027",
"params" : {
"count": 1
}
}
'

针对文档不存在的情况：upsert
curl -XPOST 'localhost:9200/website/pageviews/1/_update?pretty' -H '
Content-Type: application/json' -d'
{
"script" : "ctx._source.views+=1",
"upsert": {
"views": 1
}
}
'

并发冲突的后续处理：retry_on_conflict
curl -XPOST 'localhost:9200/website/pageviews/1/_update?retry_on_conflict=5&pretty' -H '
Content-Type: application/json' -d'
{
"script" : "ctx._source.views+=1",
"upsert": {
"views": 0
}
}
'

联合检索：批量操作：
curl -XGET 'localhost:9200/_mget?pretty' -H 'Content-Type: application/json' -d'
{
"docs" : [
{
"_index" : "website",
"_type" :  "blog",
"_id" :    2
},
{
"_index" : "website",
"_type" :  "pageviews",
"_id" :    1,
"_source": "views"
}
]
}
'

指定index 和type 
curl -XGET 'localhost:9200/website/blog/_mget?pretty' -H 'Content-Type: application/json' -d'
{
"docs" : [
{ "_id" : 2 },
{ "_type" : "pageviews", "_id" :   1 }
]
}
'

```


## 4，Elastic 集群数据输入与输出流程
### 4.1 文档的概念
```
最顶层或者根对象，
比如有这么个索引
/twitter/tweet/1 
这其实就是指向了一个文档的位置，可以理解为Linux 的文件的绝对路径
```
### 4.2 文档元数据
```
_index
文档在哪存放
_type
文档表示的对象类别
_id
文档唯一标识
```

###4.3 索引文档，是文档可以被检索
```
格式类似如下：
curl -XPUT 'localhost:9200/website/blog/123?pretty' -H 'Content-Type: application/json' -d'
{
  "title": "My first blog entry",
  "text":  "Just trying this out...",
  "date":  "2014/01/01"
}
'

自动增长ID  （可以生成一个全新的文档而不会有冲突）
curl -XPOST 'localhost:9200/website/blog/?pretty' -H 'Content-Type: application/json' -d'
{
  "title": "My second blog entry",
  "text":  "Still trying this out...",
  "date":  "2014/01/01"
}
'

在Put 的时候指定版本号
curl -XPUT 'localhost:9200/twitter/tweet/1?version=2&pretty' -H 'Content-Type: application/json' -d'
{
    "message" : "elasticsearch now has versioning support, double cool!"
}
'

生成新的文档
curl -XPUT 'localhost:9200/twitter/tweet/1?op_type=create&pretty' -H '
Content-Type: application/json' -d'
{
    "user" : "kimchy",
    "post_date" : "2009-11-15T14:12:12",
    "message" : "trying out Elasticsearch"
}
'
等同于如下：
curl -XPUT 'localhost:9200/twitter/tweet/1/_create?pretty' -H '
Content-Type: application/json' -d'
{
    "user" : "kimchy",
    "post_date" : "2009-11-15T14:12:12",
    "message" : "trying out Elasticsearch"
}
'
```
### 4.4 取回文档
```
返回一个文档
curl -XGET 'localhost:9200/website/blog/123?pretty&pretty'
curl -i -XGET http://localhost:9200/website/blog/124?pretty

返回文档的部分内容：
curl -XGET 'localhost:9200/website/blog/123?_source=title,text&pretty'

返回文档只包含source 内容
curl -XGET 'localhost:9200/website/blog/123/_source?pretty'

```
### 4.5 查看文档是否存在
```
curl -i -XHEAD http://localhost:9200/website/blog/123
```

### 4.6 更新文档
```
curl -XPUT 'localhost:9200/website/blog/123?pretty' -H 'Content-Type: application/json' -d'
{
  "title": "My first blog entry",
  "text":  "I am starting to get the hang of this...",
  "date":  "2014/01/02"
}
'

```

### 4.7 删除文档
```
curl -XDELETE 'localhost:9200/website/blog/123?pretty'
```

### 4.8 更新部分文档
```
注意和PUT 的区别
curl -XPOST 'localhost:9200/website/blog/1/_update?pretty' -H 'Content-Type: application/json' -d'
{
   "doc" : {
      "tags" : [ "testing" ],  // 表明这个字段是数组
      "views": 0
   }
}
'

使用脚本进行部分跟新

curl -XPOST 'localhost:9200/website/blog/1/_update?pretty' -H 'Content-Type: application/json' -d'
{
   "script" : "ctx._source.views+=1"
}
'

使用脚本部分更新，并且传参
curl -XPOST 'localhost:9200/website/blog/1/_update?pretty' -H 'Content-Type: application/json' -d'
{
   "script" : "ctx._source.tags+=new_tag",
   "params" : {
      "new_tag" : "search"
   }
}
'

我们甚至可以选择通过设置 ctx.op 为 delete 来删除基于其内容的文档：

POST /website/blog/1/_update
{
   "script" : "ctx.op = ctx._source.views == count ? 'delete' : 'none'",
    "params" : {
        "count": 1
    }
}

更新的时候，如果文档不存在，则新创建upsert 字段
curl -XPOST 'localhost:9200/website/pageviews/1/_update?pretty' -H 'Content-Type: application/json' -d'
{
   "script" : "ctx._source.views+=1",
   "upsert": {
       "views": 1
   }
}
'

更新的时候如果指定版本号，有冲突的情况下的处理：（尝试执行五次，让版本号自动增加）
curl -XPOST 'localhost:9200/website/pageviews/1/_update?retry_on_conflict=5&pretty' -H '
Content-Type: application/json' -d'
{
   "script" : "ctx._source.views+=1",
   "upsert": {
       "views": 0
   }
}
'


```

### 4.9 取回多个文档
```
curl -XGET 'localhost:9200/_mget?pretty' -H 'Content-Type: application/json' -d'
{
   "docs" : [   // docs 的数组指定要取回的文档
      {
         "_index" : "website",
         "_type" :  "blog",
         "_id" :    2
      },
      {
         "_index" : "website",
         "_type" :  "pageviews",
         "_id" :    1,
         "_source": "views"  // source 中只返回views
      }
   ]
}
'

// 取到的是/websiter/blog 下面的内容
// 也可以用后面的值进行指定
curl -XGET 'localhost:9200/website/blog/_mget?pretty' -H 'Content-Type: application/json' -d'
{
   "docs" : [
      { "_id" : 2 },
      { "_type" : "pageviews", "_id" :   1 }
   ]
}
'



```
### 4.10 批量的思想取数据
```
curl -XPOST 'localhost:9200/_bulk?pretty' -H 'Content-Type: application/json' -d'
{ "delete": { "_index": "website", "_type": "blog", "_id": "123" }} 
{ "create": { "_index": "website", "_type": "blog", "_id": "123" }}
{ "title":    "My first blog post" }
{ "index":  { "_index": "website", "_type": "blog" }}
{ "title":    "My second blog post" }
{ "update": { "_index": "website", "_type": "blog", "_id": "123", "_retry_on_conflict" : 3} }
{ "doc" : {"title" : "My updated blog post"} }
'


其格式为：
{ action: { metadata }}\n
{ request body        }\n
{ action: { metadata }}\n
{ request body        }\n


request body 行由文档的 _source 本身组成--文档包含的字段和值。
它是 index 和 create 操作所必需的，这是有道理的：你必须提供文档以索引。

它也是 update 操作所必需的，并且应该包含你传递给 
update API 的相同请求体： doc 、 upsert 、 script 等等。 删除操作不需要 request body 行。
```

## 5,分布式文档存储
### 5.1 主要介绍了文档的存储模型和流程，可以后续到官网上了解

## 6，基础的搜索
### 6.1,空搜索（全局搜索）
```
curl -XGET 'localhost:9200/_search?pretty'
```
### 6.2,多索引下的搜索
```
/_search
在所有的索引中搜索所有的类型
/gb/_search
在 gb 索引中搜索所有的类型
/gb,us/_search
在 gb 和 us 索引中搜索所有的文档
/g*,u*/_search
在任何以 g 或者 u 开头的索引中搜索所有的类型
/gb/user/_search
在 gb 索引中搜索 user 类型
/gb,us/user,tweet/_search
在 gb 和 us 索引中搜索 user 和 tweet 类型
/_all/user,tweet/_search
在所有的索引中搜索 user 和 tweet 类型
```

### 6.3 分页搜索
```
curl -XGET 'localhost:9200/_search?size=5&pretty'
curl -XGET 'localhost:9200/_search?size=5&from=5&pretty'
curl -XGET 'localhost:9200/_search?size=5&from=10&pretty'
```
### 6.4 轻量搜索
```
即可以在url 中指定参数：
查询在 tweet 类型中 tweet 字段包含 elasticsearch 单词的所有文档：
curl -XGET 'localhost:9200/_all/tweet/_search?q=tweet:elasticsearch&pretty'
查询在 name 字段中包含 john 并且在 tweet 字段中包含 mary 的文档。实际的查询就是这样
curl -XGET 'localhost:9200/_search?q=%2Bname%3Ajohn+%2Btweet%3Amary&pretty'
返回包含 mary 的所有文档：（除非设置特定字段，否则查询字符串就使用 _all 字段进行搜索）
curl -XGET 'localhost:9200/_search?q=mary&pretty'
下面的查询针对tweents类型，并使用以下的条件：
name 字段中包含 mary 或者 john
date 值大于 2014-09-10
_all_ 字段包含 aggregations 或者 geo
拼接成字符串后的样子：
?q=%2Bname%3A(mary+john)+%2Bdate%3A%3E2014-09-10+%2B(aggregations+geo)
```

## 7， 映射与分析（类型匹配）
```
curl -XGET 'localhost:9200/_search?q=2014              # 12 results&pretty'
curl -XGET 'localhost:9200/_search?q=2014-09-15        # 12 results !&pretty'
curl -XGET 'localhost:9200/_search?q=date:2014-09-15   # 1  result&pretty'
curl -XGET 'localhost:9200/_search?q=date:2014         # 0  results !&pretty'

查看 Elasticsearch 是如何解释文档结构的：
curl -XGET 'localhost:9200/gb/_mapping/tweet?pretty'
```
### 7.1 精确值和全文
```
一个是精确地查找到某个值，
另一种是模糊地匹配某个值
```

### 7.2，倒排索引
```
Elasticsearch 使用倒排索引的概念来加速全文搜索，（具体什么是倒排索引，还需要具体分析理解）
```

### 7.2，分析与分析器
```
分析过程：
1，将词条分成适合于倒排索引的词条：
2，将词条标准化，提高可搜索性。

字符过滤器：
把内容过滤成字符串
分词器
把字符串分成词条Token
Token 过滤器。


分析器：
标准分析器
简单分析器
空格分析器
语言分析器
例如：
"Set the shape to semi-transparent by calling set_trans(5)"
标准分析器
标准分析器是Elasticsearch默认使用的分析器。它是分析各种语言文本最常用的选择。
它根据 Unicode 联盟 定义的 单词边界 划分文本。删除绝大部分标点。最后，将词条小写。它会产生
set, the, shape, to, semi, transparent, by, calling, set_trans, 5

简单分析器
简单分析器在任何不是字母的地方分隔文本，将词条小写。它会产生
set, the, shape, to, semi, transparent, by, calling, set, trans

空格分析器
空格分析器在空格的地方划分文本。它会产生
Set, the, shape, to, semi-transparent, by, calling, set_trans(5)

语言分析器
特定语言分析器可用于 很多语言。它们可以考虑指定语言的特点。例如，
 英语 分析器附带了一组英语无用词（常用单词，例如 and 或者 the ，
 它们对相关性没有多少影响），它们会被删除。 由于理解英语语法的规则，
 这个分词器可以提取英语单词的 词干 。
英语 分词器会产生下面的词条：
set, shape, semi, transpar, call, set_tran, 5
```

### 7.3, 映射
```
curl -XGET 'localhost:9200/gb/_mapping/tweet?pretty'

指定是全文搜索，精确查找，或者查找不到：
index 属性控制怎样索引字符串。它可以是下面三个值：
analyzed
首先分析字符串，然后索引它。换句话说，以全文索引这个域。
not_analyzed
  索引这个域，所以它能够被搜索，但索引的是精确值。不会对它进行分析。
no
不索引这个域。这个域不会被搜索到。
string 域 index 属性默认是 analyzed 。如果我们想映射这个字段为一个精确值，
我们需要设置它为 not_analyzed ：


指定分析器：
analyzer编辑
对于 analyzed 字符串域，用 analyzer 属性指定在搜索和索引时使用的分析器。默认， 
Elasticsearch 使用 standard 分析器，
 但你可以指定一个内置的分析器替代它，例如 whitespace 、 simple 和 `english`：
{
    "tweet": {
        "type":     "string",
        "analyzer": "english"
    }
}


为了更新映射，需要先删除索引：
curl -XDELETE 'localhost:9200/gb?pretty'

然后重新建立索引
curl -XPUT 'localhost:9200/gb?pretty' -H 'Content-Type: application/json' -d'
{
  "mappings": {
    "tweet" : {
      "properties" : {
        "tweet" : {
          "type" :    "string",
          "analyzer": "english"
        },
        "date" : {
          "type" :   "date"
        },
        "name" : {
          "type" :   "string"
        },
        "user_id" : {
          "type" :   "long"
        }
      }
    }
  }
}
'

添加映射：
curl -XPUT 'localhost:9200/gb/_mapping/tweet?pretty' -H 'Content-Type: application/json' -d'
{
  "properties" : {
    "tag" : {
      "type" :    "string",
      "index":    "not_analyzed"
    }
  }
}
'

测试映射器：
curl -XGET 'localhost:9200/gb/_analyze?pretty' -H 'Content-Type: application/json' -d'
{
  "field": "tweet",
  "text": "Black-cats" 
}
'
curl -XGET 'localhost:9200/gb/_analyze?pretty' -H 'Content-Type: application/json' -d'
{
  "field": "tag",
  "text": "Black-cats" 
}
'
```
### 7.4 复杂核心映射对象
```
多值域：
{ "tag": [ "search", "nosql" ]}
空域：
"null_value":               null,
"empty_array":              [],
"array_with_null_value":    [ null ]

多层级对象：
内置对象：

```
## 8，进阶搜索（请求提查询,查询领域特定语言）
### 8.1 空查询
```
curl -XGET 'localhost:9200/_search?pretty' -H 'Content-Type: application/json' -d'
{}
'
支持POST 请求
```

### 8.2 查询表达式
```
结构：
GET /_search
{
    "query": YOUR_QUERY_HERE
}
例子：
curl -XGET 'localhost:9200/_search?pretty' -H 'Content-Type: application/json' -d'
{
    "query": {
        "match_all": {}
    }
}
'
一个查询语句 的典型结构：

{
    QUERY_NAME: {
        ARGUMENT: VALUE,
        ARGUMENT: VALUE,...
    }
}
如果是针对某个字段，那么它的结构如下：

{
    QUERY_NAME: {
        FIELD_NAME: {
            ARGUMENT: VALUE,
            ARGUMENT: VALUE,...
        }
    }
}
举个例子，你可以使用 match 查询语句 来查询 tweet 字段中包含 elasticsearch 的 tweet：
{
    "match": {
        "tweet": "elasticsearch"
    }
}

完整请求Query DSL
curl -XGET 'localhost:9200/_search?pretty' -H 'Content-Type: application/json' -d'
{
    "query": {
        "match": {
            "tweet": "elasticsearch"
        }
    }
}
'

合并查询的语句：
查询语句(Query clauses) 就像一些简单的组合块 ，
这些组合块可以彼此之间合并组成更复杂的查询。这些语句可以是如下形式：
叶子语句（Leaf clauses） (就像 match 语句) 被用于将查询字符串和一个字段（或者多个字段）对比。
复合(Compound) 语句 主要用于 合并其它查询语句。 比如，
一个 bool 语句 允许在你需要的时候组合其它语句，无论是 must 匹配、 must_not
 匹配还是 should 匹配，同时它可以包含不评分的过滤器（filters）：
{
    "bool": {
        "must":     { "match": { "tweet": "elasticsearch" }},
        "must_not": { "match": { "name":  "mary" }},
        "should":   { "match": { "tweet": "full text" }},
        "filter":   { "range": { "age" : { "gt" : 30 }} }
    }
}
一条复合语句可以合并 任何 其它查询语句，包括复合语句，了解这一点是很重要的。
这就意味着，复合语句之间可以互相嵌套，可以表达非常复杂的逻辑。
例如，以下查询是为了找出信件正文包含 business opportunity 的星标邮件
，或者在收件箱正文包含 business opportunity 的非垃圾邮件：
{
    "bool": {
        "must": { "match":   { "email": "business opportunity" }},
        "should": [
            { "match":       { "starred": true }},
            { "bool": {
                "must":      { "match": { "folder": "inbox" }},
                "must_not":  { "match": { "spam": true }}
            }}
        ],
        "minimum_should_match": 1
    }
}
```

### 8.3 过滤(bool 查询)和查询
```
过滤：
（确切的）
created 时间是否在 2013 与 2014 这个区间？
status 字段是否包含 published 这个单词？
lat_lon 字段表示的位置是否在指定点的 10km 范围内？

查询：
查找与 full text search 这个词语最佳匹配的文档
包含 run 这个词，也能匹配 runs 、 running 、 jog 或者 sprint
包含 quick 、 brown 和 fox 这几个词 — 词之间离的越近，文档相关性越高
标有 lucene 、 search 或者 java 标签 — 标签越多，相关性越高

过滤性能高于查询

```

### 8.4 一些常用的查询：
```
match_all 查询：
{ "match_all": {}}
match 查询：
{ "match": { "tweet": "About Search" }}
{ "match": { "age":    26           }}
{ "match": { "date":   "2014-09-01" }}
{ "match": { "public": true         }}
{ "match": { "tag":    "full_text"  }}

muti_match 查询：
（多个字段）
{
    "multi_match": {
        "query":    "full text search",
        "fields":   [ "title", "body" ]
    }
}

range 查询：
{
    "range": {
        "age": {
            "gte":  20,
            "lt":   30
        }
    }
}

term 查询：（精确查询）
{ "term": { "age":    26           }}
{ "term": { "date":   "2014-09-01" }}
{ "term": { "public": true         }}
{ "term": { "tag":    "full_text"  }}

terms 查询：
{ "terms": { "tag": [ "search", "full_text", "nosql" ] }}


terms 查询和 term 查询一样，但它允许你指定多值进行匹配。
如果这个字段包含了指定值中的任何一个值，那么这个文档满足条件：

exists 查询和 missing 查询编辑
exists 查询和 missing 查询被用于查找那些指定字段中有值 
(exists) 或无值 (missing) 的文档。这与SQL中的 IS_NULL (missing) 和 
NOT IS_NULL (exists) 在本质上具有共性：

{
    "exists":   {
        "field":    "title"
    }
}
```
### 8.5 组合查询
```
must
文档 必须 匹配这些条件才能被包含进来。
must_not
文档 必须不 匹配这些条件才能被包含进来。
should
如果满足这些语句中的任意语句，将增加 _score ，否则，
无任何影响。它们主要用于修正每个文档的相关性得分。
filter
必须 匹配，但它以不评分、过滤模式来进行。这些语句对评分没有贡献，
只是根据过滤标准来排除或包含文档。

下面的查询用于查找 title 字段匹配 how to make millions 并且不被标识为
 spam 的文档。那些被标识为 starred 或在2014之后的文档，
将比另外那些文档拥有更高的排名。如果 _两者_ 都满足，那么它排名将更高：
{
    "bool": {
        "must":     { "match": { "title": "how to make millions" }},
        "must_not": { "match": { "tag":   "spam" }},
        "should": [
            { "match": { "tag": "starred" }},
            { "range": { "date": { "gte": "2014-01-01" }}}
        ]
    }
}
如果我们不想因为文档的时间而影响得分，可以用 filter 语句来重写前面的例子：

{
    "bool": {
        "must":     { "match": { "title": "how to make millions" }},
        "must_not": { "match": { "tag":   "spam" }},
        "should": [
            { "match": { "tag": "starred" }}
        ],
        "filter": {
          "range": { "date": { "gte": "2014-01-01" }} 
        }
    }
}

如果你需要通过多个不同的标准来过滤你的文档，
bool 查询本身也可以被用做不评分的查询。简单地将它放置到 filter 语句中并在内部构建布尔逻辑：
{
    "bool": {
        "must":     { "match": { "title": "how to make millions" }},
        "must_not": { "match": { "tag":   "spam" }},
        "should": [
            { "match": { "tag": "starred" }}
        ],
        "filter": {
          "bool": { 
              "must": [
                  { "range": { "date": { "gte": "2014-01-01" }}},
                  { "range": { "price": { "lte": 29.99 }}}
              ],
              "must_not": [
                  { "term": { "category": "ebooks" }}
              ]
          }
        }
    }
}

```
### 8.6 验证查询
```
验证查询：
curl -XGET 'localhost:9200/gb/tweet/_validate/query?pretty' -H 'Content-Type: application/json' -d'
{
   "query": {
      "tweet" : {
         "match" : "really powerful"
      }
   }
}
'

理解错误：
curl -XGET 's200:9200/gb/tweet/_validate/query?explain&pretty' -H 'Content-Type: application/json' -d'
{
   "query": {
      "tweet" : {
         "match" : "really powerful"
      }
   }
}
'

理解查询语句
curl -XGET 'localhost:9200/_validate/query?explain&pretty' -H 'Content-Type: application/json' -d'
{
   "query": {
      "match" : {
         "tweet" : "really powerful"
      }
   }
}
'
```

## 9,排序与相关性
### 9.1 排序
```

按照字段进行排序：
curl -XGET 'localhost:9200/_search?pretty' -H 'Content-Type: application/json' -d'
{
    "query" : {
        "bool" : {
            "filter" : { "term" : { "user_id" : 1 }}
        }
    },
    "sort": { "date": { "order": "desc" }}
}
'
多级排序
curl -XGET 'localhost:9200/_search?pretty' -H 'Content-Type: application/json' -d'
{
    "query" : {
        "bool" : {
            "must":   { "match": { "tweet": "manage text search" }},
            "filter" : { "term" : { "user_id" : 2 }}
        }
    },
    "sort": [
        { "date":   { "order": "desc" }},
        { "_score": { "order": "desc" }}
    ]
}
'

curl -XGET 's101:9200/_search?sort=date:desc&sort=_score&q=search&pretty'

```
### 9.2 ****** 排序的内容先进行跳过，后续有时间再进行整理

## 10， 分布式搜索的原理深入
```
分布式搜索的原因也可以先进行略过
```

## 11，索引的管理
### 11.1 创建一个索引
```
创建一个索引个时候，可以为其添加设置以及映射，方便后续优化搜索。
格式类似如下：
PUT /my_index
{
    "settings": { ... any settings ... },
    "mappings": {
        "type_one": { ... any mappings ... },
        "type_two": { ... any mappings ... },
        ...
    }
}


```
### 11.2 删除索引
```
用以下的请求来 删除索引:

DELETE /my_index
你也可以这样删除多个索引：

DELETE /index_one,index_two
DELETE /index_*
你甚至可以这样删除 全部 索引：

DELETE /_all
DELETE /*


```

### 11.3 索引设置
```
创建一个索引，其中主分片为一个，副分片为0
curl -XPUT 'localhost:9200/my_temp_index?pretty' -H 'Content-Type: application/json' -d'
{
    "settings": {
        "number_of_shards" :   1,
        "number_of_replicas" : 0
    }
}
'

设置索引的副分片为1：
curl -XPUT 'localhost:9200/my_temp_index/_settings?pretty' -H 'Content-Type: application/json' -d'
{
    "number_of_replicas": 1
}
'

```
### 11.4，配置分析器
```
暂时用不到，后续再进行研究
```

### 11.5 自定义分析器
```
暂时用不到，后续再进行研究
```
### 11.6 类型与映射
```
暂时用不到，后续再进行研究
```

### 11.6 根对象
```
_type,_index 等的含义
暂时用不到，后续再进行研究
```

### 11.7 动态映射
```
可以在索引中的文档格式中，有新的字段加入时，为索引设置映射。
```
### 11.8 自定义动态映射
```
暂时用不到，后续再进行研究
```

### 11.9 缺省映射
```
暂时用不到，后续再进行研究
```

### 11.10 重新索引你的数据
```
暂时用不到，后续再进行研究
```
### 11.11 索引别名和零停机（可以重新索引数据）
```
暂时用不到，后续再进行研究
```

## 12 集群内分片原理
```
先进行跳过，后续再回过头来看
```

# 二，高级搜索
## 1，结构化搜索（过滤操作，不存在评分--相关性比较，效率高）
### 1.1 精确值查找
```
首先往Es 上插入如下数据：
curl -XPOST 'localhost:9200/my_store/products/_bulk?pretty' -H 'Content-Type: application/json' -d'
{ "index": { "_id": 1 }}
{ "price" : 10, "productID" : "XHDK-A-1293-#fJ3" }
{ "index": { "_id": 2 }}
{ "price" : 20, "productID" : "KDKE-B-9947-#kL5" }
{ "index": { "_id": 3 }}
{ "price" : 30, "productID" : "JODL-X-1937-#pV7" }
{ "index": { "_id": 4 }}
{ "price" : 30, "productID" : "QQPX-R-3956-#aD8" }
'


term可以用来查询数字,boolean,日期以及文本
term 查询数字
类似如下：
curl -XGET 'localhost:9200/my_store/products/_search?pretty' -H 'Content-Type: application/json' -d'
{
    "query" : {
        "constant_score" : { 
            "filter" : {
                "term" : { 
                    "price" : 20
                }
            }
        }
    }
}
'

trem 查询文本：
curl -XGET 'localhost:9200/my_store/products/_search?pretty' -H 'Content-Type: application/json' -d'
{
    "query" : {
        "constant_score" : {
            "filter" : {
                "term" : {
                    "productID" : "XHDK-A-1293-#fJ3"
                }
            }
        }
    }
}
'

这样查询会有问题，
地不到精确的值，
原因是因为productID 被分配成了多个token，即字段，
可以用如下查看索引的分析器：
curl -XGET 'localhost:9200/my_store/_analyze?pretty' -H 'Content-Type: application/json' -d'
{
  "field": "productID",
  "text": "XHDK-A-1293-#fJ3"
}
'
它被解析为如下：
{
  "tokens" : [ {
    "token" :        "xhdk",
    "start_offset" : 0,
    "end_offset" :   4,
    "type" :         "<ALPHANUM>",
    "position" :     1
  }, {
    "token" :        "a",
    "start_offset" : 5,
    "end_offset" :   6,
    "type" :         "<ALPHANUM>",
    "position" :     2
  }, {
    "token" :        "1293",
    "start_offset" : 7,
    "end_offset" :   11,
    "type" :         "<NUM>",
    "position" :     3
  }, {
    "token" :        "fj3",
    "start_offset" : 13,
    "end_offset" :   16,
    "type" :         "<ALPHANUM>",
    "position" :     4
  } ]
}

索引，创建索引需要重新创建。（把prodectID 匹配成精确查找）
curl -XDELETE 'localhost:9200/my_store?pretty'
curl -XPUT 'localhost:9200/my_store?pretty' -H 'Content-Type: application/json' -d'
{
    "mappings" : {
        "products" : {
            "properties" : {
                "productID" : {
                    "type" : "string",
                    "index" : "not_analyzed" 
                }
            }
        }
    }
'

然后再往Es 导入开始的时候的数据
从新搜索即可。

term 查询

```
### 1.2 组合过滤器
```
布尔过滤器编辑
一个 bool 过滤器由三部分组成：
{
   "bool" : {
      "must" :     [],
      "should" :   [],
      "must_not" : [],
   }
}
must
所有的语句都 必须（must） 匹配，与 AND 等价。
must_not
所有的语句都 不能（must not） 匹配，与 NOT 等价。
should
至少有一个语句要匹配，与 OR 等价。


curl -XGET 'localhost:9200/my_store/products/_search?pretty' -H 'Content-Type: application/json' -d'
{
   "query" : {
      "filtered" : { 
         "filter" : {
            "bool" : {
              "should" : [
                 { "term" : {"price" : 20}}, 
                 { "term" : {"productID" : "XHDK-A-1293-#fJ3"}} 
              ],
              "must_not" : {
                 "term" : {"price" : 30} 
              }
           }
         }
      }
   }
}
'

嵌套的bool 过滤器
curl -XGET 'localhost:9200/my_store/products/_search?pretty' -H 'Content-Type: application/json' -d'
{
   "query" : {
      "filtered" : {
         "filter" : {
            "bool" : {
              "should" : [
                { "term" : {"productID" : "KDKE-B-9947-#kL5"}}, 
                { "bool" : { 
                  "must" : [
                    { "term" : {"productID" : "JODL-X-1937-#pV7"}}, 
                    { "term" : {"price" : 30}} 
                  ]
                }}
              ]
           }
         }
      }
   }
}
'
```

### 1.3 查找多个精确值
```
terms 查询
查找多个精确值：
curl -XGET 'localhost:9200/my_store/products/_search?pretty' -H 'Content-Type: application/json' -d'
{
    "query" : {
        "constant_score" : {
            "filter" : {
                "terms" : { 
                    "price" : [20, 30]
                }
            }
        }
    }
}
'

 term 和 terms 是 必须包含（must contain） 操作，而不是 必须精确相等（must equal exactly） 。
 
 多字段精确查找
 # Delete the `my_index` index
 DELETE /my_index
 
 # Index example docs
 PUT /my_index/my_type/1
 {
   "tags": [
     "search"
   ],
   "tag_count": 1
 }
 
 PUT /my_index/my_type/2
 {
   "tags": [
     "search",
     "open_source"
   ],
   "tag_count": 2
 }
 
 # Where tags = "search" only
 GET /my_index/my_type/_search
 {
   "query": {
     "constant_score": {
       "filter": {
         "bool": {
           "must": [
             {
               "term": {
                 "tags": "search"
               }
             },
             {
               "term": {
                 "tag_count": 1
               }
             }
           ]
         }
       }
     }
   }
 }

```

### 1.4 范围查找
```
# Delete the `my_store` index
DELETE /my_store

# Index example docs
POST /my_store/products/_bulk
{"index":{"_id":1}}
{"price":10,"productID":"XHDK-A-1293-#fJ3"}
{"index":{"_id":2}}
{"price":20,"productID":"KDKE-B-9947-#kL5"}
{"index":{"_id":3}}
{"price":30,"productID":"JODL-X-1937-#pV7"}
{"index":{"_id":4}}
{"price":30,"productID":"QQPX-R-3956-#aD8"}


# Where 20 <= `price` < 40
GET /my_store/products/_search
{
  "query": {
    "constant_score": {
      "filter": {
        "range": {
          "price": {
            "gte": 20,
            "lt": 40
          }
        }
      }
    }
  }
}

# Where `price` > 20
GET /my_store/products/_search
{
  "query": {
    "constant_score": {
      "filter": {
        "range": {
          "price": {
            "gt": 20
          }
        }
      }
    }
  }
}


时间范围：
range 查询同样可以应用在日期字段上：
"range" : {
    "timestamp" : {
        "gt" : "2014-01-01 00:00:00",
        "lt" : "2014-01-07 00:00:00"
    }
}
当使用它处理日期字段时， range 查询支持对 日期计算（date math） 
进行操作，比方说，如果我们想查找时间戳在过去一小时内的所有文档：
"range" : {
    "timestamp" : {
        "gt" : "now-1h"
    }
}
这个过滤器会一直查找时间戳在过去一个小时内的所有文档，让过滤器作
为一个时间 滑动窗口（sliding window） 来过滤文档。
日期计算还可以被应用到某个具体的时间，并非只能是一
个像 now 这样的占位符。只要在某个日期后加上一个双管符号 (||) 并紧跟一个日期数学表达式就能做到：
"range" : {
    "timestamp" : {
        "gt" : "2014-01-01 00:00:00",
        "lt" : "2014-01-01 00:00:00||+1M" 
    }
}

字典序：
5, 50, 6, B, C, a, ab, abb, abc, b

如果我们想查找从 a 到 b （不包含）的字符串，同样可以使用 range 查询语法：

"range" : {
    "title" : {
        "gte" : "a",
        "lt" :  "b"
    }
}
```
### 1.5 处理NULL 值
```
curl -XPOST 'localhost:9200/my_index/posts/_bulk?pretty' -H 'Content-Type: application/json' -d'
{ "index": { "_id": "1"              }}
{ "tags" : ["search"]                }  
{ "index": { "_id": "2"              }}
{ "tags" : ["search", "open_source"] }  
{ "index": { "_id": "3"              }}
{ "other_field" : "some data"        }  
{ "index": { "_id": "4"              }}
{ "tags" : null                      }  
{ "index": { "_id": "5"              }}
{ "tags" : ["search", null]          }
'

查看字段是否存在：
curl -XGET 'localhost:9200/my_index/posts/_search?pretty' -H 'Content-Type: application/json' -d'
{
    "query" : {
        "constant_score" : {
            "filter" : {
                "exists" : { "field" : "tags" }
            }
        }
    }
}
'


查看是否缺失
curl -XGET 'localhost:9200/my_index/posts/_search?pretty' -H 'Content-Type: application/json' -d'
{
    "query" : {
        "constant_score" : {
            "filter": {
                "missing" : { "field" : "tags" }
            }
        }
    }
}
'

内部对象的缺失会被解析成如下：
{
    "exists" : { "field" : "name" }
}
实际执行的是：

{
    "bool": {
        "should": [
            { "exists": { "field": "name.first" }},
            { "exists": { "field": "name.last" }}
        ]
    }
}
```

### 1.6， 关于缓存
```
```
## 2, 全文搜索（设计相关性和分析，非过滤）
###2.1, 基于词项检索还是全文检索
###2.2，匹配查询
```
插入数据
curl -XDELETE 'localhost:9200/my_index?pretty'
curl -XPUT 'localhost:9200/my_index?pretty' -H 'Content-Type: application/json' -d'
{ "settings": { "number_of_shards": 1 }}
'
curl -XPOST 'localhost:9200/my_index/my_type/_bulk?pretty' -H 'Content-Type: application/json' -d'
{ "index": { "_id": 1 }}
{ "title": "The quick brown fox" }
{ "index": { "_id": 2 }}
{ "title": "The quick brown fox jumps over the lazy dog" }
{ "index": { "_id": 3 }}
{ "title": "The quick brown fox jumps over the quick dog" }
{ "index": { "_id": 4 }}
{ "title": "Brown fox brown dog" }
'

单个词语的匹配：
curl -XGET 'localhost:9200/my_index/my_type/_search?pretty' -H 'Content-Type: application/json' -d'
{
    "query": {
        "match": {
            "title": "QUICK!"
        }
    }
}
'
```
### 2.3 多词查询
```
查询多个词的匹配
curl -XGET 'localhost:9200/my_index/my_type/_search?pretty' -H 'Content-Type: application/json' -d'
{
    "query": {
        "match": {
            "title": "BROWN DOG!"
        }
    }
}
'

提高精度：
curl -XGET 'localhost:9200/my_index/my_type/_search?pretty' -H 'Content-Type: application/json' -d'
{
    "query": {
        "match": {
            "title": {      
                "query":    "BROWN DOG!",
                "operator": "and"
            }
        }
    }
}
'
控制精度：
curl -XGET 'localhost:9200/my_index/my_type/_search?pretty' -H 'Content-Type: application/json' -d'
{
  "query": {
    "match": {
      "title": {
        "query":                "quick brown dog",
        "minimum_should_match": "75%"
      }
    }
  }
}
'

```
### 2.4 组合查询
```
curl -XGET 'localhost:9200/my_index/my_type/_search?pretty' -H 'Content-Type: application/json' -d'
{
  "query": {
    "bool": {
      "must":     { "match": { "title": "quick" }},
      "must_not": { "match": { "title": "lazy"  }},
      "should": [
                  { "match": { "title": "brown" }},
                  { "match": { "title": "dog"   }}
      ]
    }
  }
}
'
控制精度：
curl -XGET 'localhost:9200/my_index/my_type/_search?pretty' -H 'Content-Type: application/json' -d'
{
  "query": {
    "bool": {
      "should": [
        { "match": { "title": "brown" }},
        { "match": { "title": "fox"   }},
        { "match": { "title": "dog"   }}
      ],
      "minimum_should_match": 2 
    }
  }
}
'

```

### 2.5 bool匹配的使用
```
等价操作
{
    "match": { "title": "brown fox"}
}
{
  "bool": {
    "should": [
      { "term": { "title": "brown" }},
      { "term": { "title": "fox"   }}
    ]
  }
}

等价操作：
{
    "match": {
        "title": {
            "query":    "brown fox",
            "operator": "and"
        }
    }
}
{
  "bool": {
    "must": [
      { "term": { "title": "brown" }},
      { "term": { "title": "fox"   }}
    ]
  }
}

等价操作：
{
    "match": {
        "title": {
            "query":                "quick brown fox",
            "minimum_should_match": "75%"
        }
    }
}
{
  "bool": {
    "should": [
      { "term": { "title": "brown" }},
      { "term": { "title": "fox"   }},
      { "term": { "title": "quick" }}
    ],
    "minimum_should_match": 2 
  }
}
```

### 2.6  权重处理，那个返回的更重要
```
普通的不加权重的情况下：
curl -XGET 'localhost:9200/_search?pretty' -H 'Content-Type: application/json' -d'
{
    "query": {
        "bool": {
            "must": {
                "match": {
                    "content": { 
                        "query":    "full text search",
                        "operator": "and"
                    }
                }
            },
            "should": [ 
                { "match": { "content": "Elasticsearch" }},
                { "match": { "content": "Lucene"        }}
            ]
        }
    }
}
'

加了权重的情况下：
curl -XGET 'localhost:9200/_search?pretty' -H 'Content-Type: application/json' -d'
{
    "query": {
        "bool": {
            "must": {
                "match": {  
                    "content": {
                        "query":    "full text search",
                        "operator": "and"
                    }
                }
            },
            "should": [
                { "match": {
                    "content": {
                        "query": "Elasticsearch",
                        "boost": 3 
                    }
                }},
                { "match": {
                    "content": {
                        "query": "Lucene",
                        "boost": 2 
                    }
                }}
            ]
        }
    }
}
'
```
### 2.7 控制分析过程
```
为字段设置分析器
curl -XPUT 'localhost:9200/my_index/_mapping/my_type?pretty' -H 'Content-Type: application/json' -d'
{
    "my_type": {
        "properties": {
            "english_title": {
                "type":     "string",
                "analyzer": "english"
            }
        }
    }
}
'

查看分析器的分析行为
curl -XGET 'localhost:9200/my_index/_analyze?pretty' -H 'Content-Type: application/json' -d'
{
  "field": "my_type.title",   
  "text": "Foxes"
}
'
curl -XGET 'localhost:9200/my_index/_analyze?pretty' -H 'Content-Type: application/json' -d'
{
  "field": "my_type.english_title",   
  "text": "Foxes"
}
'

分析过程：
查询自己定义的 analyzer ，否则
字段映射里定义的 search_analyzer ，否则
字段映射里定义的 analyzer ，否则
索引设置中名为 default_search 的分析器，默认为
索引设置中名为 default 的分析器，默认为
standard 标准分析器
```

## 3， 多字段搜索
### 3.1 多字段查询
```
例子1：
curl -XGET 'localhost:9200/_search?pretty' -H 'Content-Type: application/json' -d'
{
  "query": {
    "bool": {
      "should": [
        { "match": { "title":  "War and Peace" }},
        { "match": { "author": "Leo Tolstoy"   }}
      ]
    }
  }
}
'

例子2：
curl -XGET 'localhost:9200/_search?pretty' -H 'Content-Type: application/json' -d'
{
  "query": {
    "bool": {
      "should": [
        { "match": { "title":  "War and Peace" }},
        { "match": { "author": "Leo Tolstoy"   }},
        { "bool":  {
          "should": [
            { "match": { "translator": "Constance Garnett" }},
            { "match": { "translator": "Louise Maude"      }}
          ]
        }}
      ]
    }
  }
}
'


语句的有限级，一般上根据权重来处理：

curl -XGET 'localhost:9200/_search?pretty' -H 'Content-Type: application/json' -d'
{
  "query": {
    "bool": {
      "should": [
        { "match": { 
            "title":  {
              "query": "War and Peace",
              "boost": 2
        }}},
        { "match": { 
            "author":  {
              "query": "Leo Tolstoy",
              "boost": 2
        }}},
        { "bool":  { 
            "should": [
              { "match": { "translator": "Constance Garnett" }},
              { "match": { "translator": "Louise Maude"      }}
            ]
        }}
      ]
    }
  }
}
'
其中bool 查询的权重为1
```



