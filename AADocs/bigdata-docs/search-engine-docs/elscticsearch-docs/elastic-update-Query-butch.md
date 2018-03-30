# ElasticSearch 进行个别字段的更新
#### 1,更新setting
```
curl -XPUT "http://s103:9200/dynamic/_settings" -H 'Content-Type: application/json' -d'
{
  "settings": {
    "index.mapping.total_fields.limit": 2000
  }
}'

PUT dynamic/_settings
{
  "settings": {
    "index.mapping.total_fields.limit": 2000
  }
}

```

#### 2,更新字段(注意如果使用kibana 可能会超时)
```
curl -XPOST "http://s103:9200/dynamic/person/_update_by_query" -H 'Content-Type: application/json' -d'
{
  "script": {
    "lang": "painless",
    "inline": "ctx._source.eyeglasses = ctx._source.eleglasses"
  }
}'



POST dynamic/person/_update_by_query
{
  "script": {
    "lang": "painless",
    "inline": "ctx._source.eyeglasses = ctx._source.eleglasses"
  }
}

```

#### 3，kabana 超时设置elasticsearch.requestTimeout
```
cat kibana.yml  | grep Timeout
# the elasticsearch.requestTimeout setting.
#elasticsearch.pingTimeout: 1500
elasticsearch.requestTimeout: 3000000
#elasticsearch.shardTimeout: 0
#elasticsearch.startupTimeout: 5000

```