```
reduce(binary_function) 
reduce将RDD中元素前两个传给输入函数，产生一个新的return值，新产生的return值与RDD中下一个元素（第三个元素）组成两个元素，再被传给输入函数，直到最后只有一个值为止。

val c = sc.parallelize(1 to 10)
c.reduce((x, y) => x + y)//结果55
1
2
具体过程，RDD有1 2 3 4 5 6 7 8 9 10个元素， 
1+2=3 
3+3=6 
6+4=10 
10+5=15 
15+6=21 
21+7=28 
28+8=36 
36+9=45 
45+10=55

reduceByKey(binary_function) 
reduceByKey就是对元素为KV对的RDD中Key相同的元素的Value进行binary_function的reduce操作，因此，Key相同的多个元素的值被reduce为一个值，然后与原RDD中的Key组成一个新的KV对。

val a = sc.parallelize(List((1,2),(1,3),(3,4),(3,6)))
a.reduceByKey((x,y) => x + y).collect
1
2
//结果 Array((1,5), (3,10))
```