Fielddata is disabled on text fields by default
```
根据es官网的文档执行

GET /megacorp/employee/_search
{
  "aggs": {
    "all_interests": {
      "terms": { "field": "interests" }
    }
  }
}
这个例子时，报错

{
  "error": {
    "root_cause": [
      {
        "type": "illegal_argument_exception",
        "reason": "Fielddata is disabled on text fields by default. Set fielddata=true on [interests] in order to load fielddata in memory by uninverting the inverted index. Note that this can however use significant memory."
      }
    ],
    "type": "search_phase_execution_exception",
    "reason": "all shards failed",
    "phase": "query",
    "grouped": true,
    "failed_shards": [
      {
        "shard": 0,
        "index": "megacorp",
        "node": "-Md3f007Q3G6HtdnkXoRiA",
        "reason": {
          "type": "illegal_argument_exception",
          "reason": "Fielddata is disabled on text fields by default. Set fielddata=true on [interests] in order to load fielddata in memory by uninverting the inverted index. Note that this can however use significant memory."
        }
      }
    ],
    "caused_by": {
      "type": "illegal_argument_exception",
      "reason": "Fielddata is disabled on text fields by default. Set fielddata=true on [interests] in order to load fielddata in memory by uninverting the inverted index. Note that this can however use significant memory."
    }
  },
  "status": 400
}
搜了一下应该是5.x后对排序，聚合这些操作用单独的数据结构(fielddata)缓存到内存里了，需要单独开启，官方解释在此fielddata

简单来说就是在聚合前执行如下操作

PUT megacorp/_mapping/employee/
{
  "properties": {
    "interests": { 
      "type":     "text",
      "fielddata": true
    }
  }
}
```