package com.sydney.dream.classdefine

import util.control.Breaks._
// 首先顶一个Movie 类来实现数据的封装
class Movie(var name : String,var action : Int,var  kiss : Int,var typeMovie : Int ) {
    def this(name : String, action : Int, kiss : Int) {
        this(name, action, kiss, 0)
    }
    override def toString = s"movieName: ${name}, actinNum: ${action}， kissNum: ${kiss}, typeMovie: ${typeMovie}"
}
// 数据模型好了，那当然先再来一个类似java main 的东东了，no bb ，just do it
object Movie {
    def main(args : Array[String]): Unit = {
        // 准备数据
        val movies = Array(new Movie("两小无猜", 1, 100, 0), new Movie("泰坦尼克号",  20, 100, 0), new Movie("初恋那件小事", 0, 500, 0), 
                           new Movie("功夫足球", 100, 3, 1), new Movie("神棍",  300,  1,  1), new Movie("逃学威龙", 200, 3, 1))
        // 计算new Movie("test", 50, 50) 到各个已知movie 的距离
        val distances = getDistances(new Movie("test", 50, 50), movies)
        println("排序前的数据")
        for((k, v) <- distances) println(k, v)
        // 对计算出来的距离进行排序
        val sortedDistances = distances.toSeq.sortWith(_._2>_._2) 
        println
        println("排序后的数据：")
        for((k, v) <- sortedDistances) println(k, v)   
        // 返回分类标签
        
     
    }
    // 返回分类标签
    def getClassLable(movies : Array[Movie], sortedDistances : Map[Int, Double], k : Int) : String = {
        val tmp : Map[Int, Int] = Map()
        for((k, v) <- sortedDistances) {
            if (count >= k - 1) { 
            } else {
               tmp +=  (movies.get(k).typeMovie -> (tmp.getOrElse(movies.get(k).typeMovie, 0) + 1))   
            }
            count = count + 1
        }
        
        null        
    }
    // 获取距离列表
    def getDistances(movie : Movie, movies : Array[Movie]) : Map[Int, Double] = {
        var distances : Map[Int, Double] = Map()
        for (i <- 0 until movies.length) {
            val distance2 = Math.pow((movies(i).action - movie.action), 2) + Math.pow((movies(i).kiss - movie.kiss), 2)
            val distance = Math.sqrt(distance2)
            distances += (i -> distance)
        }
        distances
    }
}
