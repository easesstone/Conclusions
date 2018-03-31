```
curl -XPUT "http://s103:9200/spark/_settings" -H 'Content-Type: application/json' -d'
{
    "number_of_replicas": 3
}'
```
