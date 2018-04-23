# 知识点
## 简介
```
1，ant 跨平台的自动化构建工具。
简单的应用，比如自动化构建项目，项目部署。
复杂的应用，比如自动化测试框架，集群部署等。

```
## 核心概念
XML， 整个自动化流程的定义，定义在XML 文件中。  
语法：  
每个xml 构建文件包含一个project  
每个project 包含一个多个target
target 间可以进行相互依赖
target 里面可以包含多个task


## 例子
把自动化的流程看成是一个项目。  
```xml
<?xml version="1.0"?>
<project name="sayHello">
    <target name="sayHello">
        <echo message="hello,world!!" />
    </target>
</project>
```

命令使用：
```shell
#对此的说明：
#其执行的方式如下：(如果其构建的xml 文件是build.xml 则，-f 这个指定可以省略。)
ant sayHello
Buildfile: /home/test/build.xml

sayHello:
     [echo] hello,world!!

BUILD SUCCESSFUL
Total time: 0 seconds

其他的运行方式：

ant -f build.xml  sayHello
ant -file build.xml sayHello
ant -buildfile build.xml sayHello.
```


## ant 常用的一些task。
其余的参考这个链接：http://ant.apache.org/manual/index.html
```
chmod   
copy
echo 
java
javac
move
mkdir
scp 
sshexec
delete
```


## 如下展示一个有依赖关系的project。
build.xml
```xml
<?xml version="1.0"?>
<project name="sayHello" default="sayHello">
    <target name="sayHello01">
        <echo message="caonimaenha" />
    </target>
    <target name="sayHello" depends="sayHello01">
        <echo message="hello,world!!" />
    </target>
</project>
```
当执行ant -f build.xml sayHello 的时候，会先执行sayHello01, 如下：
```
[root@s114 home]# ant -f demo02.xml 
Buildfile: /home/demo02.xml

sayHello01:
     [echo] caonimaenha

sayHello:
     [echo] hello,world!!

BUILD SUCCESSFUL
Total time: 0 seconds

```

## 如下展示其中的一种方法，向ant 脚本进行传参。
注意如果参数多，不建议用这种方法，建议使用properties 配置文件的方法。  
```
<project name="demo01">
    <target name="target01" >
        <echo message="${host}"/>
    </target>
</project>
```
外部的参数传递如下：
-Dname=value  
命令如下：  
ant -f demo01.xml  -Dhost=100 target01   
执行效果：
```
ant -f demo01.xml  -Dhost=100 target01
Buildfile: /home/demo01.xml

target01:
     [echo] 100

BUILD SUCCESSFUL
```
