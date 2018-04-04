# 使用技巧  
1，hive 数据迁移,从一张表格向另一张表格进行插入数据  
  ```
  insert into table mid_table select * from person_table where date='2018-03-20'
   and ipcid='DS-2CD2T20FD-I320160122AACH571485690';
  ```
  