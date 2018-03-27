## 关于日记
[Blog01](http://blog.csdn.net/hero9881010love/article/details/43154627)  
[Blog02]()
```
日志是研究代码的第一步。
ceph的日志默认输入到/var/log/ceph目录下，可以进入到该目录下，查找相应信息。
总共有20个等级的日志输出，从1~20。
例如我要调整osd.0的日志输出等级为15/15，使用下面的命令设置输出等级
#ceph tell osd.0 injectargs --debug-osd 15/15

或者
#ceph daemon /var/run/ceph/ceph-osd.0.asok congfig set debug_osd 15/15
这个命令要在osd.0所在主机上才能生效。
两个命令功能都是一样的，个人比较习惯使用第二个命令。
#ceph daemon /var/run/ceph/ceph-osd.0.asok help
通过这个，能看到更多功能，包括读写操作在某些流程中，总共延时，平均延时，osd使用情况等。
15/15的设定值，左边的15代表打印到log文件中的日志等级，右边的15在一般情况下无用，
它设定诸如assert等原因引起的程序崩溃后，记录程序内存信息等级，可以称为in-memory log。

另外ceph的日志被划分成很多块，举个例子说。
我想要通过日志了解MON的线程运行情况，但调整debug_mon 为20/20后，打印出来的日志太多了
，很多信息是我不想要的。那么就可以不改变debug_mon ，而是改变debug_tp为20/20（tp即threadpool）。

通过
#ceph daemon /var/run/ceph/ceph-osd.0.asok congfig show | grep debug
来查看全部的日志等级。
ceph源码中，比如下面
ldout(cct, 1) << "tear_down_cache forcing close of dir " << dendl;
ldout是一个宏，括弧中的1为日志等级，当你设定日志输出为10/5, 因为10 > 1 , 
所以它就会输出到日志文件中。

cct是一个全局对象，用了单例模型，它记录了进程诸多信息，比如配置文件中的信息。
debug_osd,debug_mon等等，这些都是配置文件中的配置参数。
```

## 第一次建立连接时间会稍微长，以及第一次操作集群中的内容，时间会比较长？？？？

## CEPH 查看所有的pool
```
data@s157:/home/demo$ rados lspools
rbd
.rgw.root
default.rgw.control
default.rgw.meta
default.rgw.log
default.rgw.buckets.index
default.rgw.buckets.data
data@s157:/home/demo$ ceph osd lspools
1 rbd,2 .rgw.root,3 default.rgw.control,4 default.rgw.meta,5 default.rgw.log,6 default.rgw.buckets.index,7 default.rgw.buckets.data,
data@s157:/home/demo$ ceph osd dump |grep pool
pool 1 'rbd' replicated size 2 min_size 1 crush_rule 0 object_hash rjenkins pg_num 128 pgp_num 128 last_change 17 flags hashpspool stripe_width 0 application rbd
pool 2 '.rgw.root' replicated size 2 min_size 1 crush_rule 0 object_hash rjenkins pg_num 8 pgp_num 8 last_change 19 flags hashpspool stripe_width 0 application rgw
pool 3 'default.rgw.control' replicated size 2 min_size 1 crush_rule 0 object_hash rjenkins pg_num 8 pgp_num 8 last_change 21 flags hashpspool stripe_width 0 application rgw
pool 4 'default.rgw.meta' replicated size 2 min_size 1 crush_rule 0 object_hash rjenkins pg_num 8 pgp_num 8 last_change 23 flags hashpspool stripe_width 0 application rgw
pool 5 'default.rgw.log' replicated size 2 min_size 1 crush_rule 0 object_hash rjenkins pg_num 8 pgp_num 8 last_change 25 flags hashpspool stripe_width 0 application rgw
pool 6 'default.rgw.buckets.index' replicated size 2 min_size 1 crush_rule 0 object_hash rjenkins pg_num 8 pgp_num 8 last_change 28 flags hashpspool stripe_width 0 application rgw
pool 7 'default.rgw.buckets.data' replicated size 2 min_size 1 crush_rule 0 object_hash rjenkins pg_num 8 pgp_num 8 last_change 34 flags hashpspool stripe_width 0 application rgw
data@s157:/home/demo$ 



```
[博客地址](https://www.cnblogs.com/netmouser/p/6878885.html)


## too many PGs per OSD
[参考地址](http://www.linuxidc.com/Linux/2017-04/142518.htm)