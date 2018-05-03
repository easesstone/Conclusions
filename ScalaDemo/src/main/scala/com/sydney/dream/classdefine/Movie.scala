package com.sydney.dream.classdefine
// 首先顶一个Movie 类来实现数据的封装
class Movie(var name : String,var action : Int,var  kiss : Int,var typeMovie : Int ) {
    override def toString = s"movieName: ${name}, actinNum: ${action}， kissNum: ${kiss}, typeMovie: ${typeMovie}"
}
// 数据模型好了，那当然先再来一个类似java main 的东东了，no bb ，just do it
object Movie {
    def main(args : Array[String]): Unit = {
        val movies = Array(new Movie("两小无猜", 1, 100, 0), new Movie("泰坦尼克号",  20, 100, 0), new Movie("初恋那件小事", 0, 500, 0), 
                           new Movie("功夫足球", 100, 3, 1), new Movie("神棍",  300,  1,  1), new Movie("逃学威龙", 200, 3, 1))
        val distances = getDistances(new Movie("test", 50, 50, 0), movies) 
                
    }
    // 获取距离列表
    def getDistances(movie : Movie, movies : Array[Movie]) : Array[Double] = {
        var distances = new Array[Double](movies.length)
        for (i <- 0 until movies.length) {
            val distance2 = Math.pow((movies(i).action - movie.action), 2) + Math.pow((movies(i).kiss - movie.kiss), 2)
            val distance = Math.sqrt(distance2)
            distances(i)=distance
        }
        distances
    }
}
