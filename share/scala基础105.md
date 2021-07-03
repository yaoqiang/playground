# Future & Implicit & Type class & Generic

<!-- TOC -->

- [Future & Implicit & Type class & Generic](#future--implicit--type-class--generic)
    - [再聊Future](#再聊future)
    - [满屏幕的implicit是个啥？](#满屏幕的implicit是个啥)
    - [Type-class pattern - 你的第一个FP pattern](#type-class-pattern---你的第一个fp-pattern)
    - [浅聊泛型](#浅聊泛型)
        - [variance（型变）](#variance型变)
        - [invariance（固定类型/"rigid"）](#invariance固定类型rigid)
        - [covariance（协变/"flexible"）](#covariance协变flexible)
        - [contravariance（逆变）](#contravariance逆变)
        - [上界/下界（lower bound/upper bound）](#上界下界lower-boundupper-bound)
    - [基础Ending](#基础ending)

<!-- /TOC -->

## 再聊Future

若要问啥是`Future`？请移步入门教学

> 开始前先简单versus下jawa的Future
> jawa8前，Future是同步阻塞的，后来有了CompletableFuture，but实现笨拙，使用起来并不优雅简洁。

言归正传，回到上古时期，下面这段code，分析下有什么问题吗？

```scala
def taskA(): Unit = {
    debug("Starting taskA")
    Thread.sleep(1000) // wait 1secs
    debug("Finished taskA")
  }
 
def taskB(): Unit = {
    debug("Starting taskB")
    Thread.sleep(2000) // wait 2secs
    debug("Finished taskB")
}

def main(args: Array[String]): Unit = {
    debug("Starting Main")
    taskA()
    taskB()
    debug("Finished Main")
}

def debug(message: String): Unit = {
    val now = java.time.format.DateTimeFormatter.ISO_INSTANT
      .format(java.time.Instant.now)
      .substring(11, 23) // keep only time component
    val thread = Thread.currentThread.getName()
    println(s"$now [$thread] $message")
}

> 
08:25:42.958 [run-main-0] Starting Main
08:25:42.958 [run-main-0] Starting taskA
08:25:43.960 [run-main-0] Finished taskA
08:25:43.960 [run-main-0] Starting taskB
08:25:45.961 [run-main-0] Finished taskB
08:25:45.961 [run-main-0] Finished Main
```

看输出是很理想，世界也很美好，但是我们在浪费资源。

> Q: 为什么是浪费资源呢？

进入主题，看下`Future`的定义

```scala
object Future {
  def apply[T](body: => T)(implicit executor: ExecutionContext): Future[T] =
    unit.map(_ => body)
}
```

在《scala小技巧》有提到`by-name parameter`，Future就是这么定义的，很直白联想到为什么可以理解成占位符了。

> Q: 试试把第一段code用future实现一版异步/非阻塞？

怎么应对各种框架的Future？请移步《scala的map、flatMap》

> 到这里，你可能已经见识过Play和slick的Future，当时的你是怎么处理呢？
> Q: 如果用Await.xxx，和第一段代码有差别么？

Futures vs Threads

> future可以比线程更简单的实现并行，你不需要关心lock、queues、volatile、atomic等
> future比线程性价比更高，内存开销、上下文切换开销更小

最佳实践

DO: map, flatMap, FOR-COMPREHENSIONS

DO NOT: Await.xxx

## 满屏幕的implicit是个啥？

在scala教学里，我们也简单聊了`implicit`的使用场景和基本用法，但是可能你看到满屏幕的`implicit`还是很懵。

先聊`implicit parameters`，我们先看一段常见代码

```scala
implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

def getEmployee(id: Int)(implicit e: ExecutionContext): Future[Employee] = ???
def getRole(employee :Employee)(implicit e: ExecutionContext): Future[Role] = ???
val bigEmployee: Future[EmployeeWithRole] =
  getEmployee(100).flatMap { e =>
    getRole(e).map { r =>
      EmployeeWithRole(e.id, e.name,r) 
  }
}
```

getEmployee和getRole都需要future，也就是都需要executionContext，如果我们显示的定义ec参数，那必须每次手动传参，我们的代码就会写成这样

```scala
val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global
val bigEmployee: Future[EmployeeWithRole] =
  getEmployee(100)(ec).flatMap { e =>
    getRole(e)(ec).map { r =>
      EmployeeWithRole(e.id, e.name,r) 
  } (ec)
} (ec)
```

如果一个文件里有更多这样的代码片段，那么就是乏味、啰嗦、冗余的，`implicit`可以在一个scope(context)里消除这些。

> `implicit`是scala一个重要的底层的特性，太多的`implicit`也提升复杂度，降低可读性，所以我们没必要滥用`implicit`，不要为了`implicit`而`implicit`

同样，Play里也是这么传request的

```scala
def index = Action { implicit request =>
  Ok(views.html.index())
}
```

tips:

1. 前面提到了一个`scope`，隐式参数会先在`local scope`查找，比如说declarations、import、inheritance、local package object，其次会去`implicit scope`查找

2. def fun(arg: String)(implicit p1: String)(implicit p2: Int) = ??? // doesn't work

3. def fun(arg: String)(implicit p1: String, p2: Int) = ??? // it just works!

4. class MyClass ()(implicit p1: String, implicit p2: Int) {} // it just works!

再简单看下`implicit conversion`（隐式转换），从名字就可以get到，是可以帮你实现你期望东西（可以是function、type啥的）

```scala
scala> val i: Int = "6" // compile error

scala> implicit def string2int(s: String): Int = Integer.parseInt(s)

scala> val i: Int = "6" // it just works!

scala> val i: Int = string2int("6")
```

## Type-class pattern - 你的第一个FP pattern

`Type-class`源自`haskell`，在`haskell`里的实现是很纯粹的，对应关键词`class`和`instance`，但是在scala里实现`Type-class`是基于`implicit val xxx`，还需要`object`作为type class instance。

简而言之，它使用带有类型参数的泛型隐式，例如Foo[T]，并根据该类型参数解析它们

先来解释几个名词

- traits - type classes
- implicit values - type class instance
- implicit parameters - 使用type class
- implicit classes - 更简单的使用type class

我们来撕一个泛型的JSON序列化case

先定义一个type class

```scala
// Define a very simple JSON AST
sealed trait Json
final case class JsObject(get: Map[String, Json]) extends Json
final case class JsString(get: String) extends Json
final case class JsNumber(get: Double) extends Json
final case object JsNull extends Json

// type classes
trait JsonWriter[A] {
  def write(value: A): Json
}
```

再来个type class instance

```scala
final case class Person(name: String, email: String)

object JsonWriterInstances {
  implicit val stringWriter: JsonWriter[String] =
    new JsonWriter[String] {
      def write(value: String): Json =
        JsString(value)
    }

  implicit val personWriter: JsonWriter[Person] =
    new JsonWriter[Person] {
      def write(value: Person): Json =
        JsObject(Map(
          "name" -> JsString(value.name),
          "email" -> JsString(value.email)
        ))
    }

  // etc...
}
```

> 完成上面的工作，已经可以完成主要工作了，虽然有点丑。

> scala> JsonWriterInstances.stringWriter.write("a")   // res1: Json = JsString("a")

好了，看起来可以派上用场了，implicit一下更graceful

```scala
object Json {
  def toJson[A](value: A)(implicit w: JsonWriter[A]): Json =
    w.write(value)
}

import JsonWriterInstances._

Json.toJson(Person("Dave", "dave@example.com"))
// res1: Json = JsObject(
//   Map("name" -> JsString("Dave"), "email" -> JsString("dave@example.com"))
// )

Json.toJson(Person("Dave", "dave@example.com"))(personWriter)
```

到这里，看起来已经很美好了，跟其他语言的各种JSON库差不多了，我们使用了`implicit parameter`和`implicit conversion`，但是scala type class可以更优秀

我们可以通过`implicit classes`扩展已有的类型，定义interface syntax（`interface syntax`是`cats`体系内叫法）

```scala
object JsonSyntax {
  implicit class JsonWriterOps[A](value: A) {
    def toJson(implicit w: JsonWriter[A]): Json =
      w.write(value)
  }
}

import JsonSyntax._

Person("Dave", "dave@example.com").toJson
// res3: Json = JsObject(
//   Map("name" -> JsString("Dave"), "email" -> JsString("dave@example.com"))
// )
```

> 上面的case是`cats`风格的，`cats`是scala里比较low-level的库，也非常有代表性。`haskell`语言自身内置很多`type-class`，`cats`也搬了很多

okay，整体还是很舒服的。结合实际开发中使用`Play`时，下面的代码就通了

```scala
case class Resident(name: String, age: Int, role: Option[String])

object Resident {
    implicit val residentFormat = Json.format[Resident]
}
```

总结一下无处不在的`Type-class`

- 抽象分离/扩展性/组合性：你不需要去继承、实现特定接口就可以做你期望的事
- 易于重写：重写个`object`就好了
- 降低重复代码
- 类型安全是可维护的

> `implicit`在可读性方面有一些争议，因为太灵活了，而且scala新手是有学习成本的，隐式参数和隐式转换是在编译期，所以也会降低编译效率

## 浅聊泛型

提到泛型，如果和jawa对比的话，scala还是更胜一筹，提到jawa，就会联想到`类型擦除`(type erasure)，没辙，谁叫jawa这么牛逼呢，像better java（c#/kotlin）搞一套新API就欧克了。。
其次，jawa里的泛型是`use-site`，而scala（也包括kotlin/c#）是`declaration-site`

言归正传，泛型是个啥就不多扯了，至于啥generics programming，也是吹着`"让你的类型安全更安全"`的画风，本文主要以读懂scala泛型代码切入

> type parameterized，是理解泛型的正确打开方式

### variance（型变）

其实就是以下述集中方式得到类型的扩展/变化，but jawa在这方面支持上是有那么点灰色。相比之下，大概率可以理解jawa是不变的，jawa里，type array/object arrays已经打破了美好现实。。

### invariance（固定类型/"rigid"）

也就是不变

```scala
trait Queue[T] { ... }
```

### covariance（协变/"flexible"）

如果B是A的子类（包含自己），那么List[B]也是List[A]的协变类型，在scala里用`+`表示

```scala
trait Queue[+T] { ... }
```

### contravariance（逆变）

听起来有点懊恼，但是就按协变反着来就可以，细思一下，我们有很多场景都需要他

如果B是A的子类（包含自己），那么List[A]就是List[B]的逆变类型

```scala
trait Queue[-T] { ... }
```

综上所述，再多get一个事实/法则，输入逆变，输出协变。（感兴趣可以理解下`viriance positions`

```scala
trait Function[-I, +O] {
    def apply(x: I): O
}
```

可能上述代码在jawa里会是

```java
interface Function<T> {
    public static void apply(List<? super T> in, List<? extends T> out)
}
```

### 上界/下界（lower bound/upper bound）

上界: `>:`

```scala
trait Queue[+T] {
    def enqueue[U >: T](x: U) = ???
}
```

> jawa里大概长这样子<? extends T>，可辅助实现协变

下界: `<:`

```scala
def sort[T <: Ordered[T]](xs: List[T]): List[T] = ???
```

> jawa里大概长这样子<? super T>，可辅助实现逆变

## 基础Ending

一起回顾下scala基础10x系列，我们有什么learning

- 概览、基本语法、生态
    > 心中有数，诠释各种what/why
- trait、类、样例类、对象、伴生对象
    > 更好的面向对象，你看到的绝大多数代码是这些
- 集合
    > 奠定编码基础能力
- 模式匹配、Option/Try/Either
    > 一切皆可模式，看明白简单，用好，就进阶了
- Future、implicit
    > get了这些，你会觉得scala很nb
- type class
    > 你遇见的第一个FP模式，在各种scala库里，无处不在。
- 泛型
    > 指引你更懂代码及设计
