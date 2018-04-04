## Linux磁盘挂载
#### 1、fdisk -l查看磁盘名称

```
Disk /dev/xvda: 42.9 GB, 42949672960 bytes
255 heads, 63 sectors/track, 5221 cylinders, total 83886080 sectors
Units = sectors of 1 * 512 = 512 bytes
Sector size (logical/physical): 512 bytes / 512 bytes
I/O size (minimum/optimal): 512 bytes / 512 bytes
Disk identifier: 0x000d7b57

    Device Boot      Start         End      Blocks   Id  System
/dev/xvda1            2048     8386559     4192256   82  Linux swap / Solaris
/dev/xvda2   *     8386560    83886079    37749760   83  Linux

Disk /dev/xvde: 96.6 GB, 96636764160 bytes
255 heads, 63 sectors/track, 11748 cylinders, total 188743680 sectors
Units = sectors of 1 * 512 = 512 bytes
Sector size (logical/physical): 512 bytes / 512 bytes
I/O size (minimum/optimal): 512 bytes / 512 bytes
Disk identifier: 0x00000000

Disk /dev/xvde doesn't contain a valid partition table
```


#### 2、pvcreate /dev/xvde 创建PV

```
root@ldl:/ # pvcreate /dev/xvde
  Physical volume "/dev/xvde" successfully created
```

#### 3、vgcreate vg1 /dev/xvde 创建VG

```
root@ldl:/ # vgcreate vg1 /dev/xvde
  Volume group "vg1" successfully created
```


#### 4、vgdisplay查看VG大小：

```
 --- Volume group ---
  VG Name               vg1
  System ID             
  Format                lvm2
  Metadata Areas        1
  Metadata Sequence No  1
  VG Access             read/write
  VG Status             resizable
  MAX LV                0
  Cur LV                0
  Open LV               0
  Max PV                0
  Cur PV                1
  Act PV                1
  VG Size               90.00 GiB
  PE Size               4.00 MiB
  Total PE              23039
  Alloc PE / Size       0 / 0   
  Free  PE / Size       23039 / 90.00 GiB
  VG UUID               55dqvu-cwwL-Jzvx-xncE-LKTz-fT5P-Fbc9RM
```


#### 5、lvcreate -l ***** -n lv1 vg1 （ * 为上一步骤vgdipaly查询到的VG容量,Free PE 23039）创建LV

```
Logical volume "lv1" created
```

#### 6、mkfs.ext3 -j /dev/vg1/lv1      (Ubuntu使用ext4，suse使用ext3)  tune2fs -c 0 -i 0 /dev/vg1/lv1 


```
格式化磁盘：
ROOT@LDL:/ # mkfs.ext3 -j /dev/vg1/lv1
mke2fs 1.41.9 (22-Aug-2009)
Filesystem label=
OS type: Linux
Block size=4096 (log=2)
Fragment size=4096 (log=2)
5898240 inodes, 23591936 blocks
1179596 blocks (5.00%) reserved for the super user
First data block=0
Maximum filesystem blocks=4294967296
720 block groups
32768 blocks per group, 32768 fragments per group
8192 inodes per group
Superblock backups stored on blocks: 
	32768, 98304, 163840, 229376, 294912, 819200, 884736, 1605632, 2654208, 
	4096000, 7962624, 11239424, 20480000

Writing inode tables: done                            
Creating journal (32768 blocks): done
Writing superblocks and filesystem accounting information: done

This filesystem will be automatically checked every 21 mounts or
180 days, whichever comes first.  Use tune2fs -c or -i to override.
ROOT@LDL:/ # tune2fs -c 0 -i 0 /dev/vg1/lv1 
tune2fs 1.41.9 (22-Aug-2009)
Setting maximal mount count to -1
Setting interval between checks to 0 seconds
```

#### 7、fdisk -l查询磁盘状态，生成 /dev/dm-0： (Suse 系统下执行这一步，ubuntu 转至11)

#### 8、mount /dev/dm-0 /usr1 挂载磁盘（/usr1为挂载目录，如无此目录，需先创建，另此目录可根据业务需要进行修改）：

#### 9、 修改启动项，将磁盘信息记录到文件系统中，保证重启后，磁盘仍按照指定路径挂载.

```
编辑：vi /etc/fstab/
在最后一行写入：/dev/dm-0 /usr1 ext3 defaults 0 0   使用系统重启时实现自动挂载 (ubuntu使用ext4,suse和redhat使用ext3)
```


#### 10、df -h 查看磁盘挂载状态：

#### 11、mount /dev/vg1/lv1 /usr1 挂载磁盘（/usr1为挂载目录，如无此目录，需先创建，另此目录可根据业务需要进行修改）：

#### 12、echo “/dev/mapper/vg1-lv1 /usr1 ext4 defaults 0 0” >>/etc/fstab 修改启动项，使用系统重启时实现自动挂载（unbuntu使用ext4）
       more /etc/fstab 查看是否修改成功，/etc/fstab文件显示存在下图红框部分说明修改成功。



# 其他挂载方法
```
Linux的硬盘识别:
一般使用”fdisk -l”命令可以列出系统中当前连接的硬盘
设备和分区信息.新硬盘没有分区信息,则只显示硬盘大小信息.

1.关闭服务器加上新硬盘

2.启动服务器，以root用户登录

3.查看硬盘信息
#fdisk -l
[cpp] view plain copy 在CODE上查看代码片派生到我的代码片
Disk /dev/sda: 42.9 GB, 42949672960 bytes  
255 heads, 63 sectors/track, 5221 cylinders  
Units = cylinders of 16065 * 512 = 8225280 bytes  
Sector size (logical/physical): 512 bytes / 512 bytes  
I/O size (minimum/optimal): 512 bytes / 512 bytes  
Disk identifier: 0x0004406e  
   Device Boot      Start         End      Blocks   Id  System  
/dev/sda1   *           1          39      307200   83  Linux  
Partition 1 does not end on cylinder boundary.  
/dev/sda2              39        2589    20480000   83  Linux  
/dev/sda3            2589        2850     2097152   82  Linux swap / Solaris  
/dev/sda4            2850        5222    19057664    5  Extended  
/dev/sda5            2850        5222    19056640   83  Linux  
   
Disk /dev/sdb: 10.7 GB, 10737418240 bytes  
255 heads, 63 sectors/track, 1305 cylinders  
Units = cylinders of 16065 * 512 = 8225280 bytes  
Sector size (logical/physical): 512 bytes / 512 bytes  
I/O size (minimum/optimal): 512 bytes / 512 bytes  
Disk identifier: 0x14b52796  
   Device Boot      Start         End      Blocks   Id  System  

4.创建新硬盘分区命令参数：
fdisk可以用m命令来看fdisk命令的内部命令；
a：命令指定启动分区；
d：命令删除一个存在的分区；
l：命令显示分区ID号的列表；
m：查看fdisk命令帮助；
n：命令创建一个新分区；
p：命令显示分区列表；
t：命令修改分区的类型ID号；
w：命令是将对分区表的修改存盘让它发生作用。
 

5.进入磁盘，对磁盘进行分区，注意红色部分。
#fdisk /dev/sdb
[cpp] view plain copy 在CODE上查看代码片派生到我的代码片
Command (m for help):n  
Command action  
　　   e    extended                  //输入e为创建扩展分区  
　　   p    primary partition (1-4)      //输入p为创建逻辑分区  
p  
Partion number(1-4)：1      //在这里输入l，就进入划分逻辑分区阶段了；  
First cylinder (51-125, default 51):   //注：这个就是分区的Start 值；这里最好直接按回车，如果您输入了一个非默认的数字，会造成空间浪费；  
Using default value 51  
Last cylinder or +size or +sizeM or +sizeK (51-125, default 125): +200M 注：这个是定义分区大小的，+200M 就是大小为200M ；当然您也可以根据p提示的单位cylinder的大小来算，然后来指定 End的数值。回头看看是怎么算的；还是用+200M这个办法来添加，这样能直观一点。如果您想添加一个10G左右大小的分区，请输入 +10000M ；  
  
Command (m for help): w                     //最后输入w回车保存。  
查看一下： 
#fdisk -l
可以看到/dev/sdb1分区,我就省略截图咯。
 
6.格式化分区：
#mkfs.ext3 /dev/sdb1           //注：将/dev/sdb1格式化为ext3类型
[cpp] view plain copy 在CODE上查看代码片派生到我的代码片
mke2fs 1.41.12 (17-May-2010)  
文件系统标签=  
操作系统:Linux  
块大小=4096 (log=2)  
分块大小=4096 (log=2)  
Stride=0 blocks, Stripe width=0 blocks  
640848 inodes, 2562359 blocks  
128117 blocks (5.00%) reserved for the super user  
第一个数据块=0  
Maximum filesystem blocks=2625634304  
79 block groups  
32768 blocks per group, 32768 fragments per group  
8112 inodes per group  
Superblock backups stored on blocks:  
        32768, 98304, 163840, 229376, 294912, 819200, 884736, 1605632  
   
正在写入inode表: 完成  
Creating journal (32768 blocks): 完成  
Writing superblocks and filesystem accounting information: 完成  
   
This filesystem will be automatically checked every 35 mounts or  
180 days, whichever comes first.  Use tune2fs -c or -i to override.  
这样就格式化好了，我们就可以用mount 加载这个分区，然后使用这个文件系统；

7.创建/data1目录：
#mkdir /data1

8.开始挂载分区：
#mount /dev/sdb1 /data1

9.查看硬盘大小以及挂载分区：
#df -h

10.配置开机自动挂载
因为mount挂载在重启服务器后会失效，所以需要将分区信息写到/etc/fstab文件中让它永久挂载：
#vim /etc/fstab
加入：
/dev/sdb1(磁盘分区)  /data1（挂载目录） ext3（文件格式）defaults  0  0

11.重启系统

```