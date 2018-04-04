```bash
#!/bin/bash
## 1.sh
#### $$ shell进程的PID存储在一个特殊的变量‘$$’中。PPID存储子shell父进程的ID（也就是主shell）。UID存储了执行这个脚本的当前用户ID。 
#### source /juedui/demo.sh source ./demo.sh 和 .  ./demo.sh . /juedui/demo.sh 等同。和父shell 公用环境变量，相互间有影响。
#### sh 和bash 等同，不管相对还是绝对路径。 子进程改变环境变量，对父进程无影响。
#### exec 终止父进程 sh。
export A=B  
echo "before:PID for 1.sh  pid is:$$, \$A is $A"  
case $1 in  
    exec)  
        echo "using exec..., 脚本需要执行权限,终止父进程。即1.sh"  
        exec ./2.sh ;;  
    diange)  
        echo "using diange, 处于同一个pid, 2.sh 中export 声明的变量会影响1.sh 中的变量"  
        . ./2.sh ;;  
    shdemo)  
        echo "using sh， 另起一个pid,相互间的变量互不影响。"
        sh 2.sh;;
    dianlian)
        echo "dian lian, 需要有执行权限,同 shdemo"
        ./2.sh;;
    jueduilujing)
        echo "jueduilujing，需要有执行权限,同shdemo"
        /home/test/2.sh;;
    dianjueduilujing)
        echo "dian jueduilujing ./ 的意思是当前目录，以下执行会失败"
        .//home/test/2.sh;;
    sourcejuedui)
        echo "source juedui..., source 跟上绝对路经，影响父进程"
        source /home/test/2.sh;;
    sourcexiangdui)
        echo "source xiangdui..., source 跟上相对路径， 错误写法，source 2.sh  即使在2.sh 所在目录，影响父进程的值"
        source ./2.sh;;
    shjuedui)
        "sh juedui, 同shdemo"
        sh /home/test/2.sh;;
esac  
echo "after: PID for 1.sh is $$, \$A is $A"


## 2.sh
#!/bin/bash  
echo "PID for 2.sh: $$, 2.sh get \$A=$A from 1.sh"  
export A=C  
echo "after change in A'value to C ,2.sh: \$A is $A"

```
