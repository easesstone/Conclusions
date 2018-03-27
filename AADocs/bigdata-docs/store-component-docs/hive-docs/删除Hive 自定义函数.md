
## 1，清空mysql 中的表格
```

## truncate table wp_comments;

## 清空带有外键的表格

SET FOREIGN_KEY_CHECKS = 0;   
TRUNCATE TABLE TABLE_E;  
SET FOREIGN_KEY_CHECKS = 1;  
```

## 2，删除hdfs 上的jar 包

## 3，集群重启