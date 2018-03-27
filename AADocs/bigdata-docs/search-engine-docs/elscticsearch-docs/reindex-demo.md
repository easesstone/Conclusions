```

POST _reindex
{
  "source": {
    "index": "dynamic"
  },
  "dest": {
    "index": "dynamic_demo",
    "op_type": "create"
  }
}


curl -XPOST "http://s103:9200/_reindex" -H 'Content-Type: application/json' -d'
{
  "source": {
    "index": "dynamic"
  },
  "dest": {
    "index": "dynamic_demo",
    "op_type": "create"
  }
}'

    
    
first to create an index

#!/bin/bash
## 动态信息库person 表的映射
curl -XDELETE 's105:9200/dynamic_demo?pretty'  -H 'Content-Type: application/json'
curl -XPUT 's105:9200/dynamic_demo?pretty' -H 'Content-Type: application/json' -d'
{
    "settings": {
        "analysis": {
            "filter": {
                "trigrams_filter": {
                    "type":     "ngram",
                    "min_gram": 2,
                    "max_gram": 20
                }
            },
            "analyzer": {
                "trigrams": {
                    "type":      "custom",
                    "tokenizer": "standard",
                    "filter":   [
                        "lowercase",
                        "trigrams_filter"
                    ]
                },
                "ik": {
                    "tokenizer" : "ik_max_word"
                }
            }
        }
    },
    "mappings": {
         "person": {
              "properties": {
                    "ftpurl" : {
                                          "type" : "text"
                              },
                              "eyeglasses" : {
                                           "type" : "long"
                               },
                              "gender" : {
                                           "type" : "long"
                              },
                              "haircolor" : {
                                           "type" : "long"
                              },
                              "hairstyle" : {
                                           "type" : "long"
                              },
                              "hat" : {
                                           "type" : "long"
                              },
                              "huzi" : {
                                          "type" : "long"
                              },
                              "tie" : {
                                           "type" : "long"
                              },
                              "ipcid" : {
                                           "type" : "text"
                              },
                              "timeslot" : {
                                           "type" : "text"
                              },
                              "date" : {
                                            "type" : "text"
                              },
                              "timestamp" : {
                                  "type" : "date",
                                  "format": "yyyy-MM-dd HH:mm:ss"
                              },
                              "exacttime" : {
                                  "type" : "date",
                                  "format": "yyyy-MM-dd HH:mm:ss"
                              },
                              "searchtype" : {
                                  "type" : "text"
                              }
                    }
              }
        }
    }
'


curl -XPUT 's109:9200/dynamic/_settings' -d '{
    "index": {
        "max_result_window": 1000000000
    }
}'



```