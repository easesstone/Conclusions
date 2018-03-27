## ceph Pool
相当于namespaace 的作用，是ceph 存储集群的逻辑分区。
每个pool包含一定数量的PG，PG里的对象被映射到不同的OSD上，
因此pool是分布到整个集群的。