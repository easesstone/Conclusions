```
介绍
Hive Load语句不会在加载数据的时候做任何转换工作，而是纯粹的把数据文件复制
/移动到Hive表对应的地址。

语法
LOAD DATA [LOCAL] INPATH 'filepath' [OVERWRITE] INTO TABLE tablename 
[PARTITION (partcol1=val1,partcol2=val2 ...)]

描述
filepath 可以是： 
相对路径，如project/data1
绝对路径，如/user/hive/project/data1
完整的URL，如hdfs://namenode:9000/user/hive/project/data1
目标可以是一个表或是一个分区。如果目标表是分区表，必须指定是要加载到哪个分区。
filepath 可以是一个文件，也可以是一个目录(会将目录下的所有文件都加载)。
如果命令中带LOCAL，表示： 
load命令从本地文件系统中加载数据，可以是相对路径，也可以是绝对路径。对于本地文件系统，也可以使用完整的URL，如file:///user/hive/project/data1
load命令会根据指定的本地文件系统中的filepath复制文件到目标文件系统，
然后再移到对应的表
如果命令中没有LOCAL，表示从HDFS加载文件，filepath可以使用完整的URL方式，
或者使用fs.default.name定义的值
命令带OVERWRITE时加载数据之前会先清空目标表或分区中的内容，否则就是追加的方式。

例如：
LOAD DATA INPATH "/user/hive/warehouse/mid_table/14353b9586e7427f8d7face90bb3ee92/"  INTO TABLE mid_table;
表示从hdfs 中的/user/hive/warehouse/mid_table/14353b9586e7427f8d7face90bb3ee92/ 加载数据。
这个目录下的数据文件会被移动，相当于linux 中的mv  命令。
```
