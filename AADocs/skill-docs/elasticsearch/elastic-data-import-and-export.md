## 数据迁移工具
[工具github 网址](https://github.com/medcl/esm)  
[适合5.*版本的elastic工具](https://github.com/medcl/esm/releases/download/v0.3.4/linux64.tar.gz)  
【用法说明】  
从一个集群向另一个集群迁移：
 ./esm   -s http://172.18.18.103:9200 -d http://172.18.18.100:9200 -x dynamic  -y dynamic -w 5 -b=100
 