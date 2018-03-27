# 基础
```
简介：
python 语法：
以缩进的形式进行编码
注释以# 开头，类似shell 脚本：
# print absolute value of integer:
a = 100
if a >= 0:
    print a
else: 
    print -a
当一行以冒号结尾，缩进的内容是语句块，
一般缩进4格空格。
python 对大小写敏感

```
## 简单的输出与输入
```
输出，
>>> print 'hello'
hello
>>> print 'hello'+'niya'
helloniya
>>> print 'hello',100+200
hello 300
>>> print 'hello','caodan'
hello caodan


输入
>>> name = raw_input()
Michael
>>> name
'Michael'
>>> print name
Michael
>>> name = raw_input('please enter your name:')
please enter your name:Michael
>>> print 'hello,'+name
hello,Michael
>>> 



birth = raw_input('birth: ')
if birth < 2000:
    print '00前'
else:
    print '00后'
 
输入的是字符串，结果可能和预期不一致

birth = int(raw_input('birth: '))
可以用下面的内容进行转型

```
## 数据类型和变量
```
高级数据类型允许您在单个语句中表示复杂的操作;
语句分组是通过缩进来完成的，而不是开始和结束括号;
没有变量或参数声明是必要的

1， 数据类型与变量
1，1 数据类型
整型：1，100，-100，0
浮点数： 1.23.1.24.-9.91，12.3e8(12.3x10的8次方).0.000012 (1.2e-5)
字符串： "", 或者'' 需要用单引号和双引号引起来    
    （''' '''）用来保持里面的格式正确，注意有些内容需要进行转义
    print r'\\\t\\' 和 print '\\\t\\' 的区别
    字符串是不可变对象
Bool 值; True,False
空值： None

1.2 bool 值的逻辑运算
True and True
True or False
not True



1.2 变量：
命名规则：
大小写英文，数字和下划线_ 组成，而且不能以数字开头

注意顺序执行的结构

x=1
动态语言，自动映射数据类型

a = 123 
a = '123'
可以多次复制。


1.3 除法
10/3 = 3.3333333333
9/3 = 3.0
9//3 = 3


1.4 常量
一般用大写字母表示，

```
## 2,字符编码(简单理解即可)
```
ASCII
GB2312

Unicode
以Unicode表示的字符串用u'...'表示，
u'中文'


ord('中') 显示字符的整数表示
chr(123)显示整数表示的字符

UTF-8
Unicode 转换为utf-8
u'中文'.encode('utf-8')

由于历史遗留问题，Python 2.x版本虽然支持Unicode，但在语法上需要
'xxx'和u'xxx'两种字符串表示方式。

Python当然也支持其他编码方式，比如把Unicode编码成GB2312：

>>> u'中文'.encode('gb2312')
'\xd6\xd0\xce\xc4'
但这种方式纯属自找麻烦，如果没有特殊业务要求，请牢记仅使用Unicode
和UTF-8这两种编码方式。

在Python 3.x版本中，把'xxx'和u'xxx'统一成Unicode编码，即写不写前缀u都是一样的，
而以字节形式表示的字符串则必须加上b前缀：b'xxx'。

格式化字符串的时候，可以用Python的交互式命令行测试，方便快捷
```

## 3，格式化输出
```
类似C语言
%运算符就是用来格式化字符串的。在字符串内部，%s表示用字符串替换，%d表示用整数替换，
有几个%?占位符，后面就跟几个变量或者值，顺序要对应好。如果只有一个%?，括号可以省略
%d	整数
%f	浮点数
%s	字符串
%x	十六进制整数



>>> name='Steve.Li'
>>> 'hello,%s' % name
'hello,Steve.Li'
>>> pay=100000
>>> 'hello, %s, your pay is %d...' % (name, pay)
'hello, Steve.Li, your pay is 100000...'

保留小数位数和整数位数，以及是否补0
>>> '%2d-%02d' % (3, 1)
' 3-01'
>>> '%.2f' % 3.1415926
'3.14'

>>> 'growth rate: %d %%' % 7
'growth rate: 7 %'

```

## List 和Tuple
```python
List

>>> classmates = ['Machael', 'Bob', 'Tracy']  ## 列表，相同元素
>>> classmates
['Machael', 'Bob', 'Tracy']
>>> len(classmates)                     ## 列表长度
3
>>> classmates[0]                       ## 取得列表中的某个数据，下表从0开始，类似数组
'Machael'
>>> classmates[1]
'Bob'
>>> classmates[2]
'Tracy'
>>> classmates[3]                          ## 下表不可以越界
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
IndexError: list index out of range
>>> classmates[-1]                        ## 取最后一个元素
'Tracy' 
>>> classmates[-2]                       ## 取得倒数第二个
'Bob'
>>> classmates[-3]                        ## 倒数第三个     
'Machael'
>>> classmates[-4]                       ## 越界
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
IndexError: list index out of range
>>> classmates.append('Admin')             ## 在最后添加元素
>>> classmates
['Machael', 'Bob', 'Tracy', 'Admin']
>>> classmates.insert(1, 'Jack')               ## 在1 的位置插入一个元素
>>> classmates
['Machael', 'Jack', 'Bob', 'Tracy', 'Admin']
>>> classmates.pop()                      ## 删除最后一个元素
'Admin'
>>> classmates                    
['Machael', 'Jack', 'Bob', 'Tracy']
>>> classmates.pop(1)                        ## 删除某个元素
'Jack'
>>> classmates
['Machael', 'Bob', 'Tracy']
>>> classmates[1]='Sarah'                     ## 替换摸个元素
>>> classmates
['Machael', 'Sarah', 'Tracy']              
>>> L = ['Apple', 123, True]                 ## 数组里的内容，可以不是所有的类型都一致
>>> L
['Apple', 123, True]
>>> s = ['pythone', 'java', ['asp', 'php'], 'scheme']         ## 类似二维数组
>>> s
['pythone', 'java', ['asp', 'php'], 'scheme']
>>> len(s)
4
>>> p = ['asp', 'php']
>>> s = ['python', 'java', p, 'schema']
>>> s
['python', 'java', ['asp', 'php'], 'schema']
>>> p[1]
'php'
>>> s[2][1]
'php'
>>> L = []                      ## 空列表
>>> len(L)
0
>>> 

tuple 元组
只可读的List,
表示如下，没有pop(),insert(1, 'a'), classmates[0]='b' 的操作,只可以获取
classmates = ('Machael', 'Bob', 'Tracy')
>>> classmates = ('Machael', 'Bob', 'Tracy')
>>> classmates
('Machael', 'Bob', 'Tracy')
>>> classmates.pop()
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
AttributeError: 'tuple' object has no attribute 'pop'
>>> classmates(0)
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
TypeError: 'tuple' object is not callable
>>> classmates[0]
'Machael'
>>> classmates[0]='nima'
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
TypeError: 'tuple' object does not support item assignment
>>> classmates[0]='nima'
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
TypeError: 'tuple' object does not support item assignment
>>> 
>>> classmates.insert(0,'nima')
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
AttributeError: 'tuple' object has no attribute 'insert'
>>> 


>>> t=(1,)                              ## 一个元素的tuple
>>> t[0]=5
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
TypeError: 'tuple' object does not support item assignment
>>> t[1]
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
IndexError: tuple index out of range
>>> t[0]
1
>>> t=(1)                            ## 数学括号，表示整数1
>>> t[0]
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
TypeError: 'int' object has no attribute '__getitem__'
>>> t
1

>>> t = ()   ## 表示整数


重点，可变tuple
>>> t = ('a', 'b', ['A', 'B'])
>>> t[2][0] = 'X'
>>> t[2][1] = 'Y'
>>> t
('a', 'b', ['X', 'Y']

Tuple 的不变值得是地址不变
```

## 条件判断和循环
```
if 自带break 功能，遇到正确，即退出


if <条件判断1>:
    <执行1>
elif <条件判断2>:
    <执行2>
elif <条件判断3>:
    <执行3>
else:
    <执行4>
    
 age = 3
 if age >= 18:
     print 'your age is', age
     print 'adult'
 else:
     print 'your age is', age
     print 'teenager'   
     
>>> age=20
>>> if age >= 18:
...     print 'your age is', age
...     print 'adult'
... 
your age is 20
adult

if语句执行有个特点，它是从上往下判断，如果在某个判断上是True，把该判断对应的语句执行后，就忽略掉剩下的elif和else，所以，请测试并解释为什么下面的程序打印的是teenager：

age = 20
if age >= 6:
    print 'teenager'
elif age >= 18:
    print 'adult'
else:
    print 'kid'
    
if x:
    print 'True'
只要x是非零数值、非空字符串、非空list等，就判断为True，否则为False。
（0，'', None,[],()） -------> False

names = ['Michael', 'Bob', 'Tracy']
for name in names:
    print name
    
sum = 0
for x in [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]:
    sum = sum + x
print sum


>>> range(5)
[0, 1, 2, 3, 4]
>>> sum = 0
>>> for x in range(101)
  File "<stdin>", line 1
    for x in range(101)
                      ^
SyntaxError: invalid syntax
>>> for x in range(101):
...     sum = sum + x
... 
>>> print sum 
5050
>>> 


while 循环
>>> sum = 0
>>> n = 99
>>> while n > 0:
...     sum = sum + n
...     n = n - 2
... 
>>> print sum 
2500
>>> 

```

## 字典dict（K-V） 和Set
```
dict 是无序的，无序的数据，和key 存放的顺序无关，根据Key 来寻找Value 
所以Key 不可以变。

>>> d = {'Michael': 95, 'Bob': 75, 'Tracy': 85}
>>> d
{'Bob': 75, 'Michael': 95, 'Tracy': 85}
>>> d['Tracy']
85
>>> 

>>> d['hello'] = 99  ## 添加一个元素
>>> d
{'Bob': 75, 'Michael': 95, 'Tracy': 85, 'hello': 99}
>>> d['Nima']      ## 当元素不存在，会报错
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
KeyError: 'Nima'
>>> 'Nima' in d     ## 判断是否包含这个Key
False
二是通过dict提供的get方法，如果key不存在，可以返回None，或者自己指定的value：
>>> d.get('Thomas')    ## 不存在返回None
>>> d.get('Thomas', -1)    ## 指定不存在的时候返回的值为-1
-1

>>> d
{'Bob': 75, 'Michael': 95, 'Tracy': 85, 'hello': 99}  
>>> d.pop('hello')          ## 删除一个元素
99
>>> d
{'Bob': 75, 'Michael': 95, 'Tracy': 85}


和list比较，dict有以下几个特点：

查找和插入的速度极快，不会随着key的增加而增加；
需要占用大量的内存，内存浪费多。
而list相反：

查找和插入的时间随着元素的增加而增加；
占用空间小，浪费内存很少。

所以，dict是用空间来换取时间的一种方法



set
set 集合（数学含义上的set， 没有重复的数组）
看成数学意义上的无序和无重复元素的集合，
里面的对象跟dict 的Key一样不可变


>>> s=set([1,2,3,4,5,6,7,5,6,4,5,5])
>>> s
set([1, 2, 3, 4, 5, 6, 7])
>>> s.add(10)
>>> s.add('10')
>>> s
set([1, 2, 3, 4, 5, 6, 7, 10, '10'])
>>> s.remove('10')
>>> s
set([1, 2, 3, 4, 5, 6, 7, 10])
>>> s=set([1,2,3])
>>> s1=set([1,2,3])
>>> s2=set([2,3,4])
>>> s1 & s2
set([2, 3])
>>> s1 | s3
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
NameError: name 's3' is not defined
>>> s1 | s2
set([1, 2, 3, 4])
>>> 

不可变对象
字符串是不可变对象，
>>> a = 'abc'
>>> b = a.replace('a', 'A')
>>> b
'Abc'
>>> a
'abc'

replace 只是新创建了一个字符串返回

可变对象
>>> a = ['c', 'b', 'a']
>>> a.sort()
>>> a
['a', 'b', 'c']
```

