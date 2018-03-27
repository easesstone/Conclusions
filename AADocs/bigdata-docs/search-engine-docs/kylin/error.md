```
在安装好kylin之后我直接去访问web监控页面发现能够进去，也没有去看日志。
然后在运行官方带的例子去bulid cube时去发现状态一直是pending而不是runing。
这个时候才去查看日志：
2017-01-18 09:54:49,236 INFO  [localhost-startStop-1] web.DefaultSecurityFilterChain:28
 : Creating filter chain: org.springframework.security.web.util.AnyRequestMatcher@1, 
 [org.springframework.security.web.context.SecurityContextPersistenceFilter@4ffd3ea7,
  org.springframework.security.web.authentication.logout.LogoutFilter@7a22f758, 
  org.springframework.security.web.authentication.UsernamePasswordAuthenticationFi
  lter@386e0672, org.springframework.security.web.authentication.ui.DefaultLoginPag
  eGeneratingFilter@7fd0bb6d, org.springframework.security.web.authentication.www.Ba
  sicAuthenticationFilter@597eba36, org.springframework.security.web.savedrequest.Re
  questCacheAwareFilter@340b0e76, org.springframework.security.web.servletapi.Securi
  tyContextHolderAwareRequestFilter@7c1ec313, org.springframework.security.web.authe
  ntication.AnonymousAuthenticationFilter@6ef142e, org.springframework.security.web.
  session.SessionManagementFilter@2a6ca1cf, org.springframework.security.web.access.
  ExceptionTranslationFilter@4678724a, org.springframework.security.web.access.inter
  cept.FilterSecurityInterceptor@4cf437cd]
2017-01-18 09:54:49,712 ERROR [Thread-10] imps.CuratorFrameworkImpl:543 : 
Background exception was not retry-able or retry gave up
java.net.UnknownHostException: node1:2181: unknown error
        at java.net.Inet6AddressImpl.lookupAllHostAddr(Native Method)
        at java.net.InetAddress$2.lookupAllHostAddr(InetAddress.java:928)
        at java.net.InetAddress.getAddressesFromNameService(InetAddress.java:1323)
        at java.net.InetAddress.getAllByName0(InetAddress.java:1276)
        at java.net.InetAddress.getAllByName(InetAddress.java:1192)
        at java.net.InetAddress.getAllByName(InetAddress.java:1126)
        at org.apache.zookeeper.client.StaticHostProvider.<init>(StaticHostProvider.j
        ava:61)
        at org.apache.zookeeper.ZooKeeper.<init>(ZooKeeper.java:445)
        at org.apache.curator.utils.DefaultZookeeperFactory.newZooKeeper(DefaultZoo
        keeperFactory.java:29)
        at org.apache.curator.framework.imps.CuratorFrameworkImpl$2.newZooKeeper(Cur
        atorFrameworkImpl.java:154)
        at org.apache.curator.HandleHolder$1.getZooKeeper(HandleHolder.java:94)
        at org.apache.curator.HandleHolder.getZooKeeper(HandleHolder.java:55)
        at org.apache.curator.ConnectionState.reset(ConnectionState.java:219)
        at org.apache.curator.ConnectionState.start(ConnectionState.java:103)
        at org.apache.curator.CuratorZookeeperClient.start(CuratorZookeeperClient.j
        ava:190)
        at org.apache.curator.framework.imps.CuratorFrameworkImpl.start(CuratorFrameworkImpl.java:256)
        at org.apache.kylin.storage.hbase.util.ZookeeperJobLock.lock(ZookeeperJobLock.java:67)
        at org.apache.kylin.job.impl.threadpool.DefaultScheduler.init(DefaultScheduler.java:202)
        at org.apache.kylin.rest.controller.JobController$1.run(JobController.java:89)
        at java.lang.Thread.run(Thread.java:745)
2017-01-18 09:55:04,739 ERROR [Thread-10] curator.ConnectionState:201 : 
Connection timed out for connection string (node1:2181:2181,node2:2181:2181,
node3:2181:2181) and timeout (15000) / elapsed (15702)
org.apache.curator.CuratorConnectionLossException: KeeperErrorCode = ConnectionLoss
        at org.apache.curator.ConnectionState.checkTimeouts(ConnectionState.java:198)
        at org.apache.curator.ConnectionState.getZooKeeper(ConnectionState.java:88)
        at org.apache.curator.CuratorZookeeperClient.getZooKeeper(
        CuratorZookeeperClient.java:115)
</init></init>
 在红色标记的地方很明显发现端口号2181在zk connectString里写了两遍，
 查看org.apache.kylin.storage.hbase.util.ZookeeperJobLock.lock的源码
 (ZookeeperJobLock.java:90)可知，
读取的是配置文件hbase-site.xml的hbase.zookeeper.quorum，该项只需配置Host
不需要配置端口号Port，改回来之后重启hbase就好了.
```