```
#!/bin/bash
################################################################################
## Copyright:   HZGOSUN Tech. Co, BigData
## Filename:    xcall
## Description: 一个辅助工具。方便查看各个机器上面的进程，
## 以及在各个机器上面进行执行命令
## Version:     1.0
## Author:      lidiliang
## Created:     2017-11-25
################################################################################

#set -x

#---------------------------------------------------------------------#
#                              定义变量                               #
#---------------------------------------------------------------------#
cd `dirname $0`
## 脚本所在目录
export BIN_DIR=`pwd`
cd ..
## 安装包根目录
export ROOT_HOME=`pwd`
## 配置文件目录
export CONF_DIR=${ROOT_HOME}/conf
## 安装日记目录
export LOG_DIR=${ROOT_HOME}/logs
## 日记文件
export LOG_FILE=${LOG_DIR}/xcall.log

## 集群所有节点主机名，放入数组中
CLUSTER_HOSTNAME_LISTS=$(grep Cluster_HostName ${CONF_DIR}/cluster_conf.properties|cut -d '=' -f2)
CLUSTER_HOSTNAME_ARRY=(${CLUSTER_HOSTNAME_LISTS//;/ })

pcount=$#
if(($pcount<1)) ; then
  echo "usage:  ./xcall command,  such as : ./xcall jps"
  exit 0
fi

command_do_in_all_nodes(){
    for host in ${CLUSTER_HOSTNAME_ARRY[@]};do
        echo  ""
        echo "---------------------------------------------------------"
        echo "$(date "+%Y-%m-%d  %H:%M:%S"), in ${host} doing  $@ show as bellow..."
        ssh ${host} "source /etc/profile;$@"
    done
}


command_do_in_all_nodes $@

set +x
```