逻辑CPU个数：
# cat /proc/cpuinfo | grep 'processor' | wc -l
物理CPU个数：
# cat /proc/cpuinfo | grep 'physical id' | sort | uniq | wc -l
每个物理CPU中Core的个数：
# cat /proc/cpuinfo | grep 'cpu cores' | wc -l