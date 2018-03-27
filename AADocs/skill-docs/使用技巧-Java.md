## java 断言不要乱用
```java
//assert 断言不要乱用
public class Demo {
    public static byte[] inputPicture(String picPath) {
        File imageFile;
        ByteArrayOutputStream baos = null;
        FileInputStream fis = null;
        byte[] buffer = new byte[1024];
        try {
            imageFile = new File(picPath);
            if (imageFile.exists()) {
                baos = new ByteArrayOutputStream();
                fis = new FileInputStream(imageFile);
                int len;
                while ((len = fis.read(buffer)) > -1) {
                    baos.write(buffer, 0, len);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert baos != null;
        return baos.toByteArray();
    }
}
```

## 可变常量
```java
package util;

import org.junit.Test;

public class SimpleDemoTestSuite {
    final long hello = 123456;
    final Flag flag = new Flag();
    @Test
    public void demoFinal() {
//        hello = 12345;
        System.out.println(flag.isFlag());
        flag.setFlag(true);
        System.out.println(flag.isFlag());
    }
}

class Flag {
    private boolean flag;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "Flag{" +
                "flag=" + flag +
                '}';
    }
}
```

## java 中判断线程池中的所有线程都已经执行完成。
```java
//https://www.zhihu.com/question/52580874
//https://www.cnblogs.com/stonefeng/p/5967451.html
//https://my.oschina.net/nipin/blog/788966?utm_source=debugrun&utm_medium=referral
public class ThreadsIsDone {
    public static void main(String[] args) {
        //创建一个10个线程的线程池
        ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 10,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

        for(int i=0;i<10;i++){
            pool.submit(new Runnable() {
                public void run() {
                    System.out.println("当前线程:"+Thread.currentThread().getName()+",打印随机数:"+ new Random().nextInt(1000));
                }
            });
        }
        System.out.println("pool.getTaskCount():"+pool.getTaskCount());
        System.out.println("pool.getCompletedTaskCount():"+pool.getCompletedTaskCount());
        boolean allThreadsIsDone = pool.getTaskCount()==pool.getCompletedTaskCount();
        System.out.println(allThreadsIsDone);
        if(allThreadsIsDone){
            System.out.println("全部执行完成");
        }
        while (!allThreadsIsDone){
            allThreadsIsDone = pool.getTaskCount()==pool.getCompletedTaskCount();
            if(allThreadsIsDone){
                System.out.println("全部执行完成");
            }
        }
    }
}

```


# CRUD接口设置
### 接口的输入结果和输出结果，封装成对象进行处理。
### 不分多条件和单条件查询，统一拼装成SQL 语句拼接的形式。


## jdbc 中，如下语句会把问号当成是字符串，所以在prepareStatement.setString 等的时候会报错。
String sql = "select * from " + ObjectInfoTable.TABLE_NAME + " where id = ’?‘";


## 记录一次很蠢的问题，properties 文件中的内容，设置了jdbc url 加了双引号：
phoenixJDBCURL="jdbc:phoenix:172.18.18.100,172.18.18.101,172.18.18.102,172.18.18.105,172.18.18.106,172.18.18.107:2181"
```
java.sql.SQLException: No suitable driver found for "jdbc:phoenix:172.18.18.100,172.18.18.101,172.18.18.102,172.18.18.105,172.18.18.106,172.18.18.107:2181"
	at java.sql.DriverManager.getConnection(DriverManager.java:689)
	at java.sql.DriverManager.getConnection(DriverManager.java:270)
	at com.hzgc.service.staticrepo.PhoenixJDBCHelper.initConnection(PhoenixJDBCHelper.java:36)
	at com.hzgc.service.staticrepo.PhoenixJDBCHelper.getPhoenixJdbcConn(PhoenixJDBCHelper.java:19)
	at com.hzgc.service.staticreposuite.StaticJDBCTest.testGetPhoenixConnection(StaticJDBCTest.java:12)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:50)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)
	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
	at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:325)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:78)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:57)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
	at org.junit.runner.JUnitCore.run(JUnitCore.java:137)
	at com.intellij.junit4.JUnit4IdeaTestRunner.startRunnerWithArgs(JUnit4IdeaTestRunner.java:68)
	at com.intellij.rt.execution.junit.IdeaTestRunner$Repeater.startRunnerWithArgs(IdeaTestRunner.java:51)
	at com.intellij.rt.execution.junit.JUnitStarter.prepareStreamsAndStart(JUnitStarter.java:242)
	at com.intellij.rt.execution.junit.JUnitStarter.main(JUnitStarter.java:70)
```


## maven  test 中，用到的resources 文件是test 下面的resource 文件。。。。。

## java Main入口参数验证，或者其他if 分支处理的时候的情况
```
技巧：
先把符合条件的情况列举出来，
然后，比如有四个添加符合情况，a1,a2,a3,a4
则其反面是
!(a1 || a2 || a3 || a4)

此条件下用于参数验证
#showRunTime
set -x
tmp=$(echo “$2” | grep ^[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]$) 
if [[ !(($# == 1 && $1 == mid_table) || ($# == 2 && $1 == person_table && $2 == now) || \
($# == 2 && $1 == person_table && $2 == before) || ($# == 2 && $1 == person_table && $2 == now)) ]];then
    #  showUsage
    echo "caonima"
fi
set +x

```