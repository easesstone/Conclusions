#!/bin/bash
################################################################################
## Copyright:   HZGOSUN Tech. Co, BigData
## Filename:    ceph-get-object-demo-V1.sh
## Description: 取出所有对象文件
## Version:     1.0
## Author:      lidiliang
## Created:     2018-01-02
################################################################################
set -x  ## 用于调试用，不用的时候可以注释掉

#---------------------------------------------------------------------#
#                              定义变量                                #
#---------------------------------------------------------------------#
cd `dirname $0`
BIN_DIR=`pwd`    ### bin目录
cd ..
DEPLOY_DIR=`pwd`
LIB_DIR=$DEPLOY_DIR/lib        ## Jar 包目录
LIB_JARS=`ls $LIB_DIR|grep .jar|awk '{print "'$LIB_DIR'/"$0}'|tr "\n" ":"`    ## jar 包位置以及第三方依赖jar包，绝对路径
LOG_DIR=${DEPLOY_DIR}/logs                       ## log 日记目录
LOG_FILE=${LOG_DIR}/ceph-get-object-demo-V1.log      ##  log 日记文件


localObjectPath=$1
key=$2
cephGWBalanceNode=$3
accessKey=$4
secretKey=$5
bucketName=$6

#####################################################################
# 函数名: test_ceph_client_haproxy_performance
# 描述: 测试类Main 主入口，用于测试ceph 对象网关的性能
# 参数: N/A
# 返回值: N/A
# 其他: N/A
#####################################################################
function test_get_all_object_info()
{
    nohup java -server -Xms2g -Xmx4g  -XX:PermSize=512m -XX:MaxPermSize=512m  -classpath \
    $CONF_DIR:$LIB_JARS com.sydney.dream.facecomdemo.GetAllObjectFromInputKeys $localObjectPath $key \
     $cephGWBalanceNode $accessKey $secretKey $bucketName > ${LOG_FILE} 2>&1 &
}

#####################################################################
# 函数名: main
# 描述: 脚本主要业务入口
# 参数: N/A
# 返回值: N/A
# 其他: N/A
#####################################################################
function main()
{
    test_get_all_object_info
}


## 脚本主要业务入口
main
