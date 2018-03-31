```shell
#!/bin/bash
################################################################################
## Copyright:   HZGOSUN Tech. Co, BigData
## Filename:    schema-merge-parquet-file.sh
## Description: 定时启动合并小文件的脚本,包括两个功能
##              1,合并临时表mid_tabel 中的parquet 文件
##              2，定时合并当天的person_table 中的数据。
##              3，合并前一天的person_table 中的数据。
##              4, 额外的功能，对person 表某一天的数据进行合并。
## Author:      lidiliang
## Created:     2017-11-06
## Modified:    2017-12-01(lidiliang)
################################################################################
#set -x  ## 用于调试用，不用的时候可以注释掉

### TODO公共变量统一
#---------------------------------------------------------------------#
#                              公共变量统一                            #
#---------------------------------------------------------------------#


#---------------------------------------------------------------------#
#                              变量定义                               #
#---------------------------------------------------------------------#
source /etc/profile
cd `dirname $0`
BIN_DIR=`pwd`    ### bin目录
cd ..
DEPLOY_DIR=`pwd`
CONF_DIR=$DEPLOY_DIR/conf    ### 项目根目录                      ## log 日记目录
LOG_DIR=$DEPLOY_DIR/logs
LOG_FILE=${LOG_DIR}/schema-merge-parquet-file.log        ##  log 日记文件
cd ..
declare -r BIGDATA_SERVICE_DIR=`pwd`
declare -r COMMMON_DIR=${BIGDATA_SERVICE_DIR}/common
declare -r FTP_DIR=${BIGDATA_SERVICE_DIR}/ftp
declare -r SERVICE=${BIGDATA_SERVICE_DIR}/service
declare -r CLUSTER_DIR=${BIGDATA_SERVICE_DIR}/cluster
CLUSTER_VERSION=`ls ${COMMMON_DIR}/lib| grep ^cluster-[0-9].[0-9].[0-9].jar$`
SPARKJOB_PROPERTIES=${CONF_DIR}/sparkJob.properties
hdfsClusterName=$(grep job.smallfile.merge.hdfs.name  ${SPARKJOB_PROPERTIES} | awk -F "=" '{print $2}')
tmpTableHdfsPath=$(grep job.smallfile.merge.mid_table.hdfs_path  ${SPARKJOB_PROPERTIES} | awk -F "=" '{print $2}')
hisTableHdfsPath=$(grep job.smallfile.merge.person_table.hdfs_path  ${SPARKJOB_PROPERTIES} | awk -F "=" '{print $2}')
tableName=$(grep job.smallfile.merge.person_table.name   ${SPARKJOB_PROPERTIES}| awk -F "=" '{print $2}')
dateString=""
BIGDATA_ENV_FILE=/opt/hzgc/env_bigdata.sh
source ${BIGDATA_ENV_FILE}
mkdir -p ${LOG_DIR}

#---------------------------------------------------------------------#
#                              函数定义                               #
#---------------------------------------------------------------------#

#####################################################################
# 函数名: showUsage
# 描述: 输入参数不对的时候的提示信息
# 参数: N/A
# 返回值: N/A
# 其他: N/A
#####################################################################
function showUsage() {
    echo "usage: "
    echo "1, sh schema-merge-parquet-file.sh mid_table to 和并临时表mid_table 的parquet 文件."
    echo "2, sh schema-merge-parquet-file.sh person_table now  合并person_table 表中当天的parquet文件. "
    echo "3, sh schema-merge-parquet-file.sh person_table before 合并person_table 表格中前一天的parquet文件."
    echo "4, sh schema-merge-parquet-file.sh person_table 2018-01-03 合并person_table 表格中指定某天的文件."
    exit 1;
}

#####################################################################
# 函数名: merge_parquet
# 描述: 小文件合并
# 参数: N/A
# 返回值: N/A
# 其他: N/A
#####################################################################
function merge_parquet()
{
    if [ ! -d $LOG_DIR ]; then
        mkdir $LOG_DIR;
    fi
    if [ "${dateString}" == "" ];then
        spark-submit --class com.hzgc.cluster.smallfile.MergeParquetFileV1 \
        --master local[*] \
        --driver-memory 4g \
        ${COMMMON_DIR}/lib/${CLUSTER_VERSION} ${hdfsClusterName} ${tmpTableHdfsPath} ${hisTableHdfsPath} ${tableName} ${dateString}
		checkReturn $?
    else
        spark-submit --class com.hzgc.cluster.smallfile.MergeParquetFileV2 \
        --master local[*] \
        --driver-memory 4g \
        ${COMMMON_DIR}/lib/${CLUSTER_VERSION} ${hdfsClusterName} ${tmpTableHdfsPath} ${hisTableHdfsPath} ${tableName} ${dateString}
		checkReturn $?
    fi
    if [ $? -eq 0 ];then
        echo "==================================================="  | tee -a ${LOG_FILE}
        echo "$(date "+%Y-%m-%d  %H:%M:%S")  Schema merge parquet file Success!"  | tee  -a  ${LOG_FILE}
    else
        echo "==================================================="  | tee -a ${LOG_FILE}
        echo "$(date "+%Y-%m-%d  %H:%M:%S")  Schema merge parquet file Failure!"     | tee  -a  ${LOG_FILE}
        exit 1
    fi
}

#####################################################################
# 函数名: showRunTime
# 描述: 显示脚本执行的时间
# 参数: N/A
# 返回值: N/A
# 其他: N/A
#####################################################################
function showRunTime() {
	echo ""  | tee  -a  $LOG_FILE
	echo "当前时间为："  | tee  -a  $LOG_FILE
	echo "==================================================="  | tee -a $LOG_FILE
	echo "$(date "+%Y-%m-%d  %H:%M:%S")"                       | tee  -a  $LOG_FILE
}

#####################################################################
# 函数名: checkReturn
# 描述: 检查小文件合并的成功与否
# 参数: N/A
# 返回值: N/A
# 其他: N/A
#####################################################################
function checkReturn() {
	if [ $1 == 0 ];then
		echo "Schema merge parquet file success!!!"
        echo "==================================================="  | tee -a ${LOG_FILE}
        echo "$(date "+%Y-%m-%d  %H:%M:%S")  Schema merge parquet file Success!"  | tee  -a  ${LOG_FILE}
	else
		echo "==================================================="  | tee -a ${LOG_FILE}
        echo "$(date "+%Y-%m-%d  %H:%M:%S")  Schema merge parquet file Failure!"     | tee  -a  ${LOG_FILE}
        exit 1
	fi
}


#---------------------------------------------------------------------#
#                              功能代码块                             #
#---------------------------------------------------------------------#
#---------------------------------------------------------------------#
#                              参数验证                               #
#---------------------------------------------------------------------#
## 不符合情况的条件下，展示提示信息，并且退出
showRunTime
tmp=$(echo “$2” | grep ^[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]$) 
if [[ !(($# == 1 && "$1" == "mid_table") || ($# == 2 && "$1" == "person_table" && "$2" == "now") || \
($# == 2 && "$1" == "person_table" && "$2" == "before") || ($# == 2 && "$1" == "person_table" && "$tmp" != "")) ]];then
    showUsage
fi


#---------------------------------------------------------------------#
#                              执行业务                               #
#---------------------------------------------------------------------#

if [[ $# == 1 && "$1" == "mid_table" ]];then
    echo "1, sh schema-merge-parquet-file.sh mid_table 合并临时表mid_table 的parquet 文件."
    merge_parquet
fi

tmpTableHdfsPath=$(grep job.smallfile.merge.person_table.hdfs_path  ${SPARKJOB_PROPERTIES} | awk -F "=" '{print $2}')
if [[ $# == 2 && "$1" == "person_table" && "$2" == "now" ]];then
	echo "2, sh schema-merge-parquet-file.sh person_table now  合并person_table 表中当天的parquet文件."
	dateString=$(date "+%Y-%m-%d")
	merge_parquet
	checkReturn $?
fi

if [[ $# == 2 && "$1" == "person_table" && "$2" == "before" ]];then
	echo "3, sh schema-merge-parquet-file.sh person_table before 合并person_table"
	dateString=$(date -d "1 day ago" +"%Y-%m-%d")
	merge_parquet
	checkReturn $?
fi

if [[ $# == 2 && "$1" == "person_table" && "$tmp" != "" ]];then
	echo "4, sh schema-merge-parquet-file.sh person_table before 2018-01-03 合并person_table 中指定某天的文件."
	dateString=$2
	merge_parquet
	checkReturn $?
fi 

set +x
```