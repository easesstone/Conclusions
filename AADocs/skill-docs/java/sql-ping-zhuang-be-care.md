## jdbc 中，如下语句会把问号当成是字符串，所以在prepareStatement.setString 等的时候会报错。
String sql = "select * from " + ObjectInfoTable.TABLE_NAME + " where id = ’?‘";
