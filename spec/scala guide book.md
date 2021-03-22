# 提纲

## 编程工具

* Install Java/SBT/Scala

    * Windows

        推荐使用[scoop](https://github.com/lukesampson/scoop)安装OpenJDK8/OpenJDK11, sbt, scala
        > Windows terminal + powershell + oh-my-posh / WSL2 + oh-my-zsh 更香
        ```shell
        scoop bucket add java
        scoop install openjdk
        scoop install sbt
        scoop install scala
        ```
        
        or

        自行下载安装包单独安装

    * Mac OS-X

        推荐使用homebrew安装
        
* IDE support

    * Installing IntelliJ for Scala

        ![scala plugin](https://www.handsonscala.com/images/intellij-plugin.png "Scala plugin")

    * VSCode support

        ![metals plugin](https://www.handsonscala.com/images/vscode-metals-install.png "VSCode metals plugin")
        

    * Vim/Emacs - Metals

* Scala REPL / [Scasite](https://scastie.scala-lang.org/)

    可以在REPL/Scasite做一些简单练习和验证
    > REPL在安装完scala后，在命令行输入scala即可进入

## 基本语法

### 类定义

下面是一个Scala类，其构造函数定义了两个参数：firstName和lastName

```scala
class Person(var firstName: String, var lastName: String)
```

根据该定义，您可以创建如下所示的新Person实例

```scala
val p = new Person("Bill", "Panner")
```

在类构造函数中定义参数会自动在类中创建字段，在本例中，您可以访问firstName和lastName字段

```scala
println(p.firstName + " " + p.lastName)
Bill Panner
```

在本例中，因为这两个字段都定义为var字段，所以它们也是可变的，你可以这样重新赋值。

```scala
scala> p.firstName = "William"
p.firstName: String = William

scala> p.lastName = "Bernheim"
p.lastName: String = Bernheim
```

> 上述定义class的方式，相当于Java中定义一个class，包含2个私有成员变量和一个构造函数，并且变量有各自的getter/setter

您还可以将类参数定义为val字段，这使得它们不可变，默认就是val，也是scala推崇的方式

```scala
class Person(val firstName: String, val lastName: String)
```

> 提示：如果您使用Scala编写OOP代码，请将字段创建为var字段，以便对其进行变异。使用Scala编写FP代码时，通常使用case类，而不是这样的类。（稍后将详细介绍。）

您可以通过定义名为this的方法来定义辅助Scala类构造函数。（这里有些小原则）

```scala
final val DefaultFirstName = "Alice"
final val DefaultLastName = "M"

// the primary constructor
class Person(val firstName: String, val lastName: String) {
    // one-arg auxiliary constructor
    def this(firstName: String) = {
        this(firstName, DefaultLastName)
    }

    // zero-arg auxiliary constructor
    def this() = {
        this(DefaultFirstName, DefaultLastName)
    }
}
```

定义了所有这些构造函数后，您可以用几种不同的方法创建`Person`实例

```scala
val p1 = new Person("San", "Zhang")
val p2 = new Person("Bob")
val p3 = new Person
```

> 推荐在Scala REPL或者Scasite中敲几行

### 变量定义

在Java中，您可以这样声明变量，在声明时需要指定类型

```java
String s = "hello";
int i = 42;
Person p = new Person("Joel Fleischman");
```

Scala中，有两种类型的变量

- `val` 创建一个不可变的变量（如Java中的final）
- `var` 创建一个可变变

```scala
val s = "hello"   // immutable
var i = 42        // mutable

val p = new Person("Joel Fleischman")
```

这是因为scala编译器可以根据`=`符号右侧的代码推断出具体类型，当然也可以显示的声明类型（通常情况，编译器不关心显示类型，如果出于代码可读性考虑，可以适当显示声明）

```scala
val s: String = "hello"
var i: Int = 42
```

在class中可以这样定义变量

```scala
class Person(firstName: String, lastName: String) {
    val age = 18
    var height = 180
    private val secret = "not known"
    ...
}
```

**val和var之间的区别**

> val和var的区别在于，val使变量不可变——就像Java中的final一样，而var使变量可变。因为val字段不能变化，所以有些人把它们称为值而不是变量

你可以在REPL尝试下，给val赋值会发生什么

```scala
scala> val a = 'a'
a: Char = a

scala> a = 'b'
<console>:12: error: reassignment to val
       a = 'b'
         ^
```

编译不会通过，换成var就可以了。

> 在Scala中，推荐使用val，有助于开始函数式编程，其中所有字段都是不可变的

### 方法定义

你可以使用`def`关键字来定义方法（有点像python/groovy)

```scala
def printHello(times: Int) = {
    println("hello " + times)
}

scala> printHello(1)
hello 1

scala> printHello(times = 2) // 显式提供的参数名
hello 2
```

也可以给参数设置默认值

```scala
def printHello2(times: Int = 0) = {
    println("hello " + times)
}

scala> printHello2()
hello 0
```

**本地函数**

可以灵活的在method内部定义local functions

```scala
def processFile(filename: String, width: Int) = {
    def processLine(filename: String, width: Int, line: String) = ???
    
    ...
    
    processLine(filename, width, line)
}
```

**方法返回值**

大括号{}块中的最后一个表达式被视为一个Scala方法的返回值，并且在方法声明时不需要显示指定返回值类型签名，Scala可以自己完成类型推断。

> scala保留了`return`，但并不提倡使用，感兴趣可以读一下[SAY NO TO RETURN](https://blog.knoldus.com/scala-best-practices-say-no-to-return/)

```scala
def hello(i: Int = 0) = {
    "hello " + i
}

scala> hello(1)
res: String = hello 1
```

在Scala中可以使用特殊符号定义方法，比如定义一个`+`(add)方法

```scala
def + (b: Int): Int = ???

scala> 1 + 2   // 实则是在调用`+`方法，+并不是运算符
3
```

> 这种风格（语法糖）很常见，包括各种第三方库中，有大量的`符号`方法，比如: ==, ##, |, *, |+|等等……

**函数值（匿名函数）**

函数值类似于方法，在调用函数时带参数，它们可以执行某些操作或返回某些值。与方法不同，函数本身是值:您可以传递它们，将它们存储在变量中，然后再调用它们

> 可以先以lambda方式理解Function values

用`=>`定义函数值

```scala
val add: (Int, Int) => Int = (a, b) => { a + b }

scala> add(1, 2)
res: Int = 3
```

function values的演进是这样的, 下面case是上述的显示打开方式

```scala
// implicit approach
val add = (x: Int, y: Int) => { x + y }
val add = (x: Int, y: Int) => x + y
```

在方法中使用函数值

```scala
scala> def modMethod(i: Int) = i % 2 == 0
modMethod: (i: Int)Boolean

scala> val list = List.range(1, 10)
list: List[Int] = List(1, 2, 3, 4, 5, 6, 7, 8, 9)

scala> list.filter(modMethod)
res0: List[Int] = List(2, 4, 6, 8)
```

> 函数值的使用相对比较灵活，但也有其他弊端

### 类型声明 (TODO?)

Scala虽然是静态类型语言，但是依附于类型推断，我们可以在声明变量时，不显示的定义类型签名（这很灵活，看起来像动态类型），完全是因为Scala强大的类型系统

**值类型**

Scala中的每个值都有一个类型，以很多种形式存在

包括但不限于Single Type, Literal Type, Type Projection, Parameterized Types, Tuple Types, Infix Types, Function Types

**非值类型**

不会显示的出现

Method Types, Polymorphic Method Types, Type Constructors

**类型声明**

合法的类型声明：

```scala
type IntList = List[Integer]
type T <: Comparable[T]
type Two[A] = Tuple2[A, A]
type MyCollection[+X] <: Iterable[X]
```

值类型声明和定义

```scala
val pi = 3.1415
val pi: Double = 3.1415   // 等同于上面
val Some(x) = f()         // 一种模式定义
val x :: xs = mylist      // 中缀模式定义
```

### 枚举定义

Scala中没有`enum`关键字，Scala提供了一个`Enumeration`类，我们可以通过扩展来创建枚举

定义一组简单的枚举

```scala
object Weekday extends Enumeration {
  val Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday = Value
}

scala> Weekday.Monday.toString
res0: String = Monday

scala> Weekday.withName("Monday")
res1: Weekday.Value = Monday

scala> Weekday.values.toList.sorted
res2: List[Weekday.Value] = List(Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday)

scala> Weekday.withName("Mondai")
java.util.NoSuchElementException: No value found for 'Mondai'
  at scala.Enumeration.withName(Enumeration.scala:124)
  ... 32 elided
```

可以重写ID和name来改变排序规则和增加可读性

```scala
object Weekday extends Enumeration {
    val Monday = Value(1, "Mo.")   // ID=1
    val Tuesday = Value(2, "Tu.")  // ID=2
    val Wednesday = Value(3, "We.")    // ID=3
    val Thursday = Value("Th.") // ID=4
    val Friday = Value("Fr.")   // ID=5
    val Saturday = Value("Sa.") // ID=6
    val Sunday = Value(0, "Su.")    // ID=0
}

scala> Weekday.Monday.toString
res0: String = Mo.

scala> Weekday.values.toList.sorted
res0: List[Weekday.Value] = List(Su., Mo., Tu., We., Th., Fr., Sa.)

scala> println(s"ID of Monday = ${Weekday.Monday.id}") 
ID of Monday = 0
```

> ID是继承`Enumeration`扩展而来，枚举值的唯一标识，默认是根据定义顺序从0开始，并且*.values的是根据ID排序的，我们也可以更改枚举值ID

**使用type alias定义枚举，让代码更优雅**

```scala
object WeekDay extends Enumeration {
  type WeekDay = Value
  val Mon, Tue, Wed, Thu, Fri, Sat, Sun = Value
}

// 有了type alias，我们可以这么使用WeekDay
object SomeObject {
  import WeekDay._

  val someDay: WeekDay = Fri
}

// 如果没有定义type alias，我们需要这么使用枚举
object SomeObject {
  val someDay: WeekDay.Value = WeekDay.Mon
}
```

> Scala默认枚举类只支持ID和name属性，但是我们可以为枚举类增加自定义属性

### for循环（Loops, Conditionals, Comprehensions）

#### for-loop

在最简单的使用中，可以使用Scala for循环来遍历集合中的元素。例如，给定一个整数序列

```scala
scala> val nums = Seq(1,2,3)
nums: Seq[Int] = List(1, 2, 3)

scala> for (n <- nums) println(n)
1
2
3
```

> `<-`符号是Scala的关键字，在for loop中，它实际是foreach的语法糖；当然它还有更强大的意义，我们后续讨论，这里先理解为将集合iterator

使用for来遍历Map

```scala
val ratings = Map(
    "Lady in the Water"  -> 3.0, 
    "Snakes on a Plane"  -> 4.0, 
    "You, Me and Dupree" -> 3.5
)

scala> for ((name,rating) <- ratings) println(s"Movie: $name, Rating: $rating")
Movie: Lady in the Water, Rating: 3.0
Movie: Snakes on a Plane, Rating: 4.0
Movie: You, Me and Dupree, Rating: 3.5
```

#### If-Else

Scala的`if-else`语法上和其他语言类似。不同的是，在Scala中`if-else`也可以用作表达式，因为它总是有返回结果的

```scala
var total = 0

for (i <- Range(0, 10)) {
    if (i % 2 == 0) total += i
    else total += 2
}

scala> total
res: Int = 30
```

当做表达式使用（类似于其他语言的a ? b : c）

```scala
val minValue = if (a < b) a else b
```

#### FOR-COMPREHENSIONS

前面提到的`for-loop`与其他语言类似，但是带有副作用（例如STDOUT）。Scala提供了一种轻量级的符号来表示序列推导式，他可以配合`yield`从而消除副作用。

> 在函数式编程领域，副作用的处理非常讲究

```scala
val a = Array(1, 2, 3, 4)

scala> val a2 = for (i <- a) yield i * i
res: a2: Array[Int] = Array(1, 4, 9, 16)

scala> val a3 = for (i <- a) yield "hello " + i
res: a3: Array[String] = Array("hello 1", "hello 2", "hello 3", "hello 4")

scala> val a4 = for (i <- a if i % 2 == 0) yield "hello " + i
res: a4: Array[String] = Array("hello 2", "hello 4")
```

for推导式还可以接受多个输入数组，下面是a和b。这会将它们平铺成一个最终的输出数组，类似于使用嵌套的for循环

```scala
val a = Array(1, 2); val b = Array("hello", "world")
scala> val flattened = for (i <- a; s <- b) yield s + i
res: flattened: Array[String] = Array("hello1", "world1", "hello2", "world2")
```

也可以more pretty些

```scala
scala> val flattened = for{
    i <- a
    s <- b
} yield s + i
res: flattened: Array[String] = Array("hello1", "world1", "hello2", "world2")

scala> val flattened2 = for{
    s <- b
    i <- a
} yield s + i
res: flattened2: Array[String] = Array("hello1", "hello2", "world1", "world2")
```

> 注意，for推导中，`<- s`的顺序很重要，就像嵌套循环的顺序如何影响循环操作发生的顺序一样；
> `yield`也可以是代码块{}，常见的会丢出新集合或者Tuple

在前面的例子中，我们看到了`for-comprehension`的语义如何等同于sequence或stream上的一系列操作的语义。在Scala中，`for-comprehension`只不过是对一个或多个方法的一系列调用的语法糖: foreach, map, flatMap, withFilter

## 样本类/对象（case class/object）

### 单例对象（SINGLETON OBJECT）

scala中没有`static`关键字, 但是如果你想实现单例模式, 使用`object`关键字就可以实现单例

```scala
object Name {
// code...
}
```

scala中的main函数通常定义在单例`object`中

```scala
// singleton object 
object Main { 
    def main(args: Array[String]) { 
        //do something
    } 
} 
```

> `object`可以扩展class和trait, 它不支持带参数的主构造函数, 但是可以通过`apply`构造(后面讨论)

### 伴生对象（COMPANION OBJECT）

Scala中的伴生对象是在同一个文件中声明为类的对象，并且与类具有相同的名称。例如，以下代码保存在名为Person.scala，则Person对象被视为Person类的伴生对象.

```scala
class Person {

}

object Person {
    def apply(): Person = ???
}
```

**创建Person实例不需要使用new关键字**

```scala
val p = Person("Fred Flinstone")
```

这是因为object在编译中，会转换为

```scala
val p = Person.apply("Fred Flinstone")
```

`apply`方法可以理解为object的工厂方法, 因为scala的语法糖, 所以不需要显示apply或new

`class`和`object`之间可以相互访问

**在一个伴随对象中创建多个apply方法来提供多个构造函数**

```scala
class Person {
    var name: Option[String] = None
    var age: Option[Int] = None
    override def toString = s"$name, $age"
}

object Person {

    // a one-arg constructor
    def apply(name: Option[String]): Person = {
        var p = new Person
        p.name = name
        p
    }

    // a two-arg constructor
    def apply(name: Option[String], age: Option[Int]): Person = {
        var p = new Person
        p.name = name
        p.age = age
        p
    }

}
```

**对象提取器（EXTRACTOR OBJECTS）**

提取器对象是带有`unapply`方法的object，`unapply`与`apply`是相互的，并且可以是不同的类型签名。

```scala
class Person(var name: String, var age: Int)

object Person {
    def unapply(p: Person): String = s"${p.name}, ${p.age}"
}
```

你可以尝试在REPL执行查看结果

```scala
scala> val result = Person.unapply(p)
result: String = Lori, 29
```

> `unapply`通常在模式匹配中使用，所以如果你需要重写unapply，请把返回值类型定义为Option

- 如果只是测试，可以返回布尔值。例如`case even()`
- 如果它返回类型T的单个子值，则返回Option[T]
- 如果你想返回几个子值T1，…，Tn，将它们组成一个可选的Tuple Option[(T1，…，Tn)]

### 样本类（CASE CLASS）

为函数式编程提供支持的另一个Scala特性是case class。case class具有常规类的所有功能，甚至更多。当编译器在类前面看到case关键字时，它会为您生成代码，具有以下好处：

* Case class构造函数参数默认是公共val字段，并会生成getter方法。

* apply方法是在类的伴随对象中创建的，因此不需要使用new关键字来创建类的新实例

* 生成unapply方法，使您可以在pattern match中以更多方式使用case class

* 在类中生成一个copy方法。您可能在Scala/OOP代码中不使用此功能，但在Scala/FP中一直使用它

* 帮你生成equals/hashCode方法，还有默认的toString

**不需要使用new关键字实例化class**

```scala
scala> case class Person(name: String, relation: String)
defined class Person

// "new" not needed before Person
scala> val christina = Person("Christina", "niece")
christina: Person = Person(Christina, niece)
```

> 这是因为名为apply的方法是在Person的伴生对象`object`中生成的（前面提到的）

case class不会为成员生成setter，即默认都是val，所以当你对*.x = xxx时，会编译失败

**应用在模式匹配中（杀手锏）**

定义两个case class

```scala
trait Person {
    def name: String
}

case class Student(name: String, year: Int) extends Person
case class Teacher(name: String, specialty: String) extends Person
```

利用case class built-in的`unapply`进行模式匹配

```scala
def getPrintableString(p: Person): String = p match {
    case Student(name, year) =>
        s"$name is a student in Year $year."
    case Teacher(name, whatTheyTeach) =>
        s"$name teaches $whatTheyTeach."
}
```

Scala的标准实现是，unapply方法返回用Option包装的元组中的case类构造函数字段

**copy/equals and hashCode/toString**

case class很贴心的帮我们实现了这些

## 隐式转换（Implicit）

## 模式匹配（pattern matching）

## 特征（Trait）

## Scala继承结构图

Scala中的类型层次结构

![type hierarchy](https://www.baeldung.com/wp-content/uploads/sites/3/2020/08/Scala_type_hierarchy-1-768x644-1.png "Scala Type Hierarchy")

## 常用数据结构

### 列表（List）
### 元组（Tuple）

在Scala中，元组是一个包含固定数量元素的值，每个元素都有自己的类型。元组是不可变的

创建一个元组

```scala
val ingredient = ("Sugar" , 25)
```

ingredient的推断类型是(String, Int)，这是`Tuple2[String, Int]`的简写
为了表示元组，Scala使用了一系列的类:Tuple2, Tuple3，等等，直到Tuple22。每个类的类型参数和元素的数量一样多

> 通常利用元组可以在函数中返回多个不同类型的值

我们可以通过位置来访问元组中的元素，这种风格很方便，但是如果你的元组长度很大，建议使用case class封装，否则影响可读性

```scala
println(ingredient._1) // Sugar
println(ingredient._2) // 25
```

在scala中，一切皆模式，把元组应用在模式匹配中也是常见风格

```scala
ingredient match {
    case ("Sugar", quantity) => println(quantity)
    case _ => 
}
```

### 字典（Map）
### 异常（Exception）
### Option
### Either
### Future

`Future`在Scala中可以说是无处不在，例如HTTP调用、数据库操作等，它以一种高效和非阻塞的方式进行异步计算。
当我们创建一个新的Future时，Scala会产生一个新线程并执行其代码。执行完成后，计算结果（值或异常）将分配给Future（可以理解为占位符）

**ExecutionContext**

在学习Future之前，我们先看下ExecutionContext。这指定了Future的代码将如何以及在哪个线程池上执行。

创建ExecutionContext的几种方式

```scala
// 适配Java JUC的Executor
ExecutionContext.fromExecutor(new ThreadPoolExecutor( /* your configuration */ ))

// Scala way
val forkJoinPool: ExecutorService = new ForkJoinPool(4)
implicit val forkJoinExecutionContext: ExecutionContext = 
  ExecutionContext.fromExecutorService(forkJoinPool)

val singleThread: Executor = Executors.newSingleThreadExecutor()
implicit val singleThreadExecutionContext: ExecutionContext = 
  ExecutionContext.fromExecutor(singleThread)
```

还有一个全局内置的ExecutionContext，它使用ForkJoinPool，并将其并行级别设置为可用处理器的数量

```scala
// 局部使用
implicit val globalExecutionContext: ExecutionContext = ExecutionContext.global

// 全局import（惯用）
import scala.concurrent.ExecutionContext.Implicits.global
```

现在我们有了Future的运行环境，下面我们创建一个Future

```scala
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

def generateMagicNumber(): Int = {
    Thread.sleep(3000L)
    23
}
val generatedMagicNumberF: Future[Int] = Future {
    println("magic started")
    val result = generateMagicNumber()
    println("magic finished")
    result
}
```

如果我们已经有结果，可以直接创建一个成功的Future

```scala
def multiply(multiplier: Int): Future[Int] =
  if (multiplier == 0) {
    Future.successful(0)
  } else {
    Future(multiplier * generateMagicNumber())
  }
```

或者创建一个异常结果的Future

```scala
def divide(divider: Int): Future[Int] =
  if (divider == 0) {
    Future.failed(new IllegalArgumentException("Don't divide by zero"))
  } else {
    Future(generateMagicNumber() / divider)
  }

// more graceful
def tryDivide(divider: Int): Future[Int] = Future.fromTry(Try {
  generateMagicNumber() / divider
})
```

**获取`Future`中的值**

```scala
scala> generatedMagicNumberF
val res9: Option[scala.util.Try[Int]] = Some(Success(23))

scala> Await.result(generatedMagicNumberF, Duration.Inf)
val res11: Int = 23
```

> `Await`是blocking主线程的，他的设计者后来也很纠结，因为本身Future是一个高性能异步非阻塞计算，为什么还要设计出Await来blocking???这里有很多讨论，但是根据经验，当你明确必须要等待结果时可以使用Await，还可以在单元测试中使用Await，除此之外，不要滥用Await

可以使用`onComplete`（回调函数）来获取

```scala
def printResult[A](result: Try[A]): Unit = result match {
  case Failure(exception) => println("Failed with: " + exception.getMessage)
  case Success(number)    => println("Succeed with: " + number)
}
magicNumberF.onComplete(printResult)
```

> 后面我们会学习通过组合函数去转换/合并Future，从而完成数据转换抽取

**Future的异常处理**

`failed`

```scala
val failedF: Future[Int] = Future.failed(new IllegalArgumentException("Boom!"))
val failureF: Future[Throwable] = failedF.failed
```

fallbackTo在当前失败的情况下，它采用了另一种Future，并同时计算它们。如果两者都失败了，那么产生的Future将会失败，而从当前的Future中获取的异常也会失败。

```scala
val magicNumberF: Future[Int] =
    repository.readMagicNumber()
      .fallbackTo(backup.readMagicNumberFromLatestBackup())
```

`recover`和`recoverWith`来处理Future异常

```scala
val recoveredF: Future[Int] = Future(3 / 0).recover {
  case _: ArithmeticException => 0
}

val recoveredWithF: Future[Int] = Future(3 / 0).recoverWith {
  case _: ArithmeticException => magicNumberF
}
```

> recover和recoverWith结合着map和flatMap理解就对了

**操作Future结果（transform and combining）**

这一部分是Future的核心，利用函数式编程、高阶函数、Monad等对Future进行转换合并，从而得到您想要的结果。

`Future.map`和`Future.flatMap`

```scala
def increment(number: Int): Int = number + 1
val nextMagicNumberF: Future[Int] = magicNumberF.map(increment)
// scala> 24

val updatedMagicNumberF: Future[Boolean] =
  nextMagicNumberF.flatMap(repository.updateMagicNumber)
```

`Future.sequence`和`Future.traverse`

当你FP代码写多了，就会遇到Seq[Future[T]]的结果，但是你需要的Seq[T]，如果你用`OO`的思路去解决，又要考虑immutable，那的确是灾难，`Future.sequence`才是正道。

```scala
scala> val pricesList: List[Future[Int]] = List(Future(1001),Future(999),Future(-2000),Future(1000)) ...

scala> Future.sequence(pricesList)
res: Future(List(1001, 999, -2000, 1000...))
```

![future sequence](https://www.oreilly.com/library/view/scala-reactive-programming/9781787288645/assets/d8922285-7a66-41ad-9a7c-a39f30bbe83c.png "future sequence")

> sequence是traverse的简单版本，traverse会在更复杂的场景遇到，例如Slick的DBIO

scala里还有许多高阶函数，你也可以扩展设计自己的函数

### DBIOAction
## For推倒式
## 常用模式
### Functor/Monoid/Monad
### 蛋糕模式（Cake Pattern）
### 类型类（Type class）

### 代数数据类型（Algebraic Data Types）

代数数据类型（简称ADT）是一种结构化数据的方式。它们在Scala中得到广泛使用的主要原因是，它们在模式匹配方面的工作非常出色，并且使用它们使无法表示非法状态变得如此容易

ADT有两种基本类别：

- Product type
- Sum type
