# 聚合
## 基本概念
> 桶（Buckets）

满足条件的文档的集合。  

> 指标 (Metrics)

对桶内的文档进行统计计算。  

> 聚合 (Aggregations) 

每个集合都是一个或者多个桶和零个或者多个指标的组合。翻译成Sql 语句如下：  
```
SELECT COUNT(color)
FROM table
GROUP BY color 
```
COUNT(color) 指标， GROUP BY 桶。  
聚合可能只有一个桶，可能只有一个指标，或者两者都有。  

## 高阶概念
> 桶

简单的说就是满足条件的文档的集合，基于条件来划分文档。  
桶可以嵌套桶，相当于传统SQL 语句中的嵌套查询。  

例如：  
一个雇员属于 男性桶或者 女性桶  
奥尔巴尼属于 纽约桶  

> 指标  

最大值，最小值，平均值，还有汇总。  
如平均工资，最高出售价格等。  

## 聚合尝试  
### 1，数据索引
以下是一批车的数据
```
POST /cars/transactions/_bulk
{ "index": {}}
{ "price" : 10000, "color" : "red", "make" : "honda", "sold" : "2014-10-28" }
{ "index": {}}
{ "price" : 20000, "color" : "red", "make" : "honda", "sold" : "2014-11-05" }
{ "index": {}}
{ "price" : 30000, "color" : "green", "make" : "ford", "sold" : "2014-05-18" }
{ "index": {}}
{ "price" : 15000, "color" : "blue", "make" : "toyota", "sold" : "2014-07-02" }
{ "index": {}}
{ "price" : 12000, "color" : "green", "make" : "toyota", "sold" : "2014-08-19" }
{ "index": {}}
{ "price" : 20000, "color" : "red", "make" : "honda", "sold" : "2014-11-05" }
{ "index": {}}
{ "price" : 80000, "color" : "red", "make" : "bmw", "sold" : "2014-01-01" }
{ "index": {}}
{ "price" : 25000, "color" : "blue", "make" : "ford", "sold" : "2014-02-12" }
```

### 2,入门聚合操作 
查询那个颜色的车的销量情况。  
```
curl -XGET 'localhost:9200/cars/transactions/_search?pretty' -H 'Content-Type: application/json' -d'
{
    "size" : 0,
    "aggs" : {                  
        "popular_colors" : { 
            "terms" : { 
              "field" : "color"
            }
        }
    }
}
'


1，聚合操作会被放在aggs之下，（也可以用全名，aggregations）
2，popular_colors。给聚合取一个名字
3，最后定义单个桶的类型terms

返回结果类似如下：
{
...
   "hits": {
      "hits": [] 
   },
   "aggregations": {
      "popular_colors": { 
         "buckets": [
            {
               "key": "red", 
               "doc_count": 4 
            },
            {
               "key": "blue",
               "doc_count": 2
            },
            {
               "key": "green",
               "doc_count": 2
            }
         ]
      }
   }
}

```
### 添加度量指标
统计各个颜色车的销量数据，各个颜色的车的平均价格。桶里面嵌套桶。
```
curl -XGET 'localhost:9200/cars/transactions/_search?pretty' -H 'Content-Type: application/json' -d'
{
   "size" : 0,
   "aggs": {
      "colors": {
         "terms": {
            "field": "color"
         },
         "aggs": { 
            "avg_price": { 
               "avg": {
                  "field": "price" 
               }
            }
         }
      }
   }
}
'

```

### Terms 桶

