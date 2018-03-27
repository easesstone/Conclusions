```
You can execute the script by specifying its lang as expert_scripts, and the name of the script as the the script source:

POST /_search
{
  "query": {
    "function_score": {
      "query": {
        "match": {
          "body": "foo"
        }
      },
      "functions": [
        {
          "script_score": {
            "script": {
                "inline": "pure_df",
                "lang" : "expert_scripts",
                "params": {
                    "field": "body",
                    "term": "foo"
                }
            }
          }
        }
      ]
    }
  }
}


```