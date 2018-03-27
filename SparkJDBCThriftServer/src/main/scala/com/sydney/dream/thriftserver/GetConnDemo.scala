package com.sydney.dream.thriftserver

import java.sql.DriverManager

object GetConnDemo {
    def main(args: Array[String]): Unit = {
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
}
