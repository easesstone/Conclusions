## 第一：初始化的时候。
```
object Sample {
   var name:String=_
   def main (args: Array[String]){
      name="hello world"
     println(name)
   }
```
在这里，name也可以声明为null，例：var name:String=null。这里的下划线和null的作用是一样的。
## 第二：引入的时候。
```
import math._
object Sample {
   def main (args: Array[String]){
    println(BigInt(123))
   }
}
```
这里的math._就相当于Java中的math.*; 即“引用包中的所有内容”。
## 第三：集合中使用。（最典型，最常用）
```
object Sample {
   def main (args: Array[String]){
    val newArry= (1 to 10).map(_*2)
   println(newArry)
   }
}
```

这里的下划线代表了集合中的“某（this）”一个元素。这个用法很常见，在foreach等语句中也可以使用。
## 第四：模式匹配。
```
object Sample {
   def main (args: Array[String]){
     val value="a"
     val result=  value match{
       case "a" => 1
       case "b" => 2
       case _ =>"result"
     }
     println(result)
   }
}
```
在这里的下划线相当于“others”的意思，就像Java  switch语句中的“default”。  
还有一种写法，是被Some“包”起来的，说明Some里面是有值的，而不是None。  
```
object Sample {
  def main (args: Array[String]){
    val value=Some("a")
    val result=  value match{
      case Some(_) => 1
      case _ =>"result"
    }
    println(result)
  }
```
还有一种表示队列
```
object Sample {
  def main (args: Array[String]){
    val value=1 to 5
    val result=  value match{
      case Seq(_,_*) => 1
      case _ =>"result"
    }
    println(result)
  }
}
```
## 第五：函数中使用。
```
object Sample {
   def main (args: Array[String]){
    val set=setFunction(3.0,_:Double)
     println(set(7.1))
   }
  def setFunction(parm1:Double,parm2:Double): Double = parm1+parm2
}
```
这是Scala特有的“偏函数”用法。
## 第六：元组Tuple。（如果这也算是的话）
```
object Sample {
   def main (args: Array[String])={
     val value=(1,2)
     print(value._1)
   }
}

```
## 第七：传参。
```
object Sample {
   def main (args: Array[String])={
    val result=sum(1 to 5:_*)
     println(result)
   }
  def sum(parms:Int*)={
    var result=0
    for(parm <- parms)result+=parm
    result
  }
}
```
当函数接收的参数不定长的时候，假如你想输入一个队列，可以在一个队列后加入“:_*”，
因此，这里的“1 to 5”也可以改写为：“Seq(1,2,3,4,5)”。这算是一个小的用法吧