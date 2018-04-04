```
#!/bin/bash
################################################################################
## Copyright:   HZGOSUN Tech. Co, BigData
## Filename:    xsync 
## Description: 一个同步工具，只同步有差异的文件，入参可以为文件名/相对路径/绝对路径
## Version:     1.0
## Author:      lidiliang
## Editor:      mashencai
## Created:     2017-11-25
################################################################################

#set -x

#当前的执行路径
EXECUTION_PATH=`pwd`
## 脚本所在目录
cd `dirname $0`
## 安装包根目录
ROOT_HOME=`cd ..;pwd`
## 配置文件目录
CONF_DIR=${ROOT_HOME}/conf

## 集群所有节点主机名，放入数组中
CLUSTER_HOSTNAME_LISTS=$(grep Cluster_HostName ${CONF_DIR}/cluster_conf.properties|cut -d '=' -f2)
CLUSTER_HOSTNAME_ARRY=(${CLUSTER_HOSTNAME_LISTS//;/ })

## USER whoami查看当前有效用户名
export USER=$(whoami)

# $#:表示返回所有脚本参数的个数。
# 若脚本的入参不为1，则输出该脚本的用法
pcount=$#
if(($pcount!=1)) ; then
  echo "usage:  ./xsync [file|path in this node, 绝对路径/相对路径] such as : ./xsync /etc/hosts"
  exit 0
fi

## INPUT_PARAMETERS 入参，可以为文件名/相对路径/绝对路径
export INPUT_PARAMETERS=$1
# 获取输入参数的目录部分PARAMETERS_DIR：
# 1.1：如果给定的参数本身为一个目录，那就得到当前目录的上一层目录。
# 1.2：如果给定的参数为文件，得到该文件的路径
# 2.1：若传入的为相对路径：获取到的目录还不是最终目录（如../a/test.t，获取到的是../a），需要得到该文件所在的目录
# 2.2：若传入的为绝对路径：获取到的目录是最终目录的上一层目录（如tmp/a/test.t，获取到的是tmp/a）
PARAMETERS_DIR=$(dirname ${INPUT_PARAMETERS}) #获取输入参数的上一层目录


# 获取输入参数中，最后一级目录（若输入绝对路径，则获取到的是文件名；若输入相对路径，则获取到的是最后一级目录）
#「##」号截取，删除左边字符，保留右边字符。
#「##*/」表示从左边开始删除最后（最右边）一个 / 号及左边的所有字符
FILE_OR_LASTPATH=${INPUT_PARAMETERS##*/}


# 传入的参数可能为文件或目录
# 无论是哪一种，都需要获取到给定路径的目录部分后，拼接上该文件的文件名or该目录的最后一级目录
# 若传入的为绝对路径：最终路径=截取参数的上一层目录部分+截取参数的文件/最后一级目录部分
# 若传入的为相对路径：最终路径=获取参数的所在目录上一层目录部分+截取参数的文件/最后一级目录部分
# 判断输入的参数为绝对路径还是相对路径（根据是否有“..”判断）
if [[ $INPUT_PARAMETERS =~ ".." ]];then ##输入参数为相对路径，一般若输入相对路径，说明都是在当前执行目录下可到达的目录
	cd $EXECUTION_PATH #进入当前执行路径
	REAL_FILE_DIR=$(cd $PARAMETERS_DIR;pwd)
	COMPLETE_PATH=${REAL_FILE_DIR}"/"${FILE_OR_LASTPATH}
	
else #输入参数为绝对路径（或仅为文件名）
	
	cd $EXECUTION_PATH #进入当前执行路径
	REAL_FILE_DIR=$(cd $PARAMETERS_DIR;pwd)
	COMPLETE_PATH=${REAL_FILE_DIR}"/"${FILE_OR_LASTPATH}

fi

# 函数：将文件/路径分发到不同节点
sync_the_file_to_every_nodes(){
    for host in ${CLUSTER_HOSTNAME_ARRY[@]};do
        echo ""
        echo "---------------------------------------------------------"
        echo "$(date "+%Y-%m-%d  %H:%M:%S"), in ${host} the jps show as bellow..."
		# ssh到每个节点，创建该节点上该文件的所在路径
		ssh ${USER}@${host} "mkdir -p ${PARAMETERS_DIR}"
		cd  ${PARAMETERS_DIR}
		rsync -rvl ${COMPLETE_PATH} ${USER}@${host}:${REAL_FILE_DIR}
	done
}

sync_the_file_to_every_nodes

set +x

```