
#### 1，在SUSE Linux Enterprise Server 11 SP3  (x86_64) 这个中安装MySql 的方法：
#### 2，在MySql 官网找到如下安装包mysql-server; mysql-client; mysql-devel;mysql-shared-advanced  四个Rpm 包。
#### 3，删除机器上的没有删除的mysql 安装项，
```
用  rpm -qa | grep -i mysql  ，比如查找到如下两个内容，
libmysqlclient_r15-5.0.96-0.6.1
libqt4-sql-mysql-4.6.3-5.25.4，然后用 rpm -e   libmysqlclient_r15-5.0.96-0.6.1  进行删除内容，出现错误，可能是因为其依赖其他的项，
那就先删除其他的依赖的项， 比如会依赖libqt4-sql-mysql-4.6.3-5.25.4 ，那就先删除rpm -e libqt4-sql-mysql-4.6.3-5.25.4 ，然后删除，
libmysqlclient_r15-5.0.96-0.6.1
```
4，安装 mysql 的四个rpm 安装包。
```
进入到相应目录：目录下的文件：MySQL-client-advanced-5.5.18-1.sles11.x86_64.rpm  MySQL-server-advanced-5.5.18-1.sles11.x86_64.rpm，
MySQL-devel-advanced-5.5.18-1.sles11.x86_64.rpm   MySQL-shared-advanced-5.5.18-1.sles11.x86_64.rpm
例如：rpm -ivh MySQL-*   执行结果如下所示：
Preparing...                ########################################### [100%]
   1:MySQL-shared-advanced  ########################################### [ 25%]
   2:MySQL-client-advanced  ########################################### [ 50%]
   3:MySQL-devel-advanced   ########################################### [ 75%]
   4:MySQL-server-advanced  ########################################### [100%]
insserv: script acc.sh is broken: incomplete LSB comment.
insserv: missing valid name for `Provides:' please add.
insserv: script acc.sh is broken: incomplete LSB comment.
insserv: missing valid name for `Provides:' please add.
insserv: script acc.sh is broken: incomplete LSB comment.
insserv: missing valid name for `Provides:' please add.
insserv: script acc.sh is broken: incomplete LSB comment.
insserv: missing valid name for `Provides:' please add.
insserv: script acc.sh is broken: incomplete LSB comment.
insserv: missing valid name for `Provides:' please add.
insserv: script acc.sh is broken: incomplete LSB comment.
insserv: missing valid name for `Provides:' please add.
insserv: script acc.sh is broken: incomplete LSB comment.
insserv: missing valid name for `Provides:' please add.
insserv: script acc.sh is broken: incomplete LSB comment.
insserv: missing valid name for `Provides:' please add.
insserv: script acc.sh is broken: incomplete LSB comment.
insserv: missing valid name for `Provides:' please add.
insserv: script HCWatcher: service testwrapper already provided!
insserv: Service network is missed in the runlevels 4 to use service testwrapper
insserv: Service syslog is missed in the runlevels 4 to use service testwrapper
mysql                     0:off  1:off  2:on   3:on   4:on   5:on   6:off

PLEASE REMEMBER TO SET A PASSWORD FOR THE MySQL root USER !
To do so, start the server, then issue the following commands:

/usr/bin/mysqladmin -u root password 'new-password'
/usr/bin/mysqladmin -u root -h SHA1000046667 password 'new-password'

Alternatively you can run:
/usr/bin/mysql_secure_installation

which will also give you the option of removing the test
databases and anonymous user created by default.  This is
strongly recommended for production servers.

See the manual for more instructions.

Please report any problems with the /usr/bin/mysqlbug script!
```
#### 6，启动mysql 服务：service mysql start
Starting MySQL..                                                                                                 done

#### 7，测试mysql 是否启动成功。
```
直接键入命令 mysql .
(注)如果安装过程中出现以下现象， Usge  mysql [start|stop****]说明环境变量没有设置，才会出现系统自己寻找路径导致错误。
需要将需要用到的mysql路径(如果是使用rpm安装，则默认在/usr/bin)设置进/etc/profile的path里面，
```

#### 8,修改密码，
```
一个办法：mysqladmin -u root -h 100.106.43.30 password "huawei"， 另一个办法，mysql 进入交互模式。然后，use user;
update user set password=password('huawei')  where user="root" and host="SHA1000046667"
```
#### 9,用户管理：
insert into user(Host,User,Password) values("SHA1000046667","easy",password("easy"));刷新系统权限表：flush privileges;
#### 10,用户删除，
delete from user where user="easy" and host="SHA1000046667"

#### 11, 为用户授权。test 是数据库，,允许远程登录。
```
修改/etc/mysql/mysql.conf.d/mysqld.cnf，注释掉bind-address	= 127.0.0.1
GRANT ALL PRIVILEGES ON *.* TO root@"%" IDENTIFIED BY "root";
grant all privileges on  test.*  to easy@SHA1000046667  identified by "easy";
刷新系统权限表：SHA1000046667 这的是可以链接到服务器的机器ip. 
identified by "***" 这个表示认证的密码，
root@ 表示用root 用户登录，后面@ 的内容指的是ip.
flush privileges;
重启mysql 服务
```

#### 12，重启mysql 服务。/etc/init.d/mysql stop/start
```
/etc/inint.d/mysql start/stop
一、启动方式
1、使用 service 启动：service mysqld start
2、使用 mysqld 脚本启动：/etc/inint.d/mysqld start
3、使用 safe_mysqld 启动：safe_mysqld&
二、停止
1、使用 service 启动：service mysqld stop
2、使用 mysqld 脚本启动：/etc/inint.d/mysqld stop
3、mysqladmin shutdown
三、重启
1、使用 service 启动：service mysqld restart
2、使用 mysqld 脚本启动：/etc/inint.d/mysqld restart 四，启动过程中，出现如下错误：
ERROR 2002 (HY000): Can't connect to local MySQL server through socket '/var/lib/mysql/mysql.sock' (2)
则用 service mysql start 
```
#### 13，netstat -ntpl ；查看，mysql 所占用的端口。
#### 14 ，mysql 安装之后的目录结构，如下：/var/lib/mysql/  数据库的数据目录：    /usr/share/mysql；数据库配置目录：       /usr/bin：mysql 命令：
#### 15 ，卸载  rpm -qa|grep -i mysql  ，rpm -e *****.
#### 16,利用 listen lsof  -i:3306 监听3306 端口绑定的程序。
