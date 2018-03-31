改变默认的可以返回的最大条数。
```
curl -XPUT 's109:9200/dynamic/_settings' -d '{
    "index": {
        "max_result_window": 1000000000
    }
}'
```
