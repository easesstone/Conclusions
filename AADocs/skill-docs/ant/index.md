# 知识点
## 简介
```
1，ant 跨平台的自动化构建工具。
简单的应用，比如自动化构建项目，项目部署。
复杂的应用，比如自动化测试框架，集群部署等。
```
## 例子
```xml
<?xml version="1.0"?>
<project name="sayHello">
    <target name="sayHello">
        <echo message="hello,world!!" />
    </target>
</project>
```
```shell
#对此的说明：
#其执行的方式如下：(如果其构建的xml 文件是build.xml 则，-f 这个指定可以省略。)
ant sayHello
Buildfile: /home/test/build.xml

sayHello:
     [echo] hello,world!!

BUILD SUCCESSFUL
Total time: 0 seconds
ant -f build.xml  sayHello
ant -file build.xml sayHello
ant -buildfile build.xml sayHello.
```
