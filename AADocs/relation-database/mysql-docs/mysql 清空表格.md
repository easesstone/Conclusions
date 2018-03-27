## truncate table wp_comments;

## 清空带有外键的表格

SET FOREIGN_KEY_CHECKS = 0;   
TRUNCATE TABLE TABLE_E;  
SET FOREIGN_KEY_CHECKS = 1;  