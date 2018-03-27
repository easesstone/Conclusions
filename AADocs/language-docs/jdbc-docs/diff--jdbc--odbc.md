```
在学习J2EE的JDBC过程中，刚见到JDBC就马上联想到了ODBC，
而且我们可以肯定他们之间有必然的关系。开始学它的时候还是觉得有点晕，
于是就查了很多资料，与比较熟悉的ODBC进行了比较。
    先各自简单介绍一下ODBC和JDBC。
    ODBC（Open Database Connectivity）是一组对数据库访问的标准API，
    这些API通过SQL来完成大部分任务，而且它本身也支持SQL语言，支持用户发来的SQL
    。ODBC定义了访问数据库API的一组规范，这些API独立于形色各异的DBMS和编程语言。
    也就是说，一个基于ODBC的应用程序，对数据库的操作不依赖任何DBMS，
    不直接与DBMS打交道，所有的数据库操作由对应的DBMS的ODBC驱动程序完成。
    不论是SQL Server、Access还是Oracle数据库，均可用ODBC API进行访问。
    由此可见，ODBC的最大优点是能以统一的方式处理所有的数据库。
    JDBC（JavaDatabase Connectivity）是Java与数据库的接口规范，
    JDBC定义了一个支持标准SQL功能的通用低层API，它由Java 语言编写的类和接口组成
    ，旨在让各数据库开发商为Java程序员提供标准的数据库API。
    JDBC API定义了若干Java中的类，表示数据库连接、SQL指令、
    结果集、数据库元数据等。它允许Java程序员发送SQL指令并处理结果。
 
    其实JDBC和ODBC总的来说还是有更多的共同点：
    比如，JDBC与ODBC都是基于X/Open的SQL调用级接口；
         从结构上来讲，JDBC的总体结构类似于ODBC,都有四个组件：
         应用程序、驱动程序管理器、驱动程序和数据源,工作原 理亦大体相同；  
    在内容交互方面，JDBC保持了ODBC的基本特性,也独立于特定数据库.
     而且都不是直接与数据库交互，而是通过驱动程序管理器。      
        他们二者之间的区别：
 我们知道，ODBC几乎能在所有平台上连接几乎所有的数据库。为什么 Java 不使用 ODBC？
 答案是：Java 可以使用 ODBC，但最好是以JDBC-ODBC桥的形式使用（
 Java连接总体分为Java直连和JDBC-ODBC桥两种形式）。
 那为什么还需要 JDBC？
 因为ODBC 不适合直接在 Java 中使用，因为它使用 C 语言接口。从Java 调用本地
  C代码在安全性、实现、坚固性和程序的自动移植性方面都有许多缺点。
  从 ODBC C API 到 Java API 的字面翻译是不可取的。例如，Java 没有指针，
  而 ODBC 却对指针用得很广泛（包括很容易出错的指针"void *"）。
 另外，ODBC 比较复杂，而JDBC 尽量保证简单功能的简便性，同时在必要时允许使用高级功能
 。如果使用ODBC，就必须手动地将 ODBC 驱动程序管理器和驱动程序安装在每台客户机上。
 如果完全用 Java 编写 JDBC 驱动程序则 JDBC代码在所有 Java 平台上
 （从网络计算机到大型机）都可以自 动安装、移植并保证安全性。
 总之，JDBC 在很大程度上是借鉴了ODBC的，从他的基础上发展而来。
 JDBC 保留了 ODBC 的基本设计特征，因此，熟悉 ODBC 的程序员将发现 JDBC 很容易使用。
 它们之间最大的区别在于：JDBC 以 Java 风格与优点为基础并进行优化，因此更加易于使用。
```
