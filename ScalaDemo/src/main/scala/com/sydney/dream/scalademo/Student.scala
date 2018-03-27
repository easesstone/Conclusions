package com.sydney.dream.scalademo

/**
  * 伴生类和伴生对象
  * class Student 是伴生对象object Student 的伴生类
  * object Student 是伴生类class Student 的伴生对象
  */
class Student(var name:String, var adress:String) {
    private var phone = "110";
    // 直接访问伴生对象中的私有成员：
    def infoCompObj() = println("伴生类中访问伴生对象： " + Student.sno)
}
object Student{
    private var sno:Int = 10;
    def incremenSno() = {
        sno += 1
        sno
    }
    // 定义apply 方法，实例化伴生类：
    def apply(name1:String,address1:String)= new Student(name1,address1)

    def main(args: Array[String]): Unit = {
        println("单例对象：" + Student.incremenSno())
        //实例化伴生类
        val obj = new Student("yy", "bj");
        obj.infoCompObj()

        // 实例化伴生类，实际上是通过apply 方法进行实例化
        val obj2 = Student("yy_apply", "bj_apply")
        println(obj2.name + " " + obj2.adress)
    }
}
