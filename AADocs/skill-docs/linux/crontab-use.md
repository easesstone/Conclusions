```
crontab 常用命令;
使用：
    1，写好脚本。
    2，编辑/etc/crontab 文件，加入类似如下内容。
    3，重启crontab。
     



crontab
/sbin/service crond start    //启动服务
/sbin/service crond stop     //关闭服务
/sbin/service crond restart  //重启服务
/sbin/service crond reload   //重新载入配置
/sbin/service crond status   //查看服务状态 


cat  /etc/crontab

SHELL=/bin/bash
PATH=/sbin:/bin:/usr/sbin:/usr/bin
MAILTO=root
HOME=/

# For details see man 4 crontabs

# Example of job definition:
# .---------------- minute (0 - 59)
# |  .------------- hour (0 - 23)
# |  |  .---------- day of month (1 - 31)
# |  |  |  .------- month (1 - 12) OR jan,feb,mar,apr ...
# |  |  |  |  .---- day of week (0 - 6) (Sunday=0 or 7) OR sun,mon,tue,wed,thu,fri,sat
# |  |  |  |  |
# *  *  *  *  * user-name command to be executed


例子：
每天早上六点：
0 6 * * * 
每两个小时
0 */2 * * *
晚上11点到早上8点之间每两个小时包括早上的8点
0 23-7/2，8 * * * 
每个月的4号和每个礼拜的礼拜一到礼拜三的早上11点 
0 11 4 * 1-3
1月1日早上4点
0 4 1 1 *  


0-59/1 * * * 每分钟
* 0-23/2 * * * 每两个小时。
0-59/1 * * * * root /home/ldl/demo.sh

```