# 函数
## 函数调用
```
abs(100)  绝对值----- 正数
com(2,1)
>>> abs(0)
0
>>> abs(-1)
1
>>> abs(-100)
100
>>> abs(-11.09)
11.09
>>> cmp(1,2)
-1
>>> cmp(1,3)
-1
>>> cmp(1,4)
-1
>>> cmp(3,1)
1
>>> cmp(4,1)
1
>>> cmp(1,1)
0
>>> cmp('1',1)
1
>>> cmp('1','1')
0
>>> cmp('123','122')
1
>>> 


函数别名
>>> a = abs # 变量a指向abs函数
>>> a(-1) # 所以也可以通过a调用abs函数
1
```
## 类型转换
```
>>> int('123')
123
>>> int(12.34)
12
>>> float('12.34')
12.34
>>> str(1.23)
'1.23'
>>> unicode(100)
u'100'
>>> bool(1)
True
>>> bool('')
False
```
## 函数定义
```
定义一个函数要使用def语句，依次写出函数名、括号、括号中的参数和冒号:，
然后，在缩进块中编写函数体，函数的返回值用return语句返回。

由于函数也是一个对象，而且函数对象可以被赋值给变量，
所以，通过变量也能调用该函数。


def my_abs(x):
    if x >= 0:
        return x
    else:
        return -x
        
空函数
def nop():
    pass
pass 相当于占位符
还没有想好函数该怎么实现。

if age >= 18:
    pass

def my_abs(x):
    if not isinstance(x, (int, float)):  # 如果x 不是float 或者整数
        raise TypeError('bad operand type')
    if x >= 0:
        return x
    else:
        return -x

多个返回值(tuple )

import math

def move(x, y, step, angle=0):
    nx = x + step * math.cos(angle)
    ny = y - step * math.sin(angle)
    return nx, ny

>>>x, y = move(100, 100, 60, math.pi / 6)
>>>print x, y
151.961524227 70.0

>>>r = move(100, 100, 60, math.pi / 6)
>>>r
(151.96152422706632, 70.0)
```

## 函数参数
```
必选参数，默认参数、可变参数和关键字参数

1，默认参数
一是必选参数在前，默认参数在后，否则Python的解释器会报错（思考一下为什么
默认参数不能放在必选参数前面）；

二是如何设置默认参数。


def power(x, n=2):
    s = 1
    while n > 0:
        n = n - 1
        s = s * x
    return s
处理x的平方，三次方，四次方

def enroll(name, gender, age=6, city='Beijing'):
    print 'name:', name
    print 'gender:', gender
    print 'age:', age
    print 'city:', city
    
默认参数，减少默写情况下的输入。
enroll('Bob', 'M', 7)
enroll('Adam', 'M', city='Tianjin')

有多个默认参数时，调用的时候，既可以按顺序提供默认参数，比如调用enroll('Bob', 'M', 7)，意思是，
除了name，gender这两个参数外，最后1个参数应用在参数age上，city参数由于没有提供，仍然使用默认值。

也可以不按顺序提供部分默认参数。当不按顺序提供部分默认参数时，需要把参数名写上。
比如调用enroll('Adam', 'M', city='Tianjin')，意思是，city参数用传进去的值，
其他默认参数继续使用默认值。

可变参数
def calc(numbers):
    sum = 0
    for n in numbers:
        sum = sum + n * n
    return sum
    
def calc(*numbers):
    sum = 0
    for n in numbers:
        sum = sum + n * n
    return sum

这种写法当然是可行的，问题是太繁琐，
所以Python允许你在list或tuple前面加一个*号，
把list或tuple的元素变成可变参数传进去：

>>> nums = [1, 2, 3]
>>> calc(*nums)
14


关键字参数：
可变参数允许你传入0个或任意个参数，这些可变参数在函数调用时自动组装为一个tuple。

而关键字参数允许你传入0个或任意个含参数名的参数，这些关键字参数在函数内部自动
组装为一个dict。请看示例：


>>> def person(name, age, **kw):
...     print 'name:', name, 'age:',age,'other:', kw
... 
>>> person('Michael', 30)
name: Michael age: 30 other: {}
>>> person('Bob', 35, city='Bejing')
name: Bob age: 35 other: {'city': 'Bejing'}
>>> person('Admin', 45, gender='M', job='Engineer')
name: Admin age: 45 other: {'gender': 'M', 'job': 'Engineer'}

>>> kw = {'city': 'Beijing', 'job': 'Engineer'}
>>> person('Jack', 24, city=kw['city'], job=kw['job'])
name: Jack age: 24 other: {'city': 'Beijing', 'job': 'Engineer'}
当然，上面复杂的调用可以用简化的写法：

>>> kw = {'city': 'Beijing', 'job': 'Engineer'}
>>> person('Jack', 24, **kw)
name: Jack age: 24 other: {'city': 'Beijing', 'job': 'Engineer'}


参数组合

```

