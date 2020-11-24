## Scala 代码规约

引用Twitter Effective Scala开场白，也是我们的方向

> 这是一篇“活的”文档，我们会更新它,以反映我们当前的最佳实践，但核心的思想不太可能会变： 永远重视可读性；写泛化的代码但不要牺牲清晰度； 利用简单的语言特性的威力，但避免晦涩难懂（尤其是类型系统）。最重要的，总要意识到你所做的取舍。一门成熟的(sophisticated)语言需要复杂的实现，复杂性又产生了复杂性：之于推理，之于语义，之于特性之间的交互，以及与你合作者之间的理解。因此复杂性是为成熟所交的税——你必须确保效用超过它的成本。
>
> 玩的愉快。



### 命名

Scala的命名规则有点像Java

```scala
// Package names should be all lowercase letters
package thisisapackage
// Class names should begin with an uppercase letter
class ThisIsAClass
// Method names should begin with a lowercase letter
def thisIsAMethod
// Constant names should begin with an uppercase letter
// This convention is *different from Java's*
// in which constants are in all caps
val MyConstant
// The name of everything else should begin with a lowercase letter
val thisIsNotAConstant
```

其他一些Tips

1. 使用通用的缩写，避开隐秘难懂的缩写：例如每个人都知道 `ok`,`err`, `defn`等缩写的意思，而`sfri`是不常用的。
2. 对作用域较短的变量使用短名字：`i`s, `j`s 和 `k`s等可出现在循环中。
3. 对作用域较长的变量使用长名字：外部APIs应该用长的，不需加以说明便可理解的名字。例如：`Future.collect` 而非 `Future.all`



### 格式化

Scala IDE当前主流的是IntelliJ IDEA Scala插件和Metals，[Metals](https://github.com/scalameta/metals)支持VSCode/IDEA/VIM/Emacs……

两者都支持[scalafmt](https://github.com/scalameta/scalafmt)，我们统一使用[scalafmt](https://github.com/scalameta/scalafmt)，方便你我他。

在你保存退出前，记得format

格式化也包含一些好味道的代码，后续根据我们的实践陆续增加



### 集合

*优先使用不可变集合*。不可变集合适用于大多数情况，让程序易于理解和推断，因为它们是引用透明的( referentially transparent )因此缺省也是线程安全的。

*使用可变集合时，明确地引用可变集合的命名空间*。不要用使用import scala.collection.mutable._ 然后引用 Set ，应该用下面的方式替代：

```scala
import scala.collection.mutable
val set = mutable.Set()
```



### 函数式编程

**1.尽量使用val和immutable的数据结构**

**2.尽量不要在loop中update一个var值**

```scala
// bad
var sum = 0
for (elem <- elements) {
  sum += elem.value
}

// better
val sum = elements.foldLeft(0)((acc, e) => acc + e.value)

//even better, 使用built-in函数
val sum = elements.map(_.value).sum
```

**3.不要在case class中使用var**

**4.使用Option，替代null**

**5.不要使用Option.get，尽量使用Option.getOrElse、模式匹配**

**6.尽量使用built-in高阶函数**

**7.使用Tuple时，尽量清晰声明Tuple的意义**

```scala
// bad
someCollection.map(_._2)

// better
val tuple = ("Bob", 18)
val (name, age) = tuple
```



### 面向对象编程

依赖注入

trait





[Effective Scala]: https://twitter.github.io/effectivescala/
[Brown CS]: http://cs.brown.edu/courses/

[Scala best practices]: https://github.com/alexandru/scala-best-practices

