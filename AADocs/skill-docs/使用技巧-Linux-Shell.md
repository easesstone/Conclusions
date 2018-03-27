### 1，根据进程号查找端口号《--》根据端口号查看进程号《--》根据进程号查看进程名
```
我们知道， 根据ps -aux | grep xxx就是很快实现进程名和进程号的互查， 
所以我们只说进程号pid就行。 如下示例中， 进程pid常驻。
1.  根据进程pid查端口：
     lsof -i | grep pid
2.  根据端口port查进程（某次面试还考过）：
    lsof  -i:port     
3. 根据进程pid查端口：
   netstat -nap | grep pid
4.  根据端口port查进程
   netstat -nap | grep port
```

### 2, grep 匹配多个值
```
xcall jps | grep -E 'NameNode|NodeManager|DataNode|ResourceManager|
JournalNode|DFSZKFailoverController|jps show as bellow'
```

### 3，awk 打印每一个值
```
for host in $(echo "hello,niya" | awk -F ","  '{for(i=1;i<=NF;++i) print $i}');do
  echo $host
done
```

### grep查找空行
```
grep ^$ logs.log
```
### grep 匹配中的点".", 匹配除"\n" 外的任意1个字符
```
[root@s100 hadoop]# grep 'yarn.nodemanager.resource.cpu-vcores' yarn-site.xml.bak  | grep "."
        <name>yarn.nodemanager.resource.cpu-vcores</name>
        <value>yarnhnodemanagereresourceecpu-vcores</value>
```

### lINUX 行数统计
wc -l

### Linux 打印空行的行号
grep -n ^$ yarn-site.xml  | awk -F ":" '{print $1}'

### 查找以abc结尾的行
grep abc$ fileName

### linux 多条件匹配
技巧:  
先把符合条件的情况列举出来，  
然后，比如有四个添加符合情况，a1,a2,a3,a4  
则其反面是  
!(a1 || a2 || a3 || a4)  

此条件下用于参数验证  
```
#showRunTime
set -x
tmp=$(echo “$2” | grep ^[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]$) 
if [[ !(($# == 1 && $1 == mid_table) || ($# == 2 && $1 == person_table && $2 == now) || \
($# == 2 && $1 == person_table && $2 == before) || ($# == 2 && $1 == person_table && $2 == now)) ]];then
    #  showUsage
    echo "caonima"
fi
set +x
```

### Linux bc 数据计算工具的使用
DEFAULT_EXECUTOR=$(echo "(${Tatal_CORES}-${DRIVER_CORES})/${EXECUTOR_CORES}" | bc)

### 变量统一

### Linux 注释多行
:<<EOF  
echo 1  
echo 2  
echo 2  
EOF  

### ② linux 中这个该怎么打，搜狗输入法的数字符号

### ssh -l root hostnameOrIp
-l 指定登录的用户名.

### 寻找root 目录下的空文件
find ~ -empty

### 将系统中所有jpd文件压缩打包
$ find / -name *.jpg -type f -print | xargs tar -cvzf images.tar.gz

### tar gzip bzip2 
tar 对文件目录进行打包备份，生成一个.tar文档  
gzip bzip2 都是压缩程序，可以和tar 结合使用  
使用 gzip要比bzip2快，但是bzip2会获得比 gzip高的压缩率  

### linux sed 模糊匹配
sed -i "s#^phoenixJDBCURL.*#phoenixJDBCURL=jdbc:phoenix:${jdbc}:2181#g" demo.log
.* 匹配任意多个字符