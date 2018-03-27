# case class, 一种和普通类有些区别的类
## 1，初始化的时候可以不用new
当然，你也可以加上new，但是普通类是一定要new 的。
实际上是调用了伴生对象中的apply 方法。
## 2，实现了toString方法
## 3，默认实现了equals 和 hashCode
## 4，实现了Serializable,即其是可序列化的
## 5，构造函数的参数是pulic 级别的，可以直接访问。
## 6，从scala.Product 中集成了一些函数。？？？
## 7，case class 最重要的特性是模式匹配。？？？
