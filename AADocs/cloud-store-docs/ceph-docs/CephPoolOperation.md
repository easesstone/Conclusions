## Pool Operation
### 查看pool
```
rados lspools
ceph osd lspools
ceph osd dump | grep pool 
```
### 创建pool
```
通常在创建pool之前，需要覆盖默认的pg_num，官方推荐：

若少于5个OSD， 设置pg_num为128。
5~10个OSD，设置pg_num为512。
10~50个OSD，设置pg_num为4096。
超过50个OSD，可以参考pgcalc计算。

ceph ods pool create pool-test 64

```
[pg数量计算](http://ceph.com/pgcalc/)
### 调整pool 副本
```
ceph osd pool set pool1 size 2
ceph osd pool set pool01 pg_num 128
ceph osd pool set pool01 pgp_num 128
```

### pool 状态
rados df

### 重命名pool
ceph osd pool rename pool1 pool2

### 删除POOL
ceph osd pool delete pool1

### 设置POOL配额
ceph osd pool set-quota pool1 max_objects 100  #最大100个对象
ceph osd pool set-quota pool1 max_bytes $((10 * 1024 * 1024 * 1024))    #容量大小最大为10G

### 快照创建
```
ceph支持对整个pool创建快照（和Openstack Cinder一致性组区别？），
作用于这个pool的所有对象。但注意ceph有两种pool模式：

Pool Snapshot，我们即将使用的模式。创建一个新的pool时，默认也是这种模式。
Self Managed Snapsoht，用户管理的snapshot，这个用户指的是librbd，也就是说，
如果在pool创建了rbd实例就自动转化为这种模式。
这两种模式是相互排斥，只能使用其中一个。因此，如果pool中曾经创建了rbd对象
（即使当前删除了所有的image实例）就不能再对这个pool做快照了。反之，
如果对一个pool做了快照，就不能创建rbd image了。

[root@mon1 ~]# ceph osd pool mksnap pool2 pool2_snap
created pool pool2 snap pool2_snap
[root@mon1 ~]#
```

### 删除快照
```
[root@mon1 ~]# ceph osd pool rmsnap    #remove snapshot <snap> from <pool>
[root@mon1 ~]# ceph osd pool rmsnap pool2 pool2_snap
removed pool pool2 snap pool2_snap
[root@mon1 ~]#
```


