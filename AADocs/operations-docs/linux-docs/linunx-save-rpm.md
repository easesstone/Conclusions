```
方法一:yum

yum命令本身就可以用来下载一个RPM包，标准的yum命令提供了--downloadonly(只
下载)的选项来达到这个目的。

$ sudo yum install --downloadonly <package-name> 
默认情况下，一个下载的RPM包会保存在下面的目录中:

/var/cache/yum/x86_64/[centos/fedora-version]/[repository]/packages 
以上的[repository]表示下载包的来源仓库的名称(例如：base、fedora、updates)

如果你想要将一个包下载到一个指定的目录(如/tmp)：

$ sudo yum install --downloadonly --downloaddir=/tmp <package-name> 
注意，如果下载的包包含了任何没有满足的依赖关系，yum将会把所有的依赖关系包下载，
但是都不会被安装。

另外一个重要的事情是，在CentOS/RHEL 6或更早期的版本中，你需要安装一个单独y
um插件(名称为 yum-plugin-downloadonly)才能使用--downloadonly命令选项：

$ sudo yum install yum-plugin-downloadonly 
如果没有该插件，你会在使用yum时得到以下错误：

Command line error: no such option: --downloadonly




方法二: Yumdownloader

另外一个下载RPM包的方法就是通过一个专门的包下载工具--yumdownloader。
 这个工具是yum工具包(包含了用来进行yum包管理的帮助工具套件)的子集。

$ sudo yum install yum-utils 
下载一个RPM包：

$ sudo yumdownloader <package-name> 
下载的包会被保存在当前目录中。你需要使用root权限，因为yumdownloader会
在下载过程中更新包索引文件。与yum命令不同的是，任何依赖包不会被下载。

下载lsof示例：

yumdownloader lsof --resolve --destdir=/data/mydepot/　　#resolve下载依赖











```