package com.sydney.dream.scalademo

object Main {
    def main(args: Array[String]): Unit = {
//        Test.printString
//
//        //伴生对象是单例的
//        val a = Test.a
//        val a1 = Test.a
//        println("a eq al：" + (a eq a1))
//
//        // 伴生类不是单例的
//        val test01 = new Test
//        val test02 = new Test
//        println("test01 eq test02: " + (test01 eq test02))
        val a = Array(10, 20, 30, 40)
        val b = a.lastOption
        println(b)        //Some(40)
        b match {
            case Some(value) => print(value)    // 40
            case None => println(None)
        }
    }
}
