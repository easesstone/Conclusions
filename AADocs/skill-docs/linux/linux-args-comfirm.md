### linux if多条件匹配,以及脚本参数验证。
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