CPU 型号，个数，每个cpu的物理核数，总共的逻辑核数（可用的总线程数）
```
[root@s106 TestUtil]# cat /proc/cpuinfo | grep name | cut -f2 -d: | uniq -c   ## CPU 型号
     48  Intel(R) Xeon(R) CPU E5-2650 v4 @ 2.20GHz
[root@s106 TestUtil]# cat /proc/cpuinfo| grep "physical id"| sort| uniq| wc -l   ## 物理cpu 个数
2
[root@s106 TestUtil]# cat /proc/cpuinfo| grep "cpu cores"| uniq                ## 每个cpu 的物理核数
cpu cores	: 12
[root@s106 TestUtil]# cat /proc/cpuinfo| grep "processor"| wc -l              ## 总共的逻辑核数，即可以使用的最大线程数
48
```
