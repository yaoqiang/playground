# scala - class/case class/object/case object/sealed

<!-- TOC -->

- [scala - class/case class/object/case object/sealed](#scala---classcase-classobjectcase-objectsealed)
    - [scala class](#scala-class)
    - [case class](#case-class)
    - [object/case object](#objectcase-object)
    - [案例](#案例)
    - [附](#附)

<!-- /TOC -->

## scala class

概念请移步scala入门教学

- vs jawa class
    
    没啥太大差，方便了一丢丢

## case class

概念请移步scala入门教学

- vs java POJO/DTO

    有点差，强大了许多许多
    
    - 你不需要自己再写getter/setter啥的了，lombok也可以扔了
    - 结合ADT（代数数据类型），模式匹配很好使

Question
1. 既然有了class为啥还搞case class?
2. 写几个class，javap一下究竟是什么？

## object/case object

概念请移步scala入门教学

object在各种代码中出镜率很高，他的应用场景也很多，比如说单例对象、工厂、伴生对象、甚至单例类型

> scala里没有`static`关键字，因为scala团队认为`static`不是纯的OO语言，在jawa里，你不需要实例化类，就可以通过`static`访问类成员。再者还有个现实问题，`static`是有点危险的，因为他导致生产故障的几率有点大，所以一般情况对`static`的使用是有很多规范

Question
1. case object和object有啥差？
2. 请尝试javap一个单例对象，看看究竟有何不同？

## 案例

先来看一个栗子，假设我们需要提供一个命令API，处理label是移动多少距离，还是旋转多少度。

```scala
final case class Command(label: String, meters: Option[Int], degrees: Option[Int])
```

假设label: foo移动了100米，那么

```scala
Command("foo", Some(100), None)
```

假设label: bar旋转了90度，那么
```scala
Command("bar", None, Some(90))
```

上面两种case看起来没毛病，但是如果调用API时，丢过来的是这样

```scala
Command("foo", None, None)
Command("bar", Some(1), Some(2))

```

看起来，我们只能在屏幕里堆if/else了。
> 如果是jawa，你可能会说，我有23种设计模式呀！你可以试想下，在这种场景，你会怎么做？和下面的方式对比一下呢？

```scala
sealed abstract class Command
final case class Move(label: String, meters: Int) extends Command
final case class Rotate(label: String, degrees: Int) extends Command

def print(cmd: Command) = cmd match {
  case Move(label, dist)    => println(s"$label Moving by ${dist}m")
  case Rotate(label, angle) => println(s"$label Rotating by ${angle}°")
}


scala>
print(Move("foo", 100).asInstanceOf[Command])
foo Moving by 1m
print(Rotate("bar", 90).asInstanceOf[Command])
bar Rotating by 90°
```

:)， it just works!

> 上面代码就有那么点`ADTs`了，很多框架库都是这么写的，可以进阶一些手撕JSON Parse等深入巩固一下；这种风格有很多优势，比如不用担心调用者会传丢啥了，逻辑代码不需要满屏幕的if了，最重要的是真的不用担心祖传代码了

Question:
1. 如果print函数里的模式匹配去掉一个case Rotate会有问题吗？
2. sealed的作用是什么？去掉可以吗？有什么影响？
3. 用本篇get的内容，实现一个枚举？

**补充**

trait/case class/case object混用

```scala
sealed trait Coffee

case class Cappuccino(suger: Int, milk: Int) extends Coffee
case object Americano extends Coffee
case object Espresso extends Coffee
```

## 附
[Java + Scala互操作](https://twitter.github.io/scala_school/java.html)
