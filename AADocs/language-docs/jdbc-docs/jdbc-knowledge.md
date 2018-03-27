# JDBC （JAVA DATABASE CONNECTION）
## JDBC 一般的过程
1，创建连接。  
2，声明sql 语句。  
3，创建statement 对象。  
4，执行excute 方法，把sql语句发送到数据库服务端执行。  
5，执行sql数据集返得到的数据，返回客户端。  
6，关闭连接对象。  

## JDBC 三个重要对象（Statement, PreparedStatement, CallableStatement）
1,三者都是接口。  
2,继承关系上，Statement 继承与Wrapper, PrepareStatement 继承于Statement, Callable继承于PreparedStatement.  
3,区别：  
  a. Statement普通的不带参数的sql;批量更细和删除。(效率上高于PreparedStatement)  
  b. PreparedStatement:   
  　可变参数的SQL,编译一次,执行多次,效率高;   
  　安全性好，有效防止Sql注入等问题;   
  　支持批量更新,批量删除;   
  c. CallableStatement:   
　　继承自PreparedStatement,支持带参数的SQL操作;   
　　支持调用存储过程,提供了对输出和输入/输出参数(INOUT)的支持;  
 用法：
 ```
Statement用法:  
String sql = "select seq_orderdetailid.nextval as test dual";  
Statement stat1=conn.createStatement();  
ResultSet rs1 = stat1.executeQuery(sql);  
if ( rs1.next() ) {  
    id = rs1.getLong(1);  
}  
   
INOUT参数使用：  
CallableStatement cstmt = conn.prepareCall("{call revise_total(?)}");  
cstmt.setByte(1, 25);  
cstmt.registerOutParameter(1, java.sql.Types.TINYINT);  
cstmt.executeUpdate();  
byte x = cstmt.getByte(1);  
   
Statement的Batch使用:  
Statement stmt  = conn.createStatement();  
String sql = null;  
for(int i =0;i<20;i++){  
    sql = "insert into test(id,name)values("+i+","+i+"_name)";  
    stmt.addBatch(sql);  
}  
stmt.executeBatch();  
   
PreparedStatement的Batch使用:  
PreparedStatement pstmt  = con.prepareStatement("UPDATE EMPLOYEES  SET SALARY = ? WHERE ID =?");  
for(int i =0;i<length;i++){  
    pstmt.setBigDecimal(1, param1[i]);  
    pstmt.setInt(2, param2[i]);  
    pstmt.addBatch();  
}  
pstmt.executeBatch();  
   
PreparedStatement用法:  
PreparedStatement pstmt  = con.prepareStatement("UPDATE EMPLOYEES  SET SALARY = ? WHERE ID =?");  
pstmt.setBigDecimal(1, 153.00);  
pstmt.setInt(2, 1102);  
pstmt. executeUpdate()
```
