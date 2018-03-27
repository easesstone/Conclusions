## Hive
### hive 查看表格：
```
describe tableName
hive 添加分区
set hive.exec.dynamic.partition=true;
set hive.exec.dynamic.partition.mode=nonstrict;

alter table tableName add partition(date='datevale',ipcid='ipcidVlue');
```

### hive 数据迁移,从一张表格向另一张表格进行插入数据
```
insert into table mid_table select * from person_table where date='2018-03-20'
 and ipcid='DS-2CD2T20FD-I320160122AACH571485690';
```

### 从person_table 还原所有数据到mid_table
```
#!/bin/bash
################################################################################
## Copyright:    HZGOSUN Tech. Co, BigData
## Filename:     create-dynamic-table.sh
## Description:  创建动态库表(person_table,mid_table)
## Author:       qiaokaifeng
## Created:      2017-11-28
################################################################################

set -x
#---------------------------------------------------------------------#
#                              定义变量                               #
#---------------------------------------------------------------------#

cd `dirname $0`
## bin目录
BIN_DIR=`pwd`
cd ..
DEPLOY_DIR=`pwd`
## 配置文件目录
CONF_DIR=${DEPLOY_DIR}/conf
## Jar 包目录
LIB_DIR=${DEPLOY_DIR}/lib
## log 日记目录
LOG_DIR=${DEPLOY_DIR}/logs
##  log 日记文件
LOG_FILE=${LOG_DIR}/create-dynamic-table.log
## bigdata cluster path
BIGDATA_CLUSTER_PATH=/opt/hzgc/bigdata
## bigdata hive path
SPARK_PATH=${BIGDATA_CLUSTER_PATH}/Spark/spark

## 创建person_table
i=1;
for partition in $(cat ${BIN_DIR}/demo.log);do
    date=$(echo ${partition} | awk -F ":" '{print $1}')
    ipcid=$(echo ${partition} | awk -F ":" '{print $2}')
    ${SPARK_PATH}/bin/spark-sql -e "insert into table mid_table select \
                                    * from person_table where date='${date}' and ipcid='${ipcid}'"
    echo $i
    let i++
done

```