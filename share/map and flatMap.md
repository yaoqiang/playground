# Scala中map/flatMap的

<!-- TOC -->

- [Scala中map/flatMap的](#scala中mapflatmap的)
    - [目的](#目的)
    - [函数式编程范式小知识](#函数式编程范式小知识)
    - [map/flatmap on List[+A]](#mapflatmap-on-lista)
    - [map/flatmap on Option[+A]](#mapflatmap-on-optiona)
    - [map/flatmap on Future](#mapflatmap-on-future)

<!-- /TOC -->

## 目的

首先确定无疑，这里不是讨论`HashMap`！
`map`和`flatMap`都是高阶函数，在我们日常开发中非常常见，如果你常年写jawa，积累了丰富OOP思想，那你大概率会被困扰，希望这篇文章可以帮助你

- 集合中如何使用
- 其他语境里如何使用

## 函数式编程范式小知识

> 能map的就是`functor`（函子），能flatMap的就是`monad`（单子）
functor和monad是函数式编程的核心概念，函子和单子都有自己的法则，我们先记住上述的使用技巧，可以把它们当做是个`盒子`/`context上下文`。
理论上，我们可以在`盒子`外进行函数操作、组合子操作来达成目的。

简单定义一个泛型monad为M[A]，A是内部的具体类型，我们常见的有：

- List[String]
  - M[X] = List[X], A = String
- Option[Int]
  - M[X] = Option[X], A = Int
- Future[String => Boolean]
  - M[X] = Future[X], A = (String => Boolean)

## map/flatmap on List[+A]

集合中使用map比较容易理解，就是lambda。

看一下集合的map定义，看类型签名很重要。

```scala
trait Iterator[+A] {
    def map[B](f: (A) => B): Iterator[B]
    def flatMap[B](f: (A) => IterableOnce[B]): Iterator[B]
.....
```

map函数接收一个函数入参，这个函数入参就是个lambda，然后返回一个新的集合。
举个例子

```scala
val result: List[String] = List(1,2,3).map(v => v.toString)
```

你也可以这样自己实现一个单纯的map，和上述代码结果目的一样

```scala
def map[A,B](l: List[A])(f: A => B): List[B] = ???

map(List(1,2,3))(v => v.toString)
```

如果在jawa，实现类似map的目的，你可能会这么写，发现并没有scala优雅

```java
List<String> strings = new ArrayList<>();
for(Integer i : List.of(1,2,3)) {
    ints.add(i.toString());
}

or

List<String> strings = List.of(1,2,3)
    .stream()
    .map(x -> x.toString())
    .collect(Collectors.toList());

```

> 在写scala时，尽可能的杜绝var xxx, update in loop

flatMap和map非常像，不同的是flatMap改变了List原有的序列，先map再铺平（flatten）

```scala
scala> val fruits = Seq("apple", "banana", "orange")
fruits: Seq[java.lang.String] = List(apple, banana, orange)

scala> fruits.map(_.toUpperCase)
res0: Seq[java.lang.String] = List(APPLE, BANANA, ORANGE)

scala> fruits.flatMap(_.toUpperCase)
res1: Seq[Char] = List(A, P, P, L, E, B, A, N, A, N, A, O, R, A, N, G, E)
```

看起来有点怪，换个栗子，如果你想做一些运算，类似笛卡尔积这样的case，用flatMap就舒爽多了

```scala
scala> List(1, 2, 3, 4).map(x => List(x-1, x+1)) 
res0: List[List[Int]] = List(List(0, 2), List(1, 3), List(2, 4), List(3, 5))

scala> List(1, 2, 3, 4).flatMap(x => List(x-1, x+1)) 
res1: List[Int] = List(0, 2, 1, 3, 2, 4, 3, 5)
```

> 大部分情况，我们需要组合使用map/flatMap，并且结合for-comprehension来推理、组合

## map/flatmap on Option[+A]

首先，还是看类型签名

```scala
def map[B](f: A => B): Option[B]
def flatMap[B](f: A => Option[B]): Option[B]
```

乍一看，这两函数太像了，区别就在于lambda入参的返回值，map直接返回B，flatmap返回Option[B]，接下来看点具体例子

map还是比较直观的，在Option里做运算，但是你如果想取出option里的东西和其他Option操作，就又会陷入其他问题

```scala
scala> Some(10)
res4: Some[Int] = Some(10)

scala> res4.map(x => x / 2)
res5: Option[Int] = Some(5)

scala> res4.map(x => x / 2.0)
res6: Option[Double] = Some(5.0)
```

Option op Option

你可能第一反应会这样（一）

```scala
Some(1).getOrElse(0) + Some(1).getOrElse(0)
```

> 不能评价什么，因为这是大多数的选择

或许你也会这样（二）

```scala
Some(1).map { a =>
    val result: Option[Int] = Some(1). map { b =>
        a + b
    }
    result.getOrElse(0)
}

or 如果你已经get了flatmap/for推导

Some(1).flatMap { a =>
    Some(1).map {b =>
        a + b
    }
}

for (x <-one; y <- two) yield x+y
```

上述几种实现方式

- 第一种不是良好的编程风格，在部分场景会出现未知问题
- 第二种不够优雅，for还可以

我们也可以使用`cats`，是不是很香?

```scala
scala> 1.some |+| 2.some
res3: Option[Int] = Some(3)
```

再看下flatMap，其实和集合的行为一样

```scala
scala> List(Some(12), None, Some(90)).flatMap(x => x)
res2: List[Int] = List(12, 90)
```

## map/flatmap on Future

> 我们使用的框架到处都是Future（如play/slick/akka等），就拿play来说，Action默认的Response要的是一个Future[Result]，（因为是async/non-blocking），或者slick在执行完DBIO后，会丢回一个Future[X]，或者一个HTTP调用也会拿到一个Future[X]，如果你用OOP的思想去写代码，你就莫名的想要拿到Future里的`X`，而且非常想。怎么破？
你可能会写出满屏的`Await.xxx`，流畅的OOP风，丢掉了non-blocking的优势和函数式的乐趣
或者停下来仔细研究下，Future这个盒子的map和flatMap，还有for推导

言归正传，还是先看类型签名，和List、Option的没啥不同，唯一区别的是`ExecutionContext`（这个可以在Future教学中找到答案

```scala
def map[S](f: T => S)(implicit executor: ExecutionContext)
def flatMap[S](f: T => Future[S])(implicit executor: ExecutionContext)
```

看下map和flatMap，没啥大不同

```scala
// map
scala> Future(12/2).map(_ * 3)
res0: scala.concurrent.Future[Int] = Future(Success(18))
scala> Future(12/0).map(_ * 3)
res1: scala.concurrent.Future[Int] = Future(Failure(java.lang.ArithmeticException: / by zero))
 
//flatmap
scala> val twelveOverTwo = Future(12/2).flatMap(n => Future(n.toString))
twelveOverTwo: scala.concurrent.Future[String] = Future(<not completed>)
// twelveOverTwo has not completed yet – let give it another try
 
scala> twelveOverTwo
twelveOverTwo: scala.concurrent.Future[String] = Future(Success(6))
// twelveOverTwo has now completed successfully
 
scala> Future(12/0).flatMap(n => Future(n.toString))
res0: scala.concurrent.Future[String] = Future(Failure(java.lang.ArithmeticException: / by zero))
```

> 陷阱!!! 当你掌握了map，你可能会写成前者，绕着绕着就懵了。其实只要记着类型签名就好了，套套签名+编译error足以解决，通常情况后者写法都可以满足

```scala
// wrong ❎
f map {
    f2 map {
        f3 flatMap {
            
        }
    }
}

// right ✅
f flatMap {
    f2 flatMap {
        f3 map {
            
        }
    }
}
```

for推导

```scala
val f: Int => Future[String] = bound => Future(bound.toString) 
for {
  bound <- Future(10)
  out <- f(bound)
} yield out
```

组合后再map、flatmap - zip

cats - OptionT
