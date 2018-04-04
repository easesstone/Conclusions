```
############################################################################
## Copyright:   HZGOSUN Tech. Co, BigData
## Function:    登录监听服务
## Description: 记录用户登录的操作
## Version:     1.0.0
## Author:      lidiliang
## Created:     2017-12-07
################################################################################

#---------------------------------------------------------------------#
#                              start                                  #
#---------------------------------------------------------------------#
## 用户登录IP
USER_IP=`who -u am i 2>/dev/null| awk '{print $NF}'|sed -e 's/[()]//g'` 
if [ "$USER_IP" = "" ];then
    USER_IP=`hostname` 
fi
## 所有登录用户日记跟目录
if [ ! -d /home/login_history ];then 
    mkdir -p /home/login_history
    chmod 777 /home/login_history
fi
## 登录用户日记目录
if [ ! -d /home/login_history/${LOGNAME} ];then
    mkdir -p /home/login_history/${LOGNAME}
fi
## 每次登录记录的操作保存的条数
export HISTSIZE=65636
## 每次登录的时间
login_time=`date +"%Y%m%d_%H%M%S"`
## 登录日记文件
export HISTFILE="/home/login_history/${LOGNAME}/${USER_IP}.${login_time}.history"
chmod 600 /tmp/history/${LOGNAME}/*history* 2>/dev/null
export HISTIGNORE="pwd:ls:ls -ltr:ll"
#export HISTTIMEFORMAT="%Y-%m-%d %H:%M:%S  `whoami`  "
#---------------------------------------------------------------------#
#                              end                                    #
#---------------------------------------------------------------------#
```
