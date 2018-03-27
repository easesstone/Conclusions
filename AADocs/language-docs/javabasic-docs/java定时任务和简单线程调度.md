```
http://blog.csdn.net/guozebo/article/details/51090612
```

## 定时任务最简单的实现。
```
在线程里面（不管是实现Runable 还是继承Thread），
只需要让线程循环执行就可以了。
采用Thread.sleep 的方法。
注意这种方法，如果Thread的run 方法有异常，则线程会被终止掉。
```

## 第二中方法，采用Timer 定时任务类，java.util 中的工具类。
```
Timer  的简单理解：
可以点击进去看 源码：
这个是一个线程的工具，用来在后台线程中安执行将来需要执行的任务。
可以是执行一次，也可以是周期性地重复人任务执行。

这个是一个后台线程：
是线程安全的，也就是说，多个线程可以共享一个Timmer 而不必枷锁。

java 5后引入了一个java.util.concurrent 的包，包里面有一个
ScheduleredThreadPoolExecutor.用于替代Timer...

```

## 第三种方法ScheduledThreadPoolExecutor
```
它是接口ScheduledExecutorService的子类，接收Runable方法作为
子类。。


JDK 官网推荐用ScheduledThreadPoolExecutor 替代Timmer
可以允许线程执行带有返回值，更多灵活的调度方法



主要的调度方法：
/** 
 * 调度一个task，经过delay(时间单位由参数unit决定)后开始进行调度，仅仅调度一次 
 */  
public ScheduledFuture<?> schedule(Runnable command,  
                                       long delay, TimeUnit unit);  
  
/** 
 * 同上，支持参数不一样 
 */  
public <V> ScheduledFuture<V> schedule(Callable<V> callable,  
                                           long delay, TimeUnit unit);  
  
/** 
 * 周期性调度任务，在delay后开始调度，适合执行时间比“间隔”短的任务 
 * 并且任务开始时间的间隔为period，即“固定间隔”执行。 
 * 如果任务执行的时间比period长的话，会导致该任务延迟执行，不会同时执行！ 
 * 如果任务执行过程抛出异常，后续不会再执行该任务！ 
 */  
public ScheduledFuture<?> scheduleAtFixedRate(Runnable command,  
                        long initialDelay ,long period ,TimeUnit unit);  
  
/** 
 * Timer所没有的“特色”方法，称为“固定延迟(delay)”调度，适合执行时间比“间隔”长的任务 
 * 在initialDelay后开始调度该任务 
 * 随后，在每一次执行终止和下一次执行开始之间都存在给定的延迟period 
 * 即下一次任务开始的时间为：上一次任务结束时间（而不是开始时间） + delay时间 
 * 如果任务执行过程抛出异常，后续不会再执行该任务！ 
 */  
public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command,  
                        long initialDelay ,long delay ,TimeUnit unit);  




```