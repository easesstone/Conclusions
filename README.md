# 基本信息  

---------------------------------------------------------------------  

|<a href="">Name</a>|<a href="">experience</a>|<a href="">Email</a>|<a href="">WeChat</a>|  
|:----:|:------:|:-----:|:-----:|  
|<a href="">李第亮</a>|<a href="">2年大数据开发经验<a>|<a href="">unnunique@139.com</a> |<a href="">u_n_n_unique</a> |  

---------------------------------------------------------------------  

# 标签

| <a href="">Conclustions</a> | <a href="">使用技巧</a> | <a href="">大数据组件</a> | <a href="">语言学习</a> | <a href="">小情趣小确幸</a> | <a href="">最是留恋处</a>  
|:------------:|:----------:|:---------:|:----------:|:----------:|:----------:|
| [In 2018](AADocs/conclusions-docs/In2018.md) | [Linux](AADocs/skill-docs/linux/index.md) | [spark-thriftserver](AADocs/skill-docs/spark-sql-thriftservft-tiaoyou/index.md)|[scala](AADocs/skill-docs/scala/index.md) | <a href="">你猜我猜不猜</a> | 一往而深矣 |  
| [In 2017](AADocs/conclusions-docs/In2017.md) | [Jenkins](AADocs/skill-docs/jenkins/index.md) | [elasticsearch](AADocs/skill-docs/elasticsearch/index.md) | <a href="">语言学习</a> | <a href="">小情趣小确幸</a> | 桃花笑春风 |  
| [In 2016](AADocs/conclusions-docs/In2016.md) | [Java](AADocs/skill-docs/java/index.md) | [hive](AADocs/skill-docs/hbase/index.md)|<a href="">java</a> | <a href="">你猜我猜不猜</a> | 十里相徘徊 |  
| [ToBeContinue]() | [sql使用技巧](AADocs/skill-docs/sql/index.md) | [hbase](AADocs/skill-docs/hbase/index.md) | 语言学习 | 小情趣小确幸 | 最是留恋处 |  
| [ToBeContinue]() | [tool-use](AADocs/skill-docs/tool-use/index.md) | [资源下载](AADocs/skill-docs/download/index.md)|java | 你猜我猜不猜 | 桃花笑春风 | 

# 我的优势
1，熟练掌握java，有持续集成经验，熟悉代码质量控制。  
2，熟练使用shell 脚本编写自动化流程,开发过大数据集群架构自动化部署脚本。  
3，熟练掌握HBase 数据库，Elasticsearch搜索，有实际项目应用经验。  
4，对spark thriftserver 进行过调优，熟悉spark 开发。  
5，熟练使用phoenix，可以编写phoenix Hive自定义函数。 
 

# java开发定位Trouble技巧
1，定位错误日记  
2，检查代码逻辑  
3，检查配置文件  
4，对代码进行Debug  
5，查看网上关于错误日记的解决办法  
6，解决不了，向上反馈，寻求规避方案  

# 规范代码
1，接口定义，对外接口，对内接口，对外提供统一的接口    
2，方法实现：提取公共实现代码、即工具类，松耦合，单一功能  
3，脚本格式规范，变量名统一命名，放在一处，不要写死  
4，项目模块，模块名定了之后，不要改，涉及到的地方很多，不易于维护  
5，模块分离，集群分离，ETL（Extract-Transform-Load 抽取转换加载）（数据采集集群），计算集群，存储集群，对外服务，负载均衡，成本  
6，避免过于沉重的操作，比如一次性操作很大的数据量，采取分而治之的方法  
7, 項目間的類關係圖，以及項目各個模塊實現的功能，需要大致地進行描述，方便維護和交接  
8，类具有单一职责功能  
## CRUD接口设置
1, 接口的输入结果和输出结果，封装成对象进行处理  
2, 不分多条件和单条件查询，统一拼装成SQL 语句拼接的形式     

# [脚本规范，参见模板](AADocs/skill-docs/linux/linux-jiaoben-guifan.sh)

# maven 工程规范
1，项目的配置文件统一  
2，项目的大架构最好不要改动，可以在里面添加子模块  
3，项目的版本号不要写死  
4，Maven 离线编译 -O  
5，Maven 并行编译 -T  
6，加强对代码，以及文件合入的控制,代码设计，代码测试，代码提交的commit 信息  

# 规范提交代码commint 信息
No.:   
Module_Name:   
Modification_Reason:    
Modification_Content:    
Modification_Person:   
Review_Person:



# 常用快捷键
## Windows
WIN + D：显示桌面。  
Ctrl+W：关闭选项卡  
Alt + F4 关闭当前窗口  
## IDEA 
shift + 上下左右箭头选中内容  
shift + alt + 上下，快速复制一行  
ctr + shift + 上下左右， 在方法间快速切换  
alt + 左右，回到上一次的光标所在的地方  
ctr + d 删除  
alt + 上下，移动一行的位置，或者一段代码块的位置  
ctr + tab 在各个文件间切换  