## 递归函数
```
函数递归，略。
```
# 集合的高级操作
## 切片，取list 的技巧
```
取到列表中的一段范围内的数据：

>>> L=['Michael', 'Sarnh', 'Tracy', 'Bob', 'Jack']
>>> L
['Michael', 'Sarnh', 'Tracy', 'Bob', 'Jack']
>>> [L[0], L[1], L[1]]
['Michael', 'Sarnh', 'Sarnh']
>>> r = []
>>> n=3
>>> for i in range(3):
...     r.append(L[i])
... 
>>> r
['Michael', 'Sarnh', 'Tracy']
>>> 
>>> L[0:3]
['Michael', 'Sarnh', 'Tracy']
>>> L[:3]
['Michael', 'Sarnh', 'Tracy']
>>> L[-2:]                      ##注意区分L[-2:-1]
['Bob', 'Jack']
>>> L
['Michael', 'Sarnh', 'Tracy', 'Bob', 'Jack']
>>> L[-2:-1]
['Bob']


>>> L=range(100)
>>> L
[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17,
 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 
 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 
 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68,
  69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 
  86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99]
>>> L[:10]
[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
>>> L[-10:]
[90, 91, 92, 93, 94, 95, 96, 97, 98, 99]
>>> L[10:20]
[10, 11, 12, 13, 14, 15, 16, 17, 18, 19]
>>> L[:10:2]       # 0 到9 下标内，每两个取一个
[0, 2, 4, 6, 8]
>>> L[:10:3]        # 0 到 9 下标内， 每3个去一个。
[0, 3, 6, 9]
>>> L[::5]    ## 所有元素，每5个取一个
[0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95]
>>> L[:]    ## 复制一个列表
[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 
21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 
40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 
59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 
78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99]
>>> 

tuple 也可以进行以上操作
字符串也可以看成是列表：



```
## 迭代
```
dict 的排序
>>> d={'a': 1, 'b': 2, 'c': 3}
>>> d
{'a': 1, 'c': 3, 'b': 2}
>>> for key in d:
...     print key
... 
a
c
b
>>>     print key
  File "<stdin>", line 1
    print key
    ^
IndentationError: unexpected indent
>>> for key in d:
...     print key
... 
a
c
b
>>> for key in d:
...     print key
... 
a
c
b
>>> 
>>> for value in d.itervalues():
...     print value
... 
1
3
2
>>> 
>>> for k,v in d.iteritems():
...     print k,v 
... 
a 1
c 3
b 2


>>> for ch in 'ABC':              ## 字符串迭代
...    print ch 
... 
A
B
C
>>> for i,ch in enumerate('ABC'):
...     print i,ch
... 
0 A
1 B
2 C



List 迭代：
>>> for i, value in enumerate(['A', 'B', 'C']):
...     print i, value
...
0 A
1 B
2 C

```

