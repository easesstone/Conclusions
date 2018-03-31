```
对昨天那个人脸集群的merge 模块重新架构一下：
要求：
1，ftp 重启的时候，检测没有处理过的数据，恢复未处理的数据。
2，对处理失败的数据，进行重新发送Kafka，恢复处理失败的数据。
实现要以ftp 插件的形式。
即作为ftp 进程的一个线程来处理数据。
不用发送到ceph ，也不用删除本地的ftp 数据文件。



/opt/logdata/receive/r-0
                        /000000000001.log
						/000000000002.log
                     r-1
					    /000000000001.log
						/000000000002.log
					 r-2 
					    /000000000001.log
						/000000000002.log
						
/opt/logdata/process/p-0
                        /0000000000001.log
						/0000000000002.log
					 p-1
                        /0000000000001.log
						/0000000000002.log
					 p-2
						/0000000000001.log
						/0000000000002.log
						


（确保两个文件对应起来, NIO 文件读取）						
1，ftp启动的时候，检查其中没有处理过的内容流程，恢复未处理的数据。
    1，扫描Process 产生日记文件的根目录，得到日记文件绝对路径，放入到一个List 里面，proFilePaths
	2，遍历proFilePaths的每一个文件，例如如下：
	    1，/opt/logdata/process/p-0/000000000001.log  然后查看 /opt/logdata/receive/r-0/000000000001.log 文件是否存在
		    I，存在：
			    1，读取/opt/logdata/receive/r-0/000000000001.log  和 /opt/logdata/process/p-0/000000000001.log 的内容到一个AllList 里面
				2，经过排序比对，得出未处理的内容diffList
				3，对未处理的内容，提取特征，发送kafka， 发送Kafka 的时候，如果第一条发送失败，则马上退出这一层循环。如果第一条发送成功，则一直发送到diffList 没有内容，发送的时候，同时把日记添加到/opt/logdata/process/p-0/000000000001.log里面
			II，不存在，退出这一层循环，进行下一个循环，即对比下一个文件。
			


2，对处理失败的数据，进行重新发送Kafka，恢复处理失败的数据。
	1，遍历Process 目录中的文件，得到日记文件的一个绝对路径，放入到一个List 里面， proFilePaths
	2，遍历proFilePaths的每一个文件，例如如下：
		1，/opt/logdata/process/p-0/000000000001.log  然后查看 /opt/logdata/receive/r-0/000000000001.log 文件是否存在
			存在：
			  1，读取两个文件的内容到一个List 里面，
			  2，排序，对比，取得处理失败的failedList
			  3，failedList 存储到/opt/logdata/merge/receive/r-0/000000000001.log,删除 /opt/logdata/process/p-0/000000000001.log  和 /opt/logdata/receive/r-0/000000000001.log
			  4, 遍历failedList，提取特征发送Kafka，同时把处理日记发送到/opt/logdata/merge/process/p-0/000000000001.log
			不存在：
			  把/opt/logdata/process/p-0/000000000001.log 文件删除，跳过这层循环。
			  
	3，	遍历/opt/logdata/merge/process/下的所有文件放到一个mergeProFilePaths------->List
	4， 遍历mergeProFilePaths 
			1，得到比如/opt/logdata/merge/process/p-0/000000000001.log ， 和文件/opt/logdata/merge/receive/r-0/000000000001.log 进行对比
			2，把/opt/logdata/merge/receive/r-0/000000000001.log 和 /opt/logdata/merge/receive/r-0/000000000001.log 放到一个totalList  里
			3，对totalList 进行排序，比较，得到diffListV1，然后覆盖/opt/logdata/merge/receive/r-0/000000000001.log 的内容，
				1，diffListV1 没有内容，则跳过，有内容，进行下面的2
				2，遍历diffListV1,把处理的内容发送到Kafka ，同时把日记覆盖掉/opt/logdata/merge/process/p-0/000000000001.log
			4, 对比/opt/logdata/merge/receive/r-0/000000000001.log  和 /opt/logdata/merge/process/p-0/000000000001.log, 如果对应上了，删除这两个文件
			      
	5，结束
```
			  
			  
			  
			  
			  
			  
			  
			  