### 嵌套桶
计算各个颜色下的车辆的制造商。  
```
curl -XGET 'localhost:9200/cars/transactions/_search?pretty' -H 'Content-Type: application/json' -d'
{
   "size" : 0,
   "aggs": {
      "colors": {
         "terms": {
            "field": "color"
         },
         "aggs": {
            "avg_price": { 
               "avg": {
                  "field": "price"
               }
            },
            "make": { 
                "terms": {
                    "field": "make" 
                }
            }
         }
      }
   }
}
'


注意前例中的 avg_price 度量仍然保持原位。
另一个聚合 make 被加入到了 color 颜色桶中。
这个聚合是 terms 桶，它会为每个汽车制造商生成唯一的桶。

返回结果大致如下：

{
  "took": 6,
  "timed_out": false,
  "_shards": {
    "total": 5,
    "successful": 5,
    "failed": 0
  },
  "hits": {
    "total": 32,
    "max_score": 0,
    "hits": []
  },
  "aggregations": {
    "colors": {
      "doc_count_error_upper_bound": 0,
      "sum_other_doc_count": 0,
      "buckets": [
        {
          "key": "red",
          "doc_count": 16,
          "avg_price": {
            "value": 32500
          },
          "make": {
            "doc_count_error_upper_bound": 0,
            "sum_other_doc_count": 0,
            "buckets": [
              {
                "key": "honda",
                "doc_count": 12
              },
              {
                "key": "bmw",
                "doc_count": 4
              }
            ]
          }
        },
        {
          "key": "blue",
          "doc_count": 8,
          "avg_price": {
            "value": 20000
          },
          "make": {
            "doc_count_error_upper_bound": 0,
            "sum_other_doc_count": 0,
            "buckets": [
              {
                "key": "ford",
                "doc_count": 4
              },
              {
                "key": "toyota",
                "doc_count": 4
              }
            ]
          }
        },
        {
          "key": "green",
          "doc_count": 8,
          "avg_price": {
            "value": 21000
          },
          "make": {
            "doc_count_error_upper_bound": 0,
            "sum_other_doc_count": 0,
            "buckets": [
              {
                "key": "ford",
                "doc_count": 4
              },
              {
                "key": "toyota",
                "doc_count": 4
              }
            ]
          }
        }
      ]
    }
  }
}


```
### min 与 max
每个汽车生成商计算最低和最高的价格
```
curl -XGET 'localhost:9200/cars/transactions/_search?pretty' -H 'Content-Type: application/json' -d'
{
   "size" : 0,
   "aggs": {
      "colors": {
         "terms": {
            "field": "color"
         },
         "aggs": {
            "avg_price": { "avg": { "field": "price" }
            },
            "make" : {
                "terms" : {
                    "field" : "make"
                },
                "aggs" : { 
                    "min_price" : { "min": { "field": "price"} }, 
                    "max_price" : { "max": { "field": "price"} } 
                }
            }
         }
      }
   }
}
'

结果大概如下：
{
  "took": 20,
  "timed_out": false,
  "_shards": {
    "total": 5,
    "successful": 5,
    "failed": 0
  },
  "hits": {
    "total": 32,
    "max_score": 0,
    "hits": []
  },
  "aggregations": {
    "colors": {
      "doc_count_error_upper_bound": 0,
      "sum_other_doc_count": 0,
      "buckets": [
        {
          "key": "red",
          "doc_count": 16,
          "avg_price": {
            "value": 32500
          },
          "make": {
            "doc_count_error_upper_bound": 0,
            "sum_other_doc_count": 0,
            "buckets": [
              {
                "key": "honda",
                "doc_count": 12,
                "max_price": {
                  "value": 20000
                },
                "min_price": {
                  "value": 10000
                }
              },
              {
                "key": "bmw",
                "doc_count": 4,
                "max_price": {
                  "value": 80000
                },
                "min_price": {
                  "value": 80000
                }
              }
            ]
          }
        },
        {
          "key": "blue",
          "doc_count": 8,
          "avg_price": {
            "value": 20000
          },
          "make": {
            "doc_count_error_upper_bound": 0,
            "sum_other_doc_count": 0,
            "buckets": [
              {
                "key": "ford",
                "doc_count": 4,
                "max_price": {
                  "value": 25000
                },
                "min_price": {
                  "value": 25000
                }
              },
              {
                "key": "toyota",
                "doc_count": 4,
                "max_price": {
                  "value": 15000
                },
                "min_price": {
                  "value": 15000
                }
              }
            ]
          }
        },
        {
          "key": "green",
          "doc_count": 8,
          "avg_price": {
            "value": 21000
          },
          "make": {
            "doc_count_error_upper_bound": 0,
            "sum_other_doc_count": 0,
            "buckets": [
              {
                "key": "ford",
                "doc_count": 4,
                "max_price": {
                  "value": 30000
                },
                "min_price": {
                  "value": 30000
                }
              },
              {
                "key": "toyota",
                "doc_count": 4,
                "max_price": {
                  "value": 12000
                },
                "min_price": {
                  "value": 12000
                }
              }
            ]
          }
        }
      ]
    }
  }
}
```