## 列表的生成(函数式编程模型)
```
>>> range(1,11)
[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]

>>> L = []
>>> for x in range(1,11):
...     L.append(x*x)
... 
>>> L
[1, 4, 9, 16, 25, 36, 49, 64, 81, 100]
>>> 


>>> [x * x for x in range(1,11)]
[1, 4, 9, 16, 25, 36, 49, 64, 81, 100]
>>> 

>>> for a,b in d.iteritems():
...     print a, '=', b
... 
>>> [x * x for x in range(1,11)]
[1, 4, 9, 16, 25, 36, 49, 64, 81, 100]
>>> [x * x for x in range(1,11) if x % 2 == 0]
[4, 16, 36, 64, 100]
>>> [m + n for m in 'ABC' for n in 'XYZ']
['AX', 'AY', 'AZ', 'BX', 'BY', 'BZ', 'CX', 'CY', 'CZ']
>>> import os
>>> [d for d in os.listdir('.')]
['rocketmq.properties', 'processtmp.log', 'uniq.1.gz', 'producer-over-ftp.properties', 
'demo01.txt', 'demo.py', 'users.properties', 'receive.2018.01.23.log', 
'hello02.txt', 'tmp.log', 'process.2018.01.23.log', 'ftpAddress.properties', 
'tmp.txt', 'demo02.txt', 'ftpserver', 'spring-context.xml', 'cluster-over-ftp.properties', 
'hello.txt', 'new.txt', 'old.txt', 'demo.txt', 'log4j.properties', 'hello01.txt']
>>> [d for d in os.listdir('/')]
['run', 'var', 'initrd.img', 'snap', 'boot', 'vmlinuz.old', 'srv', 'om', 'bin', 'tmp', 
'lost+found', 'lib', 'lib64', 'media', 'dev', 'etc', 'mnt', 'cdrom', 'sbin', 
'initrd.img.old', 'root', 'vmlinuz', 'sys', 'opt', 'home', 'usr', 'm2.tar.gz', 'proc']
>>> [d for d in os.listdir('.')]
['rocketmq.properties', 'processtmp.log', 'uniq.1.gz', 'producer-over-ftp.properties', 
'demo01.txt', 'demo.py', 'users.properties', 'receive.2018.01.23.log', 'hello02.txt', 
'tmp.log', 'process.2018.01.23.log', 'ftpAddress.properties', 'tmp.txt', 'demo02.txt', 
'ftpserver', 'spring-context.xml', 'cluster-over-ftp.properties', 'hello.txt', 
'new.txt', 'old.txt', 'demo.txt', 'log4j.properties', 'hello01.txt']
>>> d = {'x' : 'A', 'y' : 'B', 'z' : 'C'}
>>> d
{'y': 'B', 'x': 'A', 'z': 'C'}
>>> for a,b in d.iteritems():
...     print k, '=', v
... 
b = 2
b = 2
b = 2
>>> for a,b in d.iteritems():
...     print a, '=', b
... 
y = B
x = A
z = C
>>> d = {'x' : 'A', 'y' : 'B', 'z' : 'C'}
>>> [k + '=' + v for k,v in d.iteritems()]
['y=B', 'x=A', 'z=C']
>>> L=['Hello', 'World', 'IBM', 'Apple']
>>> L
['Hello', 'World', 'IBM', 'Apple']
>>> [s.lower() for s in L]
['hello', 'world', 'ibm', 'apple']


判断一个变量是否是一个类型或者一类类型：
 isinstance(y, str)
 isinstance(y, (int, float, boolean))

```

## 生成器（调用next 的时候才返回值）
generator略  


# 函数式编程
函数式编程的一个特点就是，允许把函数本身作为参数传入另一个函数，还允许返回一个函数！  
## 高阶函数
```
变量可以指向函数
>>> abs(10)
10
>>> abs
<built-in function abs>
>>> f=abs
>>> f
<built-in function abs>
>>> f(-10)
10


传入函数：
def add(x, y, f)
    return f(x) + f(y)

>>> add(x, y, abs)
11

```
### Map/Reduce  （映射和聚合）
### filter  （过滤）
### sort   （排序）
## 返回函数   （函数里面定义函数）
## 匿名函数  （lambda 表达式，）
## 装饰器  （函数执行前后的操作）
## 偏函数（利用默认参数，改变现有函数）


# 模块
```
简介，一个.py 文件就是一个模块，
python 按照目录来组织模块（即包名）
每个包名下面都必须有一个__init__.py 可以是空文件，也可以有python 代码
__init__.py 本身就是一个模块，它的模块名字和包名相同
```

