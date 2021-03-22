---
marp: true
size: 4:3
theme: default


---

# Scala基础 101

- 介绍
- 语言特点
- 基础入门
- Q&A

![bg h:500 right](https://www.scala-lang.org/resources/img/frontpage/scala-spiral.png)

---
# 由来

- Scala = Scalable(Sca)+Language(la)

- 2001年由Martin Odersky在EPFL开始

- Pizza > Scala

- Scala更有野心
![h:500 bg right](https://upload.wikimedia.org/wikipedia/commons/b/b7/Mark_Odersky_photo_by_Linda_Poeng.jpg)

---

# 为什么是Scala
![bg](https://marp.app/assets/hero-background.jpg)
    
- Scala最终会编译成Java字节码运行在JVM
- Scala可以轻松使用Java生态系统
- Scala非常简洁（分号,getter/setter,return..）
- Scala是高级的，结合OOP和FP
- Scala是静态类型语言，但是看起来很动态

---

![bg auto](https://image.slidesharecdn.com/pragmaticrealworldscalajfokus2009-1233251076441384-2/95/slide-2-1024.jpg)

---

# Scala生态与案例
![bg](https://marp.app/assets/hero-background.jpg)
- 大数据（Spark/Flink/Kafka……）
- Web服务端（Play/Lift/Spray……）
- 分布式、高并发、并行（Akka、ZIO）
- 数据科学（Scala NLP……）
- 跨平台（Scala Native/Scala.js/Scala Android）
- 硬件（SpinalHDL）

---

![bg](https://miro.medium.com/max/1200/1*jzRoZINfqma43uWV6oD0aA.png)

---

# 类型系统

- 类型推断
- 编译期暴露潜在异常
- Null/Nil/Nothing/None 拒绝NPE/undefined

![](https://media.geeksforgeeks.org/wp-content/uploads/20190401170602/ScalaTypeHierarchy.png)

---

# 特性

![bg](https://marp.app/assets/hero-background.jpg)

- 单例 `object`
- 不可变 `immutable`
- 惰性 `lazy, by-name`
- 样例类和模式匹配 `case class`
- 特质 `trait`
- 高阶函数 `map, flatMap, filter, fold, zip...`
- 标点符号自由
- 并发控制 `future, actor`

---

# Hello Scala

![bg](https://marp.app/assets/hero-background.jpg)

创建`HelloScala.scala`

```scala
object HelloScala {
    def main(args: Array[String]): Unit = {
        println("Hello World!")
    }
}
```

编译

```shell
scalac HelloScala.scala
```

运行

```shell
scala HelloScala
```

---

# 数据类型

![bg](https://marp.app/assets/hero-background.jpg)

Scala的一些内置类型，他们都是对象，注意他不是`基本类型`

```scala
val b: Byte = 1
val x: Int = 1
val l: Long = 1
val s: Short = 1
val d: Double = 2.0
val f: Float = 3.0
```

String and Char

```scala
// 16-bit unsigned Unicode character (0 to 2^16-1, inclusive)
0 to 65,535
val c: Char = 'a'
// a sequence of `Char`
val name: String = "Bill"
```

> 可以在Scala REPL练习

---

# 表达式

![bg](https://marp.app/assets/hero-background.jpg)

> `if`表达式是有返回值的

```scala
val a = 1
val b = 2
// 定义Local变量
val minValue = if (a < b) a else b
// 定义函数
def ifThenElseExpression(aBool: Boolean) = if (aBool) 38 else 0
```

> for循环和for表达式

```scala
val nums = Seq(1,2,3)
for (n <- nums) println(n)
val doubledNums = for (n <- nums) yield n * 2
```

---

![bg](https://marp.app/assets/hero-background.jpg)

> `match`表达式 -> 模式匹配

```scala
val monthName = i match {
    case 1  => "January"
    case 2  => "February"
}

def isTrue(a: Any) = a match {
    case 0 | "" => false
    case _ => true
}
```

> `try/catch/finally`表达式

```scala
try {
    // your scala code here
} catch {
    case foo: FooException => handleFooException(foo)
    case bar: BarException => handleBarException(bar)
    case _: Throwable => println("Got some other kind of Throwable exception")
} finally {
    // your scala code here, such as closing a database connection or file handle
}
```

---

# 集合

![bg](https://marp.app/assets/hero-background.jpg)

Scala中有两种类型的集合, 可变的和不可变的

> List & Set & Map

```scala
// List
val numbersList: List[Int] = List(1, 2, 3 ,4)
List(1,2) ::: List(3,4) // List(1, 2, 3, 4)
List(1,2,3,4).reverse // List(4, 3, 2, 1)
// Set
Set(1, 2, 3, 4).head // 1
// Map
val immutableMap = Map(1 -> "a", 2 -> "b")
val mutableMap = collection.mutable.Map(1 -> "a", 2 -> "b")
```

> Tuple

```scala
val t1 = (1, "A")   // val t1 = Tuple2(1, "A")
t1._1   // 1
t1._2   // A
```

---

# 类、方法

![bg](https://marp.app/assets/hero-background.jpg)

定义一个Person，构造函数有两个参数，还有两方法
> 不需要显示声明getter/setter等

```scala
class Person(var firstName: String, val lastName: String) {
    def growing(): Unit = {
        println(s"$firstName is growing")
    }
    def sleeping(h: Int = 8): String = {
        s"I'm sleeping $h hours" 
    }
}

val p = new Person("Bill", "Panner")
println(p.firstName)
p.firstName = "Bob" // 可以
p.lastName = "James"    // 不可以，因为是val

p.growing
p.sleeping(9)
p.sleeping()    // 默认是8
```

---

# Trait Mixins
![bg right 90%](https://miro.medium.com/max/618/0*7C49Qji_qvFpHYuA)
`trait`很像`interface`，但是`Minxin`起来很强大

```scala
trait Speaker {
    def speak(): String
}

trait TailWagger {
    def startail(): Unit = println("tail is wagging") 
    def stopTail(): Unit = println("tail is stopped")
}

class Dog extends Speaker with TailWagger {
    // Speaker
    def speak(): String = "Woof!"
}

class Cat extends Speaker {
    def speak() = "Meowing"
}

val dog = new Dog
val cat = new Cat with TailWagger
val cat2 = new Cat
```

---

# Be Functional

![bg](https://marp.app/assets/hero-background.jpg)

如果⼀⾸歌既不是新歌又不会过时，那它就是民谣。

> 高阶函数 - 函数作为参数或返回值

```scala
val meat = Seq("pork", "beef", "chicken")
val map = meat.map(name => s"great $name")
val filter = meat.filter(_ == "pork")
val fold = meat.foldLeft(0)((acc, x) => acc + 1)

def mathOperation(name: String): (Int, Int) => Int = (x: Int, y: Int) => {
  name match {
    case "addition" => x + y
    case "multiplication" => x * y
    case "division" => x/y
    case "subtraction" => x - y
  }
}

def add: (Int, Int) => Int = mathOperation("addition")
def mul: (Int, Int) => Int = mathOperation("multiplication")
def div: (Int, Int) => Int = mathOperation("division")
def sub: (Int, Int) => Int = mathOperation("subtraction")
```

---

![bg](https://marp.app/assets/hero-background.jpg)

> 闭包

```scala
var factor = 3
val multiplier = (i:Int) => i * factor
scala> multiplier(10)
scala> res: Int = 100
```

> Curring（柯里化/咖喱） & Partial Application（部分应用）

```scala
val sum: (Int, Int) => Int = (x, y) => x + y 
// function def
val curriedSum: Int => Int => Int = x => y => x + y


sum(1,2) shouldBe 3
curriedSum(1)(2) shouldBe 3

// partial
def curriedSum(x: Int)(y: Int): Int = x + y
val increment: Int => Int = curriedSum(1)
val inc = curriedSum(1) _
scala> inc(2)
```

---

> 标点符号自由 - Infix Notation

```scala
val x = 1
val y = 2
scala> x + y
// `+`是一个函数名称
scala> x.+(y)
// 定义在这里
final abstract class Int() extends scala.AnyVal {
    def +(x : scala.Int) : scala.Int
    //...
}
```

> 不可变数据类型 - 语义拷贝/流畅

```scala
val update = device
    .filter(_.hierarchyId === hierarchyId)
    .map(d => (d.name))
    .update(name)
```

---

# Enjoy scala! :v: <!--fit-->