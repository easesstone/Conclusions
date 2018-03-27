# Hive笔记
## 概述
运行于HDFS 上的数据仓库  
操作结构化的数据  
用类似Mysql 的方式操作数据  
## 架构
用户接口（包括WebUI, Hive命令行，）  
元存储，metastore  
HiveQL处理引擎  
执行引擎  
HDFS或者HBase。
## 工作流程
hive 与Haddop 交互  
1，客户端发送HQL Query到Dirver    
2，Driver 把请求发送到Compiler 进行编译。  
3，Compiler 向Metastore 请求元数据。  
4，Metastore 向Compiler 返回元数据。  
5，Driver 端获取Compiler 的查询分析和编译。  
6，发送数据到执行引擎。  
7，请求经由执行引擎发送到HDFS上执行Mapreduce的同时，请求操作Metastore。  
8，HDFS 返回执行结果给执行引擎。  
9，执行引擎把数据发给driver 端。  
10，结果由driver 端返回给客户端。  

## 安装
伪分布式的HDFS 上安装。
### 1，安装Mysql
如果只是实验用，只需要简易地安装MYsql  
mysql 在线安装：yum install mysql-server  
启动服务： service mysqld start  
安装jdbc 连接：yum install mysql-connector-java  
连接到hive类路径：ls -s /usr/share/java/mysql-connector-java.jar /usr/lib/hive/lib/mysql-connector-java.jar  
配置密码：/usr/bin/mysql_secure_installation  
设置mysql 自启动：chkconfig mysqld on  
检查是否设置成功： chkconfig --list mysqld  
mysqld          0:off   1:off   2:on    3:on    4:on    5:on    6:off  
### 2，下载Hive tar 包，配置Hive（单节点配置）
#### I，在hdfs上创建目录：
hdfs dfs -mkdir -p /usr/hive/warehouse  
hdfs dfs -mkdir -p /usr/hive/tmp  
hdfs dfs -mkdir -p /usr/hive/log  
hdfs dfs -chmod 777 /usr/hive/warehouse  
hdfs dfs -chmod 777 /usr/hive/tmp  
hdfs dfs -chmod 777 /usr/hive/log  
#### II，配置Conf目录下的hive-env.sh
export HADOOP_HOME=/usr/lib/hadoop  
export HIVE_CONF_DIR=/usr/lib/hive/conf  
#### III,配置日记文件路径
mkdir $HIVE_HOME/logs  
配置hive-log4j.properties
#### III,配置hive-site.xml
服务端配置：  
```
  <property>
    <name>javax.jdo.option.ConnectionURL</name>
    <value>jdbc:mysql://s118:3306/metastore?createDatabaseIfNotExist=true</value>
    <description>
      JDBC connect string for a JDBC metastore.
      To use SSL to encrypt/authenticate the connection, provide 
      database-specific SSL flag in the connection URL.
      For example, jdbc:postgresql://myhost/db?ssl=true for postgres database.
    </description>
  </property>

  <property>
    <name>javax.jdo.option.ConnectionDriverName</name>
    <value>com.mysql.jdbc.Driver</value>
    <description>Driver class name for a JDBC metastore</description>
  </property>

  <property>
    <name>javax.jdo.option.ConnectionUserName</name>
    <value>root</value>
    <description>Username to use against metastore database</description>
  </property>

  <property>
    <name>javax.jdo.option.ConnectionPassword</name>
    <value>root</value>
    <description>password to use against metastore database</description>
  </property>

  <property>
    <name>hive.metastore.warehouse.dir</name>
    <value>/user/hive/warehouse</value>
    <description>location of default database for the warehouse</description>
  </property>


  <property>
    <name>hive.exec.scratchdir</name>
    <value>/user/hive/tmp</value>
    <description>HDFS root scratch dir for Hive jobs which gets 
    created with write all (733) permission. For each connecting user, 
    an HDFS scratch dir: ${hive.exec.scratchdir}/&lt;username&gt; is created,
     with ${hive.scratch.dir.permission}.</description>
  </property>

  <property>
    <name>hive.querylog.location</name>
    <value>/user/hive/log</value>
    <description>Location of Hive run time structured log file</description>
  </property>
</property>

<property>
    <name>hive.exec.local.scratchdir</name>
    <value>/tmp/hive/local</value>
    <description>Local scratch space for Hive jobs</description>
  </property>

  <property>
    <name>hive.downloaded.resources.dir</name>
    <value>/tmp/hive/resources</value>
    <description>Temporary local directory for added resources 
    in the remote file system.</description>
  </property>
```
客户端配置：
```
  <property>
    <name>hive.metastore.uris</name>
    <value>thrift://s118:9083</value>
    <description>Thrift URI for the remote metastore. 
    Used by metastore client to connect to remote metastore.</description>
  </property>
```
## 启动Hive
1,首先确保启动了Mysql  
2,启动Hive Metastore  
cd $HIVE_HOME/bin  
./schematool --dbType mysql --initSchema  
nohup hive --service metastore &  



