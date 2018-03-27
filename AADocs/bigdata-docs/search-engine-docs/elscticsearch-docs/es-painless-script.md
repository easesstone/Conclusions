```
PUT hockey/player/_bulk?refresh 
{"index":{"_id":1}} 
{"first":"johnny","last":"gaudreau","goals":[9,27,1],"assists":[17,46,0],"gp":[26,82,1],"born":"1993/08/13"} 
{"index":{"_id":2}} 
{"first":"sean","last":"monohan","goals":[7,54,26],"assists":[11,26,13],"gp":[26,82,82],"born":"1994/10/12"} 
{"index":{"_id":3}} 
{"first":"jiri","last":"hudler","goals":[5,34,36],"assists":[11,62,42],"gp":[24,80,79],"born":"1984/01/04"} 
{"index":{"_id":4}} 
{"first":"micheal","last":"frolik","goals":[4,6,15],"assists":[8,23,15],"gp":[26,82,82],"born":"1988/02/17"} 
{"index":{"_id":5}} 
{"first":"sam","last":"bennett","goals":[5,0,0],"assists":[8,1,0],"gp":[26,1,0],"born":"1996/06/20"} 
{"index":{"_id":6}} 
{"first":"dennis","last":"wideman","goals":[0,26,15],"assists":[11,30,24],"gp":[26,81,82],"born":"1983/03/20"} 
{"index":{"_id":7}} 
{"first":"david","last":"jones","goals":[7,19,5],"assists":[3,17,4],"gp":[26,45,34],"born":"1984/08/10"} 
{"index":{"_id":8}} 
{"first":"tj","last":"brodie","goals":[2,14,7],"assists":[8,42,30],"gp":[26,82,82],"born":"1990/06/07"} 
{"index":{"_id":39}} 
{"first":"mark","last":"giordano","goals":[6,30,15],"assists":[3,30,24],"gp":[26,60,63],"born":"1983/10/03"} 
{"index":{"_id":10}} 
{"first":"mikael","last":"backlund","goals":[3,15,13],"assists":[6,24,18],"gp":[26,82,82],"born":"1989/03/17"} 
{"index":{"_id":11}} 
{"first":"joe","last":"colborne","goals":[3,18,13],"assists":[6,20,24],"gp":[26,67,82],"born":"1990/01/30"}

GET /hockey/_search
{
  "query": {
    "function_score": {
      "script_score": {
        "script": {
          "lang": "painless",
          "inline": "float []aa = new float[]{1, 2, 3, 4, 5, 6};int total = 0; for(int i = 0; i < doc['goals'].length; ++i) {total += doc['goals'][i];} return Math.sqrt(total);"
        }
      }
    }
  }
}

Map<String, Object> params = new HashMap<>();
params.put("num1", 10);
params.put("num2", 4);

String inlineScript = "doc['score'].value * num1 * num2";

Script script = new Script(script, ScriptType.INLINE, "groovy", params);

ScriptScoreFunctionBuilder scriptBuilder = ScoreFunctionBuilders.scriptFunction(ss);


GET /hockey/_search
{
  "query": {
    "function_score": {
      "script_score": {
        "script": {
          "lang": "painless",
          "inline": "int total = 0; for(int i = 0; i < doc['goals'].length; ++i) {total += doc['goals'][i];} return Math.sqrt(total);"
        }
      }
    }
  }
}

GET /hockey/_search
{
  "query": {
    "function_score": {
      "script_score": {
        "script": {
          "lang": "painless",
          "inline": "int total = 0; for(int i = 0; i < doc['goals'].length; ++i) {total += doc['goals'][i];} return total;"
        }
      }
    }
  }
}

返回结果：
{
  "took": 5,
  "timed_out": false,
  "_shards": {
    "total": 5,
    "successful": 5,
    "failed": 0
  },
  "hits": {
    "total": 11,
    "max_score": 87,
    "hits": [
      {
        "_index": "hockey",
        "_type": "player",
        "_id": "2",
        "_score": 87,
        "_source": {
          "first": "sean",
          "last": "monohan",
          "goals": [
            7,
            54,
            26
          ],
          "assists": [
            11,
            26,
            13
          ],
          "gp": [
            26,
            82,
            82
          ],
          "born": "1994/10/12"
        }
      },
      {
        "_index": "hockey",
        "_type": "player",
        "_id": "3",
        "_score": 75,
        "_source": {
          "first": "jiri",
          "last": "hudler",
          "goals": [
            5,
            34,
            36
          ],
          "assists": [
            11,
            62,
            42
          ],
          "gp": [
            24,
            80,
            79
          ],
          "born": "1984/01/04"
        }
      },
.........


GET /hockey/_search
{
  "query": {
    "match_all": {}
  },
  "sort": {
    "_script":{
      "type": "string",
      "order": "asc",
      "script": {
        "lang": "painless",
        "inline": "doc['first.keyword'].value + '' + doc['last.keyword'].value"
      }
    }
  }
}
"hits": {
"total": 11,
"max_score": null,
"hits": [
{
"_index": "hockey",
"_type": "player",
"_id": "7",
"_score": null,
"_source": {
  "first": "david",
  "last": "jones",
  "goals": [
    7,
    19,
    5
  ],
  "assists": [
    3,
    17,
    4
  ],
  "gp": [
    26,
    45,
    34
  ],
  "born": "1984/08/10"
},
"sort": [
  "davidjones"
]
},
.......
这里需要注意几点：

这里都是_search操作，多个操作之间会形成管道，
既query::match_all的输出会作为script_fields或者sort的输入。
_search操作中所有的返回值，都可以通过一个map类型变量doc获取。
和所有其他脚本语言一样，用[]获取map中的值。这里要强调的是，
doc只可以在_search中访问到。在下一节的例子中，你将看到，使用的是ctx。
_search操作是不会改变document的值的，即便是script_fields，
你只能在当次查询是能看到script输出的值。
doc['first.keyword']这样的写法是因为doc[]返回有可能是分词之后的value，
所以你想要某个field的完整值时，请使用keyword

跟新数据：

POST hockey/player/1/_update 
{
  "script": {
    "lang": "painless",
    "inline": "ctx._source.last = params.last; ctx._source.nick = params.nick", 
    "params": {
      "last": "gaudreau",
      "nick": "hockey"
    }
  }
}

POST hockey/player/1/_update 
{
  "script": {
    "lang": "painless",
    "inline": "ctx._source.born = params.born", 
    "params": {
      "born": "1993/08/16"
    }
  }
}


正则表达式
日期与常规值有所不同。 这是一个返回每个玩家诞生年份的例子：
GET hockey/_search
{
  "script_fields": {
    "birth_year": {
      "script": {
        "inline": "doc.born.date.year"
      }
    }
  }
}
这里的关键是不能直接编入 doc.born，就像您正常的字段，
你必须调用 doc.born.date 来获取一个 ReadableDateTime 。 
从那里可以调用 getYear 和 getDayOfWeek 等方法。 在上面的例子中，getYear() 的一个快捷方式。 
如果日期字段是列表，那么日期将始终返回第一个日期。
 要访问所有日期，请使用 dates 而不是 date。


正则表达式( 在默认的情况下是关闭的)
默认情况下，正则表达式被禁用，因为它们规避了 Painless 对长时间运行和内存饥饿脚本的保护。 更糟糕的是，即使 Painless 的正则表达式也可以具有令人吃惊的性能和堆栈深度行为。 它们仍然是一个惊人的强大工具，但是在默认情况下太可怕了。 要使他们自己设置script.painless.regex.enabled：true 在elasticsearch.yml中。 我们非常希望有一个安全的替代实现，默认情况下可以启用，所以检查这个空间以备以后的开发！
Painless对正则表达式的本机支持具有语法结构：
     / pattern /：模式文字创建模式。 这是创造无痛模式的唯一途径。 `/`中的模式只是Java正则表达式。 有关更多信息，请参阅“模式标志”一节。
     =〜：find运算符返回一个布尔值，如果文本的子序列匹配则为true，否则为false。
       ==〜：匹配运算符返回一个布尔值，如果文本匹配则返回true，否则返回false。
 使用 find 操作符（=〜），您可以用“b”更新所有曲棍球选手：
POST hockey/player/_update_by_query
{
  "script": {
    "lang": "painless",
    "inline": "if (ctx._source.last =~ /b/) {ctx._source.last += \"matched\"} else {ctx.op = 'noop'}"
  }
}
使用匹配运算符（==〜），您可以更新名称以辅音开头的所有曲棍球运动员，并以元音结尾：
POST hockey/player/_update_by_query
{
  "script": {
    "lang": "painless",
    "inline": "if (ctx._source.last ==~ /[^aeiou].*[aeiou]/) {ctx._source.last += \"matched\"} else {ctx.op = 'noop'}"
  }
}
您可以直接使用 Pattern.matcher 获取 Matcher 实例，并删除其所有姓氏中的所有元音：
POST hockey/player/_update_by_query
{
  "script": {
    "lang": "painless",
    "inline": "ctx._source.last = /[aeiou]/.matcher(ctx._source.last).replaceAll('')"
  }
}
Matcher.replaceAll 只是调用 Java Matcher replaceAll 方法，所以它支持 $1 和 \1 替换：
POST hockey/player/_update_by_query
{
  "script": {
    "lang": "painless",
    "inline": "ctx._source.last = /n([aeiou])/.matcher(ctx._source.last).replaceAll('$1')"
  }
}
如果需要更多的替代控件，您可以使用构建替换的 Function<Matcher, String> 调用 CharAequence 上的 replaceAll 。 这不支持 $1 和 \1 访问替换，因为您已经有了匹配器的引用，可以使用 m.group(1) 获取它们。
在构建替换的函数内调用 Matcher.find 是粗鲁的，并且可能会破坏替换过程。
这将使曲棍球运动员姓氏中的所有元音大写:
POST hockey/player/_update_by_query
{
  "script": {
    "lang": "painless",
    "inline": "ctx._source.last = ctx._source.last.replaceAll(/[aeiou]/, m -> m.group().toUpperCase(Locale.ROOT))"
  }
}
或者您可以使用 CharSequence.replaceFirst 将其第一个元音以大写字母表示：
POST hockey/player/_update_by_query
{
  "script": {
    "lang": "painless",
    "inline": "ctx._source.last = ctx._source.last.replaceFirst(/[aeiou]/, m -> m.group().toUpperCase(Locale.ROOT))"
  }
}
注意：上面所有的 _update_by_query 示例可能真的可以用查询来限制他们拉回的数据。 
虽然您可以使用脚本查询，但它不会像使用任何其他查询一样有效，
因为脚本查询不能使用反向索引来限制他们必须检查的文档。
 painless 调度功能
Painless 使用接收器，名称和方法进行方法调度。例如，通过首先获取 s 的类，
然后用两个参数查找方法 foo 来解决 s.foo(a,b) 。
这与使用参数的运行时类型的Groovy和使用编译时类型的参数的Java不同。 
这样做的结果是，Painless 不支持像Java这样的重载方法，
当从Java标准库中将类列入白名单时，会导致一些麻烦。例如，在Java和Groovy中，Matcher有两种方法：group(int) 和 group(String)。Painless 无法将这两种方法列入白名单，因为它们具有相同的名称和相同数量的参数。因此，它具有 group(int) 和 namedGroup(String)。 
我们对这种不同的调度方法有几个理由：     
它使 def 类型的操作更简单，可能更快。使用接收器，
名称和数量意味着当 Painless 看到对def对象的调用时，它可以调度适当的方法，
而不必对参数类型进行昂贵的比较。对于使用def类型参数的调用也是如此。
 它保持一致。如果涉及到def类型的参数和Java，否则
  Painless 的行为就像Groovy一样真的很奇怪。它总是像Groovy一样慢一点。
它保持 Painless 可维护。添加Java或Groovy的方法调度感觉就像添加了一大堆复杂性，
使维护和其他改进更加困难。

```
