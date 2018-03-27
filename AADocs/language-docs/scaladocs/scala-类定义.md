# 类定义
## 基本概念
最基本的模式：
```
class ChecksumAccumulator {
    // 此处为类定义，即类的属性和方法。或者称为类的成员。
}
```
例子如下：
```
class Person(fn : String, ln : String, s: Person) {
    val lastName = ln;  // 没有private修饰符，则认为是public
    val firstName = fn;  // 从构造函数的参数类型，推断firstName 为String 类型。
    val spouse = s;  // 从构造函数推断为参数类型为，Person，
    def this(fn : String, ln : String) = {this(fn, ln, null)}
    def introduction() : String =
        return "我的名字是，" + lastName + " " + firstName +
            (if(spouse != null) "，他的名字是，" + spouse.lastName + spouse.firstName + "。 " else "。")
}
```
scala 中，实例属性默认是public，实现的属性和方法可以直接访问。  
例如 steveLi.firstName 不发生歧义的时候，"."可以换成空格
```
以上的Person 可以简化为大致如下：
class Person02(var firstName : String, val lastName : String, var spouse : Person){
    def this(fn: String, ln: String) = this(fn, ln, null)
    def introduction() : String = "我的名字是，" + lastName + " " + firstName +
        (if(spouse != null) "，他的名字是，" + spouse.lastName + spouse.firstName + "。 " else "。")
}

注意val 和var 的区别，val 数据赋值后不可以改变，var 赋值后可以改变。
```


## 继承关系
基类
```

```




## Trait 特质





## 单例对象，伴生类，伴生对象。



## 类抽象