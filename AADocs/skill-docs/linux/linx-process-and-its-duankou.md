### 1，根据进程号查找端口号《--》根据端口号查看进程号《--》根据进程号查看进程名
```
我们知道， 根据ps -aux | grep xxx就是很快实现进程名和进程号的互查， 
所以我们只说进程号pid就行。 如下示例中， 进程pid常驻。
1.  根据进程pid查端口：
     lsof -i | grep pid
2.  根据端口port查进程（某次面试还考过）：
    lsof  -i:port     
3. 根据进程pid查端口：
   netstat -nap | grep pid
4.  根据端口port查进程
   netstat -nap | grep port
```