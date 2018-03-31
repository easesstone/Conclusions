```

在ElasticSearch中，使用JSON结构来存储数据，一个Key/Value对是JSON的一个字段，而Value可以是基础数据类型，也可以是数组，文档（也叫对象），或文档数组，因此，每个JSON文档都内在地具有层次结构。复合数据类型是指数组类型，对象类型和嵌套类型，各个类型的特点分别是：

数组字段是指一个字段有多个值，每个值都是该数组字段的一个元素；元素的类型可以是基础类型，也可以是文档类型；
对象类型是指字段的值是一个JSON文档；
嵌套字段是指对象类型的一个特殊版本，ElasticSearch引擎在内部把嵌套字段索引成单个文档。如果在嵌套字段中定义对象数组，那么对象数组中的每个元素（文档）都被索引成单个文档，每个文档都能被独立地查询。
一，对象类型

JSON文档是有层次结构的，一个文档可能包含其他文档，如果一个文档包含其他文档，那么该文档值是对象类型，其数据类型是对象，ElasticSearch默认把文档的属性type设置为object，即"type":"object"。

例如，在创建索引映时，定义name字段为对象类型，不需要显式定义type属性值，其默认值是object：

"manager":{  
   "properties":{  
      "age":{ "type":"integer"},
      "name":{  
         "properties":{  
            "first":{"type":"string"},
            "last":{ "type":"string"}
         }
      }
   }
}
默认情况下，上述文档类型被索引为以点号命名的数据结构，把层次结构展开之后，数据结构是由扁平的key/value对构成：

{
  "manager.age":        30,
  "manager.name.first": "John",
  "manager.name.last":  "Smith"
}
二，开箱即用的数组类型

在ElasticSearch中，没有专门的数组（Array）数据类型，但是，在默认情况下，任意一个字段都可以包含0或多个值，这意味着每个字段默认都是数组类型，只不过，数组类型的各个元素值的数据类型必须相同。在ElasticSearch中，数组是开箱即用的（out of box），不需要进行任何配置，就可以直接使用。

1，数组类型

在同一个数组中，数组元素的数据类型是相同的，ElasticSearch不支持元素为多个数据类型：[ 10, "some string" ]，常用的数组类型是：

字符数组: [ "one", "two" ]
整数数组: productid:[ 1, 2 ]
对象（文档）数组: "user":[ { "name": "Mary", "age": 12 }, { "name": "John", "age": 10 }]，ElasticSearch内部把对象数组展开为 {"user.name": ["Mary", "John"], "user.age": [12,10]}
对于文档数组，每个元素都是结构相同的文档，文档之间都不是独立的，在文档数组中，不能独立于其他文档而去查询单个文档，这是因为，一个文档的内部字段之间的关联被移除，各个文档共同构成对象数组。

对整数数组进行查询，例如，使用多词条（terms）查询类型，查询productid为1和2的文档：

{  
   "query":{  
      "terms":{  
         "productid":[ 1, 2 ]
      }
   }
}
2，对象数组

通过PUT动词，自动创建索引和文档类型，在文档中创建对象数组：

PUT my_index/my_type/1
{
  "group" : "fans",
  "user" : [ 
    {
      "first" : "John",
      "last" :  "Smith"
    },
    {
      "first" : "Alice",
      "last" :  "White"
    }
  ]
}
ElasticSearch引擎内部把对象数组展开成扁平的数据结构，把上例的文档类型的数据结构展开之后，文档数据类似于：

{
  "group" :        "fans",
  "user.first" : [ "alice", "john" ],
  "user.last" :  [ "smith", "white" ]
}
字段 user.first 和 user.last 被展开成数组字段，但是，这样展开之后，单个文档内部的字段之间的关联就会丢失，在该例中，展开的文档数据丢失first和last字段之间的关联，比如，Alice 和 white 的关联就丢失了。

三，嵌套数据类型

嵌套数据类型是对象数据类型的特殊版本，它允许对象数组中的各个对象被索引，数组中的各个对象之间保持独立，能够对每一个文档进行单独查询，这就意味着，嵌套数据类型保留文档的内部之间的关联，ElasticSearch引擎内部使用不同的方式处理嵌套数据类型和对象数组的方式，对于嵌套数据类型，ElasticSearch把数组中的每一个嵌套文档（Nested Document）索引为单个文档，这些文档是隐藏（Hidden）的，文档之间是相互独立的，但是，保留文档的内部字段之间的关联，使用嵌套查询（Nested Query）能够独立于其他文档而去查询单个文档。在创建嵌套数据类型的字段时，需要设置字段的type属性为nested。

1，在索引映射中创建嵌套字段

设置user字段为嵌套数据类型，由于每个字段默认都可以是数组类型，因此，嵌套字段也可以是对象数组。

"mappings":{  
   "my_type":{  
      "properties":{  
         "group":{ "type":"string"},
         "user":{  
            "type":"nested",
            "properties":{  
               "first":{ "type":"string"},
               "second":{  "type":"string"}
            }
         }
      }
   }
}
2，为嵌套字段赋值

为嵌套字段赋予多个值，那么ElasticSearch自动把字段值转换为数组类型。

PUT my_index/my_type/1
{
  "group" : "fans",
  "user" : [ 
    { "first" : "John", "last" :  "Smith"},
    { "first" : "Alice", "last" :  "White"}
  ]
}
在ElasticSearch内部，嵌套的文档（Nested Documents）被索引为很多独立的隐藏文档（separate documents），这些隐藏文档只能通过嵌套查询（Nested Query）访问。每一个嵌套的文档都是嵌套字段（文档数组）的一个元素。嵌套文档的内部字段之间的关联被ElasticSearch引擎保留，而嵌套文档之间是相互独立的。在该例中，ElasticSearch引起保留Alice和White之间的关联，而John和White之间是没有任何关联的。

默认情况下，每个索引最多创建50个嵌套文档，可以通过索引设置选项：index.mapping.nested_fields.limit 修改默认的限制。

Indexing a document with 100 nested fields actually indexes 101 documents as each nested document is indexed as a separate document.

四，嵌套查询

嵌套查询用于查询嵌套对象，执行嵌套查询执行的条件是：嵌套对象被索引为单个文档，查询作用在根文档（Root Parent）上。嵌套查询由关键字“nested”指定：

"nested" : {
        "path" : "obj1",
        "query" : {...}
1，必须赋值的参数：

path参数：指定嵌套字段的文档路径，根路径是顶层的文档，通过点号“.”来指定嵌套文档的路径；
query参数：在匹配路径（参数path）的嵌套文档上执行查询，query参数指定对嵌套文档执行的查询条件。
2，使用嵌套查询访问嵌套文档

GET my_index/_search
{
  "query": {
    "nested": {
      "path": "user",
      "query": {
        "bool": {
          "must": [
            { "match": { "user.first": "Alice" }},
            { "match": { "user.last":  "White" }} 
          ]
        }
      }
    }
  }
}
五，使用C#索引数组类型

1，创建ElasticSearch的索引映射

技术分享
{  
   "settings":{  
      "number_of_shards":5,
      "number_of_replicas":0
   },
   "mappings":{  
      "events":{  
        "dynamic":"false",
         "properties":{  
            "eventid":{  
               "type":"long",
               "store":true,
               "index":"not_analyzed"
            },
            "eventname":{  
               "type":"string",
               "store":true,
               "index":"analyzed",
               "fields":{  
                  "raw":{  
                     "type":"string",
                     "store":true,
                     "index":"not_analyzed"
                  }
               }
            },
            "topics":{  
               "type":"integer",
               "store":true,
               "index":"analyzed"
            }
         }
      }
   }
}
View Code
对于topics字段，类型是integer，赋予其一组整数值[1,2,3]，那么该字段就能存储数组。

"topics":{  
    "type":"integer",
    "store":true,
    "index":"analyzed"
}
2，创建数据模型（Data Model）

为数组字段定义为List类型，每个列表项的数据类型是int。

public class EventBase
{
    public long eventid { get; set; }
}

public class EbrieEvents:EventBase
{
    public string eventname { get; set; }
    public List<int> topics { get; set; }
}
3，为字段赋值

为List字段topics赋值，调用NEST对该文档进行索引

EbrieEvents pb = new EbrieEvents();

//Topics List
List<string> strTopics = TableRow["Topics"].ToString().TrimEnd(‘,‘).Split(‘,‘).ToList();
List<int> topics = new List<int>();
foreach(string str in strTopics)
{
    topics.Add(int.Parse(str));
}
pb.topics = topics;  
4，查询数组字段

{  
   "query":{  
      "terms":{  
         "topics":[1001,487]
      }
   }
}
```
