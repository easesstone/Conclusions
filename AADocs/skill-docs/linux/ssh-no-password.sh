#!/bin/bash
################################################################################
## Copyright:   HZGOSUN Tech. Co, BigData
## Filename:    conf-no-need-password.sh
## Description: 安装 expect 工具，安装后可以用expect命令减少人与linux之间的交互
##              实现自动化的脚本
## Version:     1.0
## Author:      lidiliang
## Created:     2017-10-23
################################################################################

set -x

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
## 安装日记目录
export LOG_FILE=${LOG_DIR}/donot-need-to-enter-password-conf.log
## 系统root 用密码
export PASSWORD=123456
## authorized_keys 内容所在文件
export AUTHORIZED_KEYS=${BIN_DIR}/authorized_keys.log

echo >  ${AUTHORIZED_KEYS}


if [ ! -d $LOG_DIR ];then
    mkdir -p $LOG_DIR;
fi

echo ""  | tee  -a  $LOG_FILE
echo ""  | tee  -a  $LOG_FILE
echo "==================================================="  | tee -a $LOG_FILE
echo "$(date "+%Y-%m-%d  %H:%M:%S")"                       | tee  -a  $LOG_FILE

ssh_keygen(){
    expect << EOF
    spawn ssh root@$1 "ssh-keygen  -t rsa -C root@$1"
    while 1 {
        expect {
            "*assword:" {send "${PASSWORD}\n"}
            "yes/no*" {send "yes\n"}
            "Enter file in which to save the key*" {send "\n"}
            "Enter passphrase*" {send "\n"}
            "Enter same passphrase again:" {send "\n"}
            "Overwrite (y/n)" {send "y\n"}
            eof {exit}
        }
    }
EOF
}



get_id_rsa(){
    expect <<EOF
    spawn ssh root@$1
    expect {
        "password" {send "${PASSWORD}\r";}
        "yes/no" {send "yes\r";exp_continue}
    }
    expect "*#"
    send "cat /root/.ssh/id_rsa.pub;\n"  
    expect eof
    exit
EOF
}  

ssh_conf_first_yes(){
 expect <<EOF
    spawn ssh root@$1
    expect {
        "password" {send "${PASSWORD}\r";}
        "yes/no" {send "yes\r";exp_continue}
    }
    expect "*#"
    send "echo 'StrictHostKeyChecking no' >> /etc/ssh/ssh_config;\n"  
    expect eof
    exit
EOF
}


deliver_authorizedkesy_to_other_node(){
    expect <<EOF
    spawn scp /root/.ssh/authorized_keys root@$1:/root/.ssh
    expect {
        "password" {send "${PASSWORD}\r";}
        "yes/no" {send "yes\r";exp_continue}
    }
EOF
}

## 登录扫所有节点执行ssh-kegen -t rsa -C root@hostname 操作，并收集所有的id_rsa.pub 的内容到authorized_keys.log 文件中
get_authorized_keys_log(){
    for host in $(cat ${CONF_DIR}/hostnamelists.properties);do
        ssh_kegen ${host}
        get_id_rsa ${host}  | tee -a ${AUTHORIZED_KEYS}
    done
}

## 生成authorized_keys 
get_authorized_keys(){
    cat  ${AUTHORIZED_KEYS}  | grep ssh-rsa  > /root/.ssh/authorized_keys
}



## 分发authorized_keys
deliver_authorizedkesy_to_other_nodes(){
    for host in $(cat ${CONF_DIR}/hostnamelists.properties);do
        deliver_authorizedkesy_to_other_node ${host}
    done
}

## 两两登录取消第一次输入yes
config_no_password(){
    i=0
    for host in $(cat ${CONF_DIR}/hostnamelists.properties);do
        let i++
        echo  $i
        ssh_conf_first_yes ${host}
    done        
}

#get_authorized_keys_log

#get_authorized_keys

#deliver_authorizedkesy_to_other_nodes

config_no_password
