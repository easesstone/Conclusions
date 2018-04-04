# 使用技巧
-23,[用户密码过期解决办法](linux-user-password-outdate.md)  
-22,解压jar包：unzip zk-helper-1.0.jar  -d hello  
-21,[网络设置工具ifconfig](ifconfig.md)  
-20,[Linux 用户操作监听脚本](linux-jianting-user-caozuo.md)  
-19,[Linux 自启动设置](Linux-start-app-when-reboot.md)  
-18,对比文件，找出不同的行：cat suite_[1-9].txt suite_[1-9][0-9].txt  | sort | uniq -d（相同行uniq -u）  
-17,[服务器权限管理](linux-quanxian.md)  
-16,sed 添加一行,sed -i '4 s/^/good baby\n/' test.txt,在第四行添加一行，其余数据往后移动    
-15,[Linux 并发执行技巧](Linux-bingfa-zhi-xing.md)  
-14,[字符串截取](Linux-sub-zifuchuan.md)  
-13,Linux 传参技巧
```
$# 是传给脚本的参数个数
$0 是脚本本身的名字
$1 是传递给该shell脚本的第一个参数
$2 是传递给该shell脚本的第二个参数
$@ 是传给脚本的所有参数的列表
$* 是以一个单字符串显示所有向脚本传递的参数，与位置变量不同，参数可超过9个
$$ 是脚本运行的当前进程ID号
$? 是显示最后命令的退出状态，0表示没有错误，其他表示有错误
```
-12,[linux 磁盘挂载顺序详解,两种不同的挂载方法](Linux-cipan-guazai-shunxu.md)  
-11,[在服务器之间进行文件同步工具xsync](xsync.md)  
-10,[在服务其中同时执行命令工具xcall](xcall.md)  
-9，变量定义赋初值：VERSION=${VERSION:-enhaenha}    
-8, sort -n 按照自然数进行排序，或者按照字典序进行排序  
-7, sed 行替换，sed -i "s#ha_zookeeper_quorum#${ZK_LISTS}#g"  yarn-site.xml   
-6, sed -i "/^$suite/d" hello.txt,sed -i '/^$suite/d' hello.txt, 删除带有某某字段的一行    
-5, [保存下载的rpm](linunx-save-rpm.md)  
-4, [svn commit](linux-svn-commit.md)  
-3, [Linux 服务器间网速测试](iperflinux.md)  
-2, [source-sh-exec各种执行shell脚本方法区别以及PID获取](source-sh-exec-diff-getppid.md)  
-1, [linux 服务器之间免密码登录设置](ssh-no-password.md)  
0,  [Linux 常用bash 设置](linux-bashrs.md)  
1,  [xshell 打开Linux图形界面](shell-xshell.md)  
2,  [cpu 型号，物理个数，物理核数，逻辑核数](Linux-cpu-info-demo.md)  
3,  [进程号和端口间的关系](linx-process-and-its-duankou.md)  
4,  [grep -E 匹配多个值,xcall jps | grep -E 'NameNode|NodeManager|DataNode|ResourceManager'](grep-e-pipei.md)    
5,  [awk -F打印每一行的值](awk-print-all.md)  
6,  匹配空行 grep ^$ logs.log  
7,  grep 中如果字符包含点，则表示匹配任意一个字符（除了\n  
8,  行数统计wc -l]  
9,  打印空行行号：grep -n ^$ yarn-site.xml  | awk -F ":" '{print $1}'
10, 查找以abc结尾的行: grep abc$ fileName  
11, [if多条件匹配,以及脚本参数验证。](linux-args-comfirm.md)  
12, 数学工具bc的使用: echo "(${Tatal_CORES}-${DRIVER_CORES})/${EXECUTOR_CORES}" | bc  
13, ssh -l root hostnameOrIp: -l 指定用户名  
14, 注释多行(:<<EOF 内容  EOF)  
15, 查找空文件，即文件大小是0的文件：find ~ -empty  
16, sed 模糊匹配，sed -i "s#^phoenixJDBCURL.*#phoenixJDBCURL=jdbc:phoenix:${jdbc}:2181#g" demo.log，.* 匹配任意多个字符  
17, 将系统中的所有jpg文件打包：find / -name *.jpg -type f -print | xargs tar -cvzf images.tar.gz  
18, [tar,zip,bzip2 的区别](tar-gzip-bzip2.md)  
19, [数学计算的的简单使用参考](linux-math-jisuan.md)  
20, [git authorized key不生效参考](authorized_keys.md)  
21, [centOs 和Ubutu 系列默认bash 不一致问题](centOs-bash-ubutu-dash.md)  
22, 如果用户没有读写权限，则会无法删除文件的时候，可以用如下方法进行强制删除：echo "huawei" | su root -C "rm -rf $WORKSPACE/*"  
23, crond 无法执行的可能性很大的几个原因，脚本没有执行权限，脚本的环境变量和crond的环境变量不一致  
24, [expect免密码登录示范](expect-login-with-no-password.md)  
25, 验证jar 包是否同源 find . -name  hadoop-annotations-2.7.2.jar |xargs sha256sum  
26, [局域网静态ip设置参考](ju-yu-wang-ip-config.md)  
27, [vmware 中装ubutu 允许远程登录设置](vmware_ubutu-can-login-by-remote.md)  
28, [sed 获取数据sed -n '1,2p' bulk.json， 获取第一行到第二行，包含第二行， 获取第二行数据sed -n '2,2p' bulk.json]()  
29, [Suse系统自动挂载磁盘参考](auto-mount-script.md)  
30, [Gcc 安装参考](GCC-install.md)  
31, [时间同步设置](time-sync-clock.md)  
32, [定时任务的使用crontab](crontab-use.md)  
33, [常用软件的安装](installSoftWare.md)  
34, 去除空行grep -v "^$"  
35, CentOS 卸载java， yum remove java  
36, 列出当前所有的目录ls -d */  
37, 关于tar hello.tar.gz  ./* 与tar hello.tar.gz ./,前者不会打包.git .*等文件. 后者会,tar 可以加-C 指定要解压要的目录。  
38, Ubutu 可以用如下命令进行bash 切换sudo dpkg-reconfigure dash  
39, 可以用make uninstall 进行卸载  
40, 注意centos 和 ubutu 中脚本的兼容性  
41, [ubuntu 內源使用](Ubuntu-nei-yuan.md)  
42, [linux 网络ip 小时解决办法](service-machine-operation.md)  
43, jar 中查找text 文本：jar -tf ***jar |grep -i text  
44, [Linux 时区设置](Linux-timeZone.md)  
45, [解决ssh的"Write failed: Broken pipe"问题](SSH-BROKEN-PINELINE.md)  
46, [Linux 可以打开的最大文件数](linux-open-max-file.md)  
47, [rsync命令](rsync.md)  
48, [Linux 磁盘扩容](Linux-ci-pan-kuo-rong.md)  
48, [RedHat 修改默认jdk](Linux-xiugai-moren-jdk.md)  
49, 覆盖默认GCC：export $PATH=gcc/home/***:$PATH   
50, shell 以root用户登录,然后切换到其他用户进行执行脚本，ssh root@172.18.18.106 'su -c "source /etc/profile;cd  /opt/hzgc/bigdata/Elastic/elastic;ls;whoami" esuser'  
51, [Linux 资源分配 ulimit](Linux-ulimit.md)  
52, [ssh 第一次登录到其他节点的时候，不用数据yes设置](Login-With-No-Yes.md)  
53, 删除当前所有目录：ls -F | grep  '/$' |xargs rm -rf 或者 ls -d */ | xargs rm -rf  
54, 删除当前目录所有文件： ls -F | grep -v '/$' | xargs rm -rf  或者 find . -type f | xargs rm -rf   
55，[linux 卸载磁盘报错常见解决办法](LinuxUmount-field.md)  
56, [4791 -- process information unavailable](processInfomationUnavailable.md)  
57, [Linux 磁盘自动分区](Linux-cipan-cidong-fenqu.md)  
58, [RedHat关闭防火墙](Rehad-close-FireWord.md)  
59, 查看当前文件的个数： find /export2/BigData/datanode/dn1 -type f | wc -l  
60, 检查系统关闭或者重启的时间：方法一（用于reboot的场景）： who -b 方法二（用于shutdown的场景）： last -x|grep shutdown  
62, sed 删除包含abc 的一行sed -e '/abc/d'  a.txt  > a.log  
63, 内存释放：sync;echo 3 > /proc/sys/vm/drop_caches  
64, 集群间时区同步;ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime;hwclock -w  
65, hdfs 重定向日记 hdfs dfs -ls / >/dev/null 2> test.log&  
66, 在控制台打印hadoop 日记：  export HADOOP_CLIENT_OPTS=-Dhadoop.root.logger=DEBUG,console  
67, hdfs执行命令开启debug，在hadoop-env.sh中加上以下参数：export HADOOP_ROOT_LOGGER=DEBUG,RFA  
68, -exec 用法：find -name hadoop-common*jar -exec ls -l {} \;查出所有的文件并展示详细列表，-exec 参数为任何执行命令，"{}"表示前面过滤的数据，以“；”结尾，但是需要转移，则为"\;"  
69, grep string_search path -R -A line_num -B line_num 查找path 下带有string_search 内容，以及前面几行和后面几行。  



































