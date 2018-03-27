package com.sydney.dream.thriftserver

import java.sql.DriverManager

import org.scalatest.FunSuite

class SparkSqlTest extends FunSuite{
    test("get spark thrift server "){
        Class.forName("org.apache.hive.jdbc.HiveDriver")
        val conn = DriverManager.getConnection("jdbc:hive2://s105:23040")
        println(conn)
        val statement = conn.createStatement
        val result = statement.executeQuery("select count(*) from person_table")
        while (result.next()){
            val count = result.getInt(1)
            println(count)
        }
        conn.close()
    }

    //差集
    test("Test difference") {
        val a = Set("a", "b", "a", "c")
        val b = Set("b", "d")
        assert(a -- b === Set("a", "c"))
    }

    //交集
    test("Test intersection") {
        val a = Set("a", "b", "a", "c")
        val b = Set("b", "d")
        assert(a.intersect(b) === Set("b"))
    }

    //并集
    test("Test union") {
        val a = Set("a", "b", "a", "c")
        val b = Set("b", "d")
        assert(a ++ b === Set("a", "b", "c", "d"))
    }
}