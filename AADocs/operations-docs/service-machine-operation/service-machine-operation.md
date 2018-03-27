## 网络ip消失解决办法
```
root@s156:~# cat /etc/network/interfaces
# interfaces(5) file used by ifup(8) and ifdown(8)
auto lo
iface lo inet loopback
auto eno1
iface eno1 inet dhcp
iface eno1 inet6 auto

root@s156:~# service networking restart
```
