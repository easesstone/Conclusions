深入理解Java 8 Lambda（类库篇——Streams API，Collectors和并行）
发表于 2016-09-27   |   分类于 编程   |  
http://zh.lucida.me/blog/java-8-lambdas-inside-out-library-features/
```

关于
深入理解 Java 8 Lambda（语言篇——lambda，方法引用，目标类型和默认方法）
深入理解 Java 8 Lambda（类库篇——Streams API，Collector 和并行）
深入理解 Java 8 Lambda（原理篇——Java 编译器如何处理 lambda）
本文是深入理解 Java 8 Lambda 系列的第二篇，主要介绍 Java 8 
针对新增语言特性而新增的类库（例如 Streams API、Collectors 和并行）。

本文是对 Brian Goetz的State of the Lambda: Libraries Edition 一文的翻译。

Java SE 8 增加了新的语言特性（例如 lambda 表达式和默认方法），为此 Java SE
 8 的类库也进行了很多改进，本文简要介绍了这些改进。在阅读本文前，你应该先阅
 读 深入浅出Java 8 Lambda（语言篇），
以便对 Java SE 8 的新增特性有一个全面了解。

背景（Background）
自从lambda表达式成为Java语言的一部分之后，Java集合（Collections）API就
面临着大幅变化。而 JSR 355（规定了 Java lambda 表达式的标准）的正式启用
更是使得 Java 集合 API 变的过时不堪。尽管我们可以从头实现一个新的集
合框架（比如“Collection II”），
但取代现有的集合框架是一项非常艰难的工作，因为集合接口渗透了 Java 
生态系统的每个角落，将它们一一换成新类库需要相当长的时间。因此，
我们决定采取演化的策略（而非推倒重来）以改进集合 API：

为现有的接口（例如 Collection，List 和 Stream）增加扩展方法；
在类库中增加新的 流（stream，即 java.util.stream.Stream）抽象以便进行聚集（aggregation）操作；
改造现有的类型使之可以提供流视图（stream view）；
改造现有的类型使之可以容易的使用新的编程模式，这样用户就不必抛弃使用以
久的类库，例如 ArrayList 和 HashMap（当然这并不是说集合 API 会常驻永存，
毕竟集合 API 在设计之初并没有考虑到 lambda 表达式。我们可能会在未来的 
JDK 中添加一个更现代的集合类库）。
除了上面的改进，还有一项重要工作就是提供更加易用的并行（Parallelism）库
。尽管 Java 平台已经对并行和并发提供了强有力的支持，然而开发者在实际工作
（将串行代码并行化）中仍然会碰到很多问题。因此，我们希望 Java 类库能够既
便于编写串行代码也便于编写并行代码，因此我们把编程的重点从具体执行细节（
how computation should be formed）转移到抽象执行步骤（what computation should
 be perfomed）。除此之外，我们还需要在将并行变的 容易（easier）和将并行变的
  不可见（invisible）之间做出抉择，我们选择了一个折中的路线：提供 
  显式（explicit）但 非侵入（unobstrusive）的并行。（如果把并行变的透明，
  那么很可能会引入不确定性（nondeterminism）以及各种数据竞争（data race）问题）

内部迭代和外部迭代（Internal vs external iteration）
集合类库主要依赖于 外部迭代（external iteration）。Collection 实现 Iterable 接口
，从而使得用户可以依次遍历集合的元素。比如我们需要把一个集
合中的形状都设置成红色，那么可以这么写：

for (Shape shape : shapes) {
  shape.setColor(RED);
}
这个例子演示了外部迭代：for-each 循环调用 shapes 的 iterator() 
方法进行依次遍历。外部循环的代码非常直接，但它有如下问题：

Java 的 for 循环是串行的，而且必须按照集合中元素的顺序进行依次处理；
集合框架无法对控制流进行优化，例如通过排序、并行、短路（short-circuiting）
求值以及惰性求值改善性能。
尽管有时 for-each 循环的这些特性（串行，依次）是我们所期待的，但它对改善性能造成了阻碍。

我们可以使用 内部迭代（internal iteration）替代外部迭代，用户把对
迭代的控制权交给类库，并向类库传递迭代时所需执行的代码。

下面是前例的内部迭代代码：

1
shapes.forEach(s -> s.setColor(RED));
尽管看起来只是一个小小的语法改动，但是它们的实际差别非常巨大。
用户把对操作的控制权交还给类库，从而允许类库进行各种各样的优化（
例如乱序执行、惰性求值和并行等等）。总的来说，内部迭代使得外部迭
代中不可能实现的优化成为可能。

外部迭代同时承担了 做什么（把形状设为红色）和 怎么做（得到
 Iterator 实例然后依次遍历）两项职责，而内部迭代只负责 做什
 么，而把 怎么做 留给类库。通过这样的职责转变：用户的代码会
 变得更加清晰，而类库则可以进行各种优化，从而使所有用户都从中受益。

流（Stream）
流 是 Java SE 8 类库中新增的关键抽象，它被定义于 java.util.stream（
这个包里有若干流类型：Stream<T> 代表对象引用流，此外还有一系列特化（
specialization）流，比如 IntStream 代表整形数字流）。每个流代表一个值序列
，流提供一系列常用的聚集操作，使得我们可以便捷的在它上面进行各种运算。
集合类库也提供了便捷的方式使我们可以以操作流的方式使用集合、数组以及其它数据结构。

流的操作可以被组合成 流水线（Pipeline）。以前面的例子为例，如果我们只想把蓝色改成红色：

shapes.stream()
      .filter(s -> s.getColor() == BLUE)
      .forEach(s -> s.setColor(RED));
在 Collection 上调用 stream() 会生成该集合元素的流视图（stream view），
接下来 filter() 操作会产生只包含蓝色形状的流，最后，这些蓝色形状会被 forEach 操作设为红色。

如果我们想把蓝色的形状提取到新的 List 里，则可以：

List<Shape> blue =
    shapes.stream()
          .filter(s -> s.getColor() == BLUE)
          .collect(Collectors.toList());
collect() 操作会把其接收的元素聚集（aggregate）到一起（这里是 List），
collect() 方法的参数则被用来指定如何进行聚集操作。在这里我们使用 toList() 
以把元素输出到 List 中。（如需更多 collect() 方法的细节，请阅读 Collectors 一节）

如果每个形状都被保存在 Box 里，然后我们想知道哪个盒子至少包含一个蓝色形状，我们可以这么写：
Set<Box> hasBlueShape =
    shapes.stream()
          .filter(s -> s.getColor() == BLUE)
          .map(s -> s.getContainingBox())
          .collect(Collectors.toSet());
map() 操作通过映射函数（这里的映射函数接收一个形状，然后返回包含它的盒子）
对输入流里面的元素进行依次转换，然后产生新流。

如果我们需要得到蓝色物体的总重量，我们可以这样表达：

int sum =
    shapes.stream()
          .filter(s -> s.getColor() == BLUE)
          .mapToInt(s -> s.getWeight())
          .sum();
这些例子演示了流框架的设计，以及如何使用流框架解决实际问题。

流和集合（Streams vs Collections）
集合和流尽管在表面上看起来很相似，但它们的设计目标是不同的
集合主要用来对其元素进行有效（effective）的管理和访问（access），
而流并不支持对其元素进行直接操作或直接访问，而只支持通过声明式操作在
其上进行运算然后得到结果。除此之外，流和集合还有一些其它不同：

无存储：流并不存储值；流的元素源自数据源（可能是某个数据结构、生成
函数或 I/O 通道等等），通过一系列计算步骤得到；
天然的函数式风格（Functional in nature）：对流的操作会产生一个结果
，但流的数据源不会被修改；
惰性求值：多数流操作（包括过滤、映射、排序以及去重）都可以以惰性
方式实现。这使得我们可以用一遍遍历完成整个流水线操作，并可以用短路操作提供更高效的实现；
无需上界（Bounds optional）：不少问题都可以被表达为无限流（infinite stream）：
用户不停地读取流直到满意的结果出现为止（比如说，枚举 完美数 这个操作
可以被表达为在所有整数上进行过滤）。集合是有限的，但流不是（操作无限
流时我们必需使用短路操作，以确保操作可以在有限时间内完成）；
从API的角度来看，流和集合完全互相独立，不过我们可以既把集合作为流的
数据源（Collection 拥有 stream() 和 parallelStream() 方法），
也可以通过流产生一个集合（使用前例的 collect() 方法）。
Collection 以外的类型也可以作为 stream 的数据源，比如JDK中的
 BufferedReader、Random 和 BitSet 已经被改造可以用做流的数据源，
 Arrays.stream() 则产生给定数组的流视图。事实上，任何可以用 Iterator 
 描述的对象都可以成为流的数据源，如果有额外的信息（比如大小、
 是否有序等特性），库还可以进行进一步的优化。

惰性（Laziness）

过滤和映射这样的操作既可以被 急性求值（以 filter 为例，
急性求值需要在方法返回前完成对所有元素的过滤），也可以被 惰性求值（
用 Stream 代表过滤结果，当且仅当需要时才进行过滤操作）在实际中进行惰
性运算可以带来很多好处。比如说，如果我们进行惰性过滤，我们就可以把过
滤和流水线里的其它操作混合在一起，从而不需要对数据进行多遍遍历。
相类似的，如果我们在一个大型集合里搜索第一个满足某个条件的元素，
我们可以在找到后直接停止，而不是继续处理整个集合。（这一点对无
限数据源是很重要，惰性求值对于有限数据源起到的是优化作用，但对无
限数据源起到的是决定作用，没有惰性求值，对无限数据源的操作将无法终止）

对于过滤和映射这样的操作，我们很自然的会把它当成是惰性求值操作，
不过它们是否真的是惰性取决于它们的具体实现。另外，像 sum() 这样
生成值的操作和 forEach() 这样产生副作用的操作都是“天然急性求值”
，因为它们必须要产生具体的结果。

以下面的流水线为例：


int sum =
    shapes.stream()
          .filter(s -> s.getColor() == BLUE)
          .mapToInt(s -> s.getWeight())
          .sum();
这里的过滤操作和映射操作是惰性的，这意味着在调用 sum() 之前，
我们不会从数据源提取任何元素。在 sum 操作开始之后，我们把过滤、
映射以及求和混合在对数据源的一遍遍历之中。这样可以大大减少维持中间结果所带来的开销。

大多数循环都可以用数据源（数组、集合、生成函数以及I/O管道）
上的聚合操作来表示：进行一系列惰性操作（过滤和映射等操作）
，然后用一个急性求值操作（forEach，toArray 和 collect 等操
作）得到最终结果——例如过滤—映射—累积，过滤—映射—排序—
遍历等组合操作。惰性操作一般被用来计算中间结果，这在Streams API
设计中得到了很好的体现——与其让 filter 和 map 返回一个集合，我们
选择让它们返回一个新的流。在 Streams API 中，返回流对象的操作都是
惰性操作，而返回非流对象的操作（或者无返回值的操作，例如 forEach()）
都是急性操作。绝大多数情况下，潜在的惰性操作会被用于聚合，这正是我们
想要的——流水线中的每一轮操作都会接收输入流中的元素，进行转换，
然后把转换结果传给下一轮操作。

在使用这种 数据源—惰性操作—惰性操作—急性操作 流水线时，
流水线中的惰性几乎是不可见的，因为计算过程被夹在数据源和
最终结果（或副作用操作）之间。这使得API的可用性和性能得到了改善。

对于 anyMatch(Predicate) 和 findFirst() 这些急性求值操作，
我们可以使用短路（short-circuiting）来终止不必要的运算。以下面的流水线为例：


Optional<Shape> firstBlue =
    shapes.stream()
          .filter(s -> s.getColor() == BLUE)
          .findFirst();
由于过滤这一步是惰性的，findFirst 在从其上游得到
一个元素之后就会终止，这意味着我们只会处理这个元素及其之前
的元素，而不是所有元素。findFirst() 方法返回 Optional 对象，
因为集合中有可能不存在满足条件的元素。Optional 是一种用于描述可缺失值的类型。

在这种设计下，用户并不需要显式进行惰性求值，甚至他们都不需
要了解惰性求值。类库自己会选择最优化的计算方式。

并行（Parallelism）

流水线既可以串行执行也可以并行执行，并行或串行是流的属性。
除非你显式要求使用并行流，否则JDK总会返回串行流。（串行流可
以通过 parallel() 方法被转化为并行流）

尽管并行是显式的，但它并不需要成为侵入式的。利用 parallelStream()，
我们可以轻松的把之前重量求和的代码并行化：

1
2
3
4
5
int sum =
    shapes.parallelStream()
          .filter(s -> s.getColor = BLUE)
          .mapToInt(s -> s.getWeight())
          .sum();
并行化之后和之前的代码区别并不大，然而我们可以很容易看出它
是并行的（此外我们并不需要自己去实现并行代码）。

因为流的数据源可能是一个可变集合，如果在遍历流时数据源被修改
，就会产生干扰（interference）。所以在进行流操作时，流的数据源
应保持不变（held constant）。这个条件并不难维持，如果集合只属于
当前线程，只要 lambda 表达式不修改流的数据源就可以。（这个条件和
遍历集合时所需的条件相似，如果集合在遍历时被修改，绝大多数的集合
实现都会抛出ConcurrentModificationException）我们把这个条件称
为无干扰性（non-interference）。

我们应避免在传递给流方法的 lambda 产生副作用。一般来说，打印调试语
句这种输出变量的操作是安全的，然而在 lambda 表达式里访问可变变量就有
可能造成数据竞争或是其它意想不到的问题，因为 lambda 在执行时可能会同
时运行在多个线程上，因而它们所看到的元素有可能和正常的顺序不一致。
无干扰性有两层含义：

不要干扰数据源；
不要干扰其它 lambda 表达式，当一个 lambda 在修改某个可变状态而另一
个 lambda 在读取该状态时就会产生这种干扰。
只要满足无干扰性，我们就可以安全的进行并行操作并得到可预测的结果
，即便对线程不安全的集合（例如 ArrayList）也是一样。

实例（Examples）
下面的代码源自 JDK 中的 Class 类型（getEnclosingMethod 方法），
这段代码会遍历所有声明的方法，然后根据方法名称、返回类型以及参数的数量和类型进行匹配：

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
for (Method method : enclosingInfo.getEnclosingClass().getDeclaredMethods()) {
  if (method.getName().equals(enclosingInfo.getName())) {
    Class<?>[] candidateParamClasses = method.getParameterTypes();
    if (candidateParamClasses.length == parameterClasses.length) {
      boolean matches = true;
      for (int i = 0; i < candidateParamClasses.length; i += 1) {
        if (!candidateParamClasses[i].equals(parameterClasses[i])) {
          matches = false;
          break;
        }
      }
      if (matches) { // finally, check return type
        if (method.getReturnType().equals(returnType)) {
          return method;
        }
      }
    }
  }
}
throw new InternalError("Enclosing method not found");
通过使用流，我们不但可以消除上面代码里面所有的临时变量，还
可以把控制逻辑交给类库处理。通过反射得到方法列表之后，我们利用
 Arrays.stream 将它转化为 Stream，然后利用一系列过滤器去除类型不符、
 参数不符以及返回值不符的方法，然后通过调用 findFirst 得到 Optional<Method>
 ，最后利用 orElseThrow 返回目标值或者抛出异常。

1
2
3
4
5
6
return Arrays.stream(enclosingInfo.getEnclosingClass().getDeclaredMethods())
             .filter(m -> Objects.equals(m.getName(), enclosingInfo.getName()))
             .filter(m -> Arrays.equals(m.getParameterTypes(), parameterClasses))
             .filter(m -> Objects.equals(m.getReturnType(), returnType))
             .findFirst()
             .orElseThrow(() -> new InternalError("Enclosing method not found"));
相对于未使用流的代码，这段代码更加紧凑，可读性更好，也不容易出错。

流操作特别适合对集合进行查询操作。假设有一个“音乐库”应用，这个应用里每个库都有一
个专辑列表，每张专辑都有其名称和音轨列表，每首音轨表都有名称、艺术家和评分。

假设我们需要得到一个按名字排序的专辑列表，专辑列表里面的每张专辑都至少包含
一首四星及四星以上的音轨，为了构建这个专辑列表，我们可以这么写：

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
List<Album> favs = new ArrayList<>();
for (Album album : albums) {
  boolean hasFavorite = false;
  for (Track track : album.tracks) {
    if (track.rating >= 4) {
      hasFavorite = true;
      break;
    }
  }
  if (hasFavorite)
    favs.add(album);
}
Collections.sort(favs, new Comparator<Album>() {
  public int compare(Album a1, Album a2) {
    return a1.name.compareTo(a2.name);
  }
});
我们可以用流操作来完成上面代码中的三个主要步骤——识别一张专辑
是否包含一首评分大于等于四星的音轨（使用 anyMatch）；按名字排序
；以及把满足条件的专辑放在一个 List 中：

1
2
3
4
5
List<Album> sortedFavs =
    albums.stream()
          .filter(a -> a.tracks.anyMatch(t -> (t.rating >= 4)))
          .sorted(Comparator.comparing(a -> a.name))
          .collect(Collectors.toList());
Compartor.comparing 方法接收一个函数（该函数返回一个实现了 Comparab
le 接口的排序键值），然后返回一个利用该键值进行排序的 Comparator（请
参考下面的 比较器工厂 一节）。

收集器（Collectors）

在之前的例子中，我们利用 collect() 方法把流中的元素聚合到 List 或 Set 中。
collect() 接收一个类型为 Collector 的参数，这个参数决定了如何把流中的元素聚
合到其它数据结构中。Collectors 类包含了大量常用收集器的工厂方法，toList() 和
 toSet() 就是其中最常见的两个，除了它们还有很多收集器，用来对数据进行对复杂的转换。

Collector 的类型由其输入类型和输出类型决定。以 toList() 收集器为例，它的输入
类型为 T，输出类型为 List<T>，toMap 是另外一个较为复杂的 Collector，它有若
干个版本。最简单的版本接收一对函数作为输入，其中一个函数用来生成键（key），
另一个函数用来生成值（value）。toMap 的输入类型是 T，输出类型是 Map<K, V>，
其中 K 和 V 分别是前面两个函数所生成的键类型和值类型。（复杂版本的 toMap 收
集器则允许你指定目标 Map 的类型或解决键冲突）。举例来说，下面的代码以目录数
字为键值创建一个倒排索引：

1
2
3
Map<Integer, Album> albumsByCatalogNumber =
    albums.stream()
          .collect(Collectors.toMap(a -> a.getCatalogNumber(), a -> a));
groupingBy 是一个与 toMap 相类似的收集器，比如说我们想要把我们最喜欢的音
乐按歌手列出来，这时我们就需要这样的 Collector：它以 Track 作为输入，以 
Map<Artist, List<Track>> 作为输出。groupingBy 收集器就可以胜任这个工作，
它接收分类函数（classification function），然后根据这个函数生成 Map，
该 Map 的键是分类函数的返回结果，值是该分类下的元素列表。

1
2
3
4
Map<Artist, List<Track>> favsByArtist =
    tracks.stream()
          .filter(t -> t.rating >= 4)
          .collect(Collectors.groupingBy(t -> t.artist));
收集器可以通过组合和复用来生成更加复杂的收集器，简单版本的 
groupingBy 收集器把元素按照分类函数为每个元素计算出分类键值，
然后把输入元素输出到对应的分类列表中。除了这个版本，
还有一个更加通用（general）的版本允许你使用 其它 收集器来整理输入元素：
它接收一个分类函数以及一个下流（downstream）收集器（单参数版本的 
groupingBy 使用 toList() 作为其默认下流收集器）。举例来说，
如果我们想把每首歌曲的演唱者收集到 Set 而非 List 中，我们可以使用 toSet 收集器：

1
2
3
4
5
Map<Artist, Set<Track>> favsByArtist =
    tracks.stream()
          .filter(t -> t.rating >= 4)
          .collect(Collectors.groupingBy(t -> t.artist,
                                         Collectors.toSet()));
如果我们需要按照歌手和评分来管理歌曲，我们可以生成多级 Map：

1
2
3
4
Map<Artist, Map<Integer, List<Track>>> byArtistAndRating =
    tracks.stream()
          .collect(groupingBy(t -> t.artist,
                              groupingBy(t -> t.rating)));
在最后的例子里，我们创建了一个歌曲标题里面的词频分布。
我们首先使用 Stream.flatMap() 得到一个歌曲流，然后用 
Pattern.splitAsStream 把每首歌曲的标题打散成词流；
接下来我们用 groupingBy 和 String.toUpperCase 对这些词进行
不区分大小写的分组，最后使用 counting() 收
集器计算每个词出现的次数（从而无需创建中间集合）。

1
2
3
4
5
Pattern pattern = Pattern.compile("\\s+");
Map<String, Integer> wordFreq =
    tracks.stream()
          .flatMap(t -> pattern.splitAsStream(t.name)) // Stream<String>
          .collect(groupingBy(s -> s.toUpperCase(), counting()));
flatMap 接收一个返回流（这里是歌曲标题里的词）的函数。
它利用这个函数将输入流中的每个元素转换为对应的流，然后
把这些流拼接到一个流中。所以上面代码中的 flatMap 会返回
所有歌曲标题里面的词，接下来我们不区分大小写的把
这些词分组，并把词频作为值（value）储存。

Collectors 类包含大量的方法，这些方法被用来创造各式
各样的收集器，以便进行查询、列表（tabulation）和分组等工作
，当然你也可以实现一个自定义 Collector。

并行的实质（Parallelism under the hood）
Java SE 7 引入了 Fork/Join 模型，以便高效实现并行计算。
不过，通过 Fork/Join 编写的并行代码和同功能的串行代码的
差别非常巨大，这使改写串行代码变的非常困难。通过提供串行
流和并行流，用户可以在串行操作和并行操作之间进行便捷的切换
（无需重写代码），从而使得编写正确的并行代码变的更加容易。

为了实现并行计算，我们一般要把计算过程递归分解（recursive decompose）为若干步：

把问题分解为子问题；
串行解决子问题从而得到部分结果（partial result）；
合并部分结果合为最终结果。
这也是 Fork/Join 的实现原理。

为了能够并行化任意流上的所有操作，我们把流抽象为 Spli
terator，Spliterator 是对传统迭代器概念的一个泛化。分割
迭代器（spliterator）既支持顺序依次访问数据，也支持分解
数据：就像 Iterator 允许你跳过一个元素然后保留剩下的元素
，Spliterator 允许你把输入元素的一部分（一般来说是一半）转
移（carve off）到另一个新的 Spliterator 中，而剩下的数据则会
被保存在原来的 Spliterator 里。（这两个分割迭代器还可以被进一步分解
）除此之外，分割迭代器还可以提供源的元数据（比如元素的数量，如果已知
的话）和其它一系列布尔值特征（比如说“元素是否被排序”这样的特征），
Streams 框架可以利用这些数据来进行优化。

上面的分解方法也同样适用于其它数据结构，数据结构的作者只需要提供
分解逻辑，然后就可以直接享用并行流操作带来的遍历。

大多数用户无需去实现 Spliterator 接口，因为集合上的 strea
m() 方法往往就足够了。但如果你需要实现一个集合或一个流，
那么你可能需要手动实现 Spliterator 接口。Spliterator 接口的API如下所示：

1
2
3
4
5
6
7
8
9
10
11
12
13
public interface Spliterator<T> {
  // Element access
  boolean tryAdvance(Consumer< ? super T> action);
  void forEachRemaining(Consumer< ? super T> action);
  // Decomposition
  Spliterator<T> trySplit();
  //Optional metadata
  long estimateSize();
  int characteristics();
  Comparator< ? super T> getComparator();
}
集合库中的基础接口 Collection 和 Iterable 都实现了正确但相对低效的 spli
terator() 实现，但派生接口（例如 Set）和具体实现类（例如 ArrayList）均提供
了高效的分割迭代器实现。分割迭代器的实现质量会影响到流操作的执行效率；如果在 
split() 方法中进行良好（平衡）的划分，CPU 的利用率会得到改善；此外，提供正确的
特性（characteristics）和大小（size）这些元数据有利于进一步优化。

出现顺序（Encounter order）

多数数据结构（例如列表，数组和I/O通道）都拥有 自然出现顺序（natural encount
er order），这意味着它们的元素出现顺序是可预测的。其它的数据结构（例如 HashSet）
则没有一个明确定义的出现顺序（这也是 HashSet 的 Iterator 实现中不保证元素出现顺序的原因）。

是否具有明确定义的出现顺序是 Spliterator 检查的特性之一（这个特性也被流使用
）。除了少数例外（比如 Stream.forEach() 和 Stream.findAny()），并行操作一般
都会受到出现顺序的限制。这意味着下面的流水线：

1
2
3
4
List<String> names =
    people.parallelStream()
          .map(Person::getName)
          .collect(toList());
代码中名字出现的顺序必须要和流中的 Person 出现的顺序一致。一般
来说，这是我们所期待的结果，而且它对多大多数的流实现都不会造成明显
的性能损耗。从另外的角度来说，如果源数据是 HashSet，那么上面代码中名字就可以以任意顺序出现。

JDK 中的流和 lambda（Streams and lambdas in JDK）
Stream 在 Java SE 8 中非常重要，我们希望可以在 JDK 中尽可能广的使
用 Stream。我们为 Collection 提供了 stream() 和 parallelStream()，
以便把集合转化为流；此外数组可以通过 Arrays.stream() 被转化为流。

除此之外，Stream 中还有一些静态工厂方法（以及相关的原始类型流实现），
这些方法被用来创建流，例如 Stream.of()，Stream.generate 以及 IntStream.range。
其它的常用类型也提供了流相关的方法，例如 String.chars，BufferedReader.lines，
Pattern.splitAsStream，Random.ints 和 BitSet.stream。

最后，我们提供了一系列API用于构建流，类库的编写者可以利用这些API来在流上实现
其它聚集操作。实现 Stream 至少需要一个 Iterator，不过如果编写者还拥有其它元数据
（例如数据大小），类库就可以通过 Spliterator 提供一个更加高效的实现
（就像 JDK 中所有的集合一样）。

比较器工厂（Comparator factories）

我们在 Comparator 接口中新增了若干用于生成比较器的实用方法：

静态方法 Comparator.comparing() 接收一个函数（该函数返回一个实现 Comparab
le 接口的比较键值），返回一个 Comparator，它的实现十分简洁：

1
2
3
4
public static <T, U extends Comparable< ? super U>> Compartor<T> comparing(
    Function< ? super T, ? extends U> keyExtractor) {
  return (c1, c2) -> keyExtractor.apply(c1).compareTo(keyExtractor.apply(c2));
}
我们把这种方法称为 高阶函数 ——以函数作为参数或是返回值的函数。我们可以使
用高阶函数简化代码：

1
2
List<Person> people = ...
people.sort(comparing(p -> p.getLastName()));
这段代码比“过去的代码”（一般要定义一个实现 Comparator 接口的匿名类）要简
洁很多。但是它真正的威力在于它大大改进了可组合性（composability）。举例来说，
Comparator 拥有一个用于逆序的默认方法。于是，如果想把列表按照姓进行反序排序，我
们只需要创建一个和之前一样的比较器，然后调用反序方法即可：

1
people.sort(comparing(p -> p.getLastName()).reversed());
与之类似，默认方法 thenComparing 允许你去改进一个已有的 Comparator：在原比较
器返回相等的结果时进行进一步比较。下面的代码演示了如何按照姓和名进行排序：

1
2
3
4
Comparator<Person> c =
    Comparator.comparing(p -> p.getLastName())
              .thenComparing(p -> p.getFirstName());
people.sort(c);
可变的集合操作（Mutative collection operation）

集合上的流操作一般会生成一个新的值或集合。不过有时我们希望就地修改集合，
所以我们为集合（例如 Collection，List 和 Map）提供了一些新的方法，比如 I
terable.forEach(Consumer)，Collection.removeAll(Predicate)，List.replaceAll(UnaryOperator)
，List.sort(Comparator) 和 Map.computeIfAbsent()。除此之外，ConcurrentMap 中的一些非原子方法
（例如 replace 和 putIfAbsent）被提升到 Map 之中。

小结（Summary）
引入 lambda 表达式是 Java 语言的巨大进步，但这还不够——开发者每天都要使用
核心类库，为了开发者能够尽可能方便的使用语言的新特性，语言的演化和
类库的演化是不可分割的。Stream 抽象作为新增类库特性的核心，提供了强
大的数据集合操作功能，并被深入整合到现有的集合类和其它的 JDK 类型中。
```

