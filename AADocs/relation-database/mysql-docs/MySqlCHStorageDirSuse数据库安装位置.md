### 1，创建一个目录
例如： mkdir  /home/mysql_data
### 2，停止Mysql 服务
service mysql stop 
### 3, 先进行数据文件的备份，
cp  -rf  /var/lib/mysql /var/lib/mysql.bak
### 4，数据拷贝到/home/mysql_data
mv /var/lib/mysql/*  /home/mysql_data
### 5，改变/home/mysql_data 的用户和用户组，
chown -R mysql:mysql /home/mysql_data
### 6,如下：
cp /usr/share/mysql/my-medium.cnf　/etc/my.cnf
### 编辑my.cnf 
改变如下内容：（socket         = /home/mysql_data/mysql.sock）
```
# The following options will be passed to all MySQL clients
[client]
#password	= your_password
port		= 3306
#socket		= /var/lib/mysql/mysql.sock
socket         = /home/mysql_data/mysql.sock
# Here follows entries for some specific programs
# The MySQL server
[mysqld]
port		= 3306
#socket		= /var/lib/mysql/mysql.sock
socket          = /home/mysql_data/mysql.sock
```
### 7，改变/etc/init.d/mysql 中的datadir
```
如下:
basedir=
datadir=/home/mysql_data
```
### 8,如下两步紧紧作为参考，
```
chmod 700 /home/mysql_data/test
chown 600 /home/mysql_data/test/*
```
