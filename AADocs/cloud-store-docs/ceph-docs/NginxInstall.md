## 1，获取Nginx相关安装包
```
wget http://nginx.org/download/nginx-1.10.2.tar.gz
wget http://www.openssl.org/source/openssl-fips-2.0.10.tar.gz
wget http://zlib.net/zlib-1.2.11.tar.gz
wget ftp://ftp.csx.cam.ac.uk/pub/software/programming/pcre/pcre-8.40.tar.gz
```
## 2，安装相关C++ 编译环境
apt-get install g++

## 3，安装相关软件
### I，openssl-fips-2.0.10.tar.gz
解压，然后到代码目录下进行如下命令进行安装。  
./config && make && make install
### II，pcre-8.40.tar.gz
解压，到源码目录下进行如下命令进行安装。  
./configure && make && make install
### III，zlib-1.2.11.tar.gz
同II
### IV， nginx-1.10.2.tar.gz
同II

##4，Nginx 启动和验证。
```
root@s110:/opt/tool/nginx-1.10.2# whereis nginx
nginx: /usr/local/nginx
root@s110:/opt/tool/nginx-1.10.2# cd /usr/local/nginx/
root@s110:/usr/local/nginx# ls
conf  html  logs  sbin
root@s110:/usr/local/nginx# cd  -
/opt/tool/nginx-1.10.2
root@s110:/opt/tool/nginx-1.10.2# /usr/local/nginx/sbin/nginx 
root@s110:/opt/tool/nginx-1.10.2# echo $?
0
root@s110:/opt/tool/nginx-1.10.2# ps -ef | grep ngi
root     32155     1  0 09:05 ?        00:00:00 nginx: master process /usr/local/nginx/sbin/nginx
nobody   32156 32155  0 09:05 ?        00:00:00 nginx: worker process      
root     32158 21590  0 09:06 pts/12   00:00:00 grep --color=auto ngi
```

## 5，Ngix 相关命令
```
[root@localhost ~]# /usr/local/nginx/sbin/nginx     启动
[root@localhost ~]# /usr/local/nginx/sbin/nginx -s stop(quit、reload)  停止/重启
[root@localhost ~]# /usr/local/nginx/sbin/nginx -h  命令帮助
[root@localhost ~]# /usr/local/nginx/sbin/nginx -t  验证配置文件
[root@localhost ~]# vim /usr/local/nginx/conf/nginx.conf  配置文件
```

