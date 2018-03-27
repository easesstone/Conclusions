```
phoenix 下bin 目录需要把hbase-site.xml 拷贝上。

Error: ERROR 6003 (42F03): User defined functions are configured to not be allowed. 
To allow configure phoenix.functions.allowUserDefinedFunctions to true. (state=42F03,code=6003)
java.sql.SQLException: ERROR 6003 (42F03): User defined functions are configured to not be allowed.
 To allow configure phoenix.functions.allowUserDefinedFunctions to true.
	at org.apache.phoenix.exception.SQLExceptionCode$Factory$1.newException(SQLExceptionCode.java:483)
	at org.apache.phoenix.exception.SQLExceptionInfo.buildException(SQLExceptionInfo.java:150)
	at org.apache.phoenix.jdbc.PhoenixStatement.
	throwIfUnallowedUserDefinedFunctions(PhoenixStatement.java:1990)
	at org.apache.phoenix.jdbc.PhoenixStatement.access$800(PhoenixStatement.java:206)
	at org.apache.phoenix.jdbc.PhoenixStatement$
	ExecutableCreateFunctionStatement.compilePlan(PhoenixStatement.java:789)
	at org.apache.phoenix.jdbc.PhoenixStatement$ExecutableCreateFunctionStatement.
	compilePlan(PhoenixStatement.java:779)
	at org.apache.phoenix.jdbc.PhoenixStatement$2.call(PhoenixStatement.java:386)
	at org.apache.phoenix.jdbc.PhoenixStatement$2.call(PhoenixStatement.java:376)
	at org.apache.phoenix.call.CallRunner.run(CallRunner.java:53)
	at org.apache.phoenix.jdbc.PhoenixStatement.executeMutation(PhoenixStatement.java:374)
	at org.apache.phoenix.jdbc.PhoenixStatement.executeMutation(PhoenixStatement.java:363)
	at org.apache.phoenix.jdbc.PhoenixStatement.execute(PhoenixStatement.java:1707)
	at sqlline.Commands.execute(Commands.java:822)
	at sqlline.Commands.sql(Commands.java:732)
	at sqlline.SqlLine.dispatch(SqlLine.java:813)
	at sqlline.SqlLine.begin(SqlLine.java:686)
	at sqlline.SqlLine.start(SqlLine.java:398)
	at sqlline.SqlLine.main(SqlLine.java:291)
0: jdbc:phoenix:172.18.18.135> exit

```