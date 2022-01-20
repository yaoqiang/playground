# Typeclass

类型类，第一次听到这个名词时，拗口又困惑，但是稍做功课后，不明觉厉啊！

简单讲，是FP领域中围绕类型系统搞出来的一种设计模式！为了多态、扩展、派生……
> 类型类不是OOP的`class`啊`type`啊，我们可以先把`typeclass`理解为OOP中的`interface`，实则`typeclass`更加强大

## 从哪里来，到哪里去？

类型类首先在 Haskell 编程语言中实现，
前有[ML(Meta Language)](https://baike.baidu.com/item/ML%E8%AF%AD%E8%A8%80/7526775?fr=aladdin)提出了`eqtypes`扩展，随后在Haskell发扬光大，高阶多态、参数多态、隐士参数、泛型等等。


如今，诸多现代编程语言借鉴`typeclass`思想，在我眼里他们都差不多。
> Typeclass，Trait，Protocal，Concept都在不同维度解决多态这个事情，相信未来的践行者会更多

## Haskell Style

先拿Haskell来聊，虽然我的Haskell入门级，但毕竟haskell是typeclass鼻祖。

**为什么需要typeclass？**

从一个 `==` 运算符说起，Haskell的设计者忽略了运算符 `==` 的实现（传说出于深不可测的原因，我想可能是为了typeclass），在我们常见的编程语言中 `==` 是keyword，但在Haskell中，我们接受了现实后，来动手实现一个 `==`

```haskell
-- 定义类型：语言
data Shape = Ellipse | Rectangle

-- 实现Eq
shapeEq :: Shape -> Shape -> Bool
shapeEq Ellipse Ellipse = True
shapeEq Rectangle Rectangle = True
shapeEq _ _ = False

-- 跑一下
ghci> shapeEq Rectangle Rectangle
True
ghci> shapeEq Ellipse Rectangle
False
```


问题来了，我们不能为每一种需要Eq的类型就实现一把，所以就有了Haskell的`Eq`类型类，并且他实现了`==`，舒畅多了

```haskell
-- :t，查看函数`==`的类型签名
ghci> :t (==)
(==) :: (Eq a) => a -> a -> Bool  

-- `=>`前面的Eq a是一个类型约束

ghci> 5 == 5  -- Int实现了Eq
True  
ghci> 5 /= 5  
False  
ghci> 'a' == 'a'  -- Char实现了Eq
True  
ghci> "Ho Ho" == "Ho Ho"  -- String实现了Eq
True  
ghci> 3.432 == 3.432  -- Float实现了Eq
True  
```

上述类型都实现了`Eq`，在Haskell中叫做`instance`，即类型类`Eq`的实例

```haskell
instance Eq Integer where 
  x == y                =  x `integerEq` y
instance Eq Float where
  x == y                =  x `floatEq` y
```

> 注意，这里的`==`是个函数名称，所以`+`，`-`啥的也一样，Scala也是偷师这些

**Haskell常见的typeclass**

- `Show` 有点toString的意思，
- `Ord` 有点compare的意思，
- `Read` Show的对立面
- `Functor`，`Monad` 一些FP的东西（范畴论
- more……


基于这些基础支撑，typeclass还可以高阶多态、参数多态等等，感兴趣的同学深入研究下，也正是这些场景使得typeclass要比interface更强大

## Scala Style

Scala并没有完全偷师Haskell，在Scala 3之前需要靠`trait`和`implicit`来实现typeclass

这一pa，直接用[cats](https://typelevel.org/cats)聊更合适，cats偷师了绝大多数haskell的typeclass，非常适合做较底层的代码库（各大scala知名framework都有cats的身影），

cats里定义类型类的三部曲

- 定义类型类trait，类型约束、函数
- 定义类型类实例instance，具体实现
- 定义syntax，易用

我们来看看cats里的类型类`Eq`

```scala
// 第一步
package cats

trait Eq[A] {
    def eqv(a: A, b: A): Boolean
}

// 第二部
package cats.instance
package int

trait IntInstance {
    // some other thing
}

class IntEq extends Eq[Int] {
    override def eqv(x: Int, y: Int): Boolean = x == y
}

// 第三步
package cats.syntax

trait EqSyntax {
    implicit def catsSyntaxEq[A: Eq](a: A): EqOps[A] = new EqOps[A](a)
}

final class EqOps[A: Eq](lhs: A) {
    def ===(rhs: A): Boolean  = Eq[A].eqv(lhs, rhs)
    def eqv(rhs: A): Boolean  = Eq[A].eqv(lhs, rhs)
    // some other thing
}
```

用起来相当流畅

```scala
import cats._
import cats.instance.int._  // for instance
import cats.syntax._    // for syntax

// `===`实则是上面定义定义的函数，123是Int类型，本身是没有`===`函数的，所以说syntax易用
123 === 123
// res: Boolean = true

// 拥有类型约束
123 === "123"
// error: type mismatch:
// found    : String("123")
// required : Int
// 123 === "123"
```

cats提供了非常多的typeclass，对于做类库相当有用，CRUD场景也稍有助攻

Scala 3 的typeclass有一些变化（语法，但万变不离其宗

> 开始接触这些时，大部分是程序员嗅觉的新鲜感，深入了解后，换种样板代码写，极度舒适

## Rust Style

Rust本身偷师Haskell很多东西，强大的类型系统，ADTs，模式匹配，面向表达式等等。

Rust的trait非常强大，相当泛滥，学会使用trait，配上`deriving`就可以更上一层楼！

在Rust中搞typeclasss也是依赖trait，再来看看Rust中的类型类`Eq`的定义

```rust
pub trait PartialEq {
  fn eq(&self, other: &Self) -> bool;
  fn ne(&self, other: &Self) -> bool { ... }
}
pub trait Eq: PartialEq<Self> { }
```

实现`Eq` trait的部分片段，可以理解为instance，[Rust标准库Eq trait](https://doc.rust-lang.org/std/cmp/trait.Eq.html)
```rust
// some else
eq_impl! { () bool char usize u8 u16 u32 u64 u128 isize i8 i16 i32 i64 i128 }
// some else
```

同样 `==` 运算符背后是可以被PartialEq Overload的，譬如代码 `1 == 1`，结合类型推导，如果你有具体类型实例的实现，实则是找到上述具体类型实现，源码中依赖了诸多宏，太麻烦了，我们实现一个自己的instance

```rust
fn main() {
    let b1 = Book{ isbn: 1, format: "eBook"};
    let b2 = Book{ isbn: 1, format: "eBook"};
    println!("{}", b1 == b2);
    // true
}

struct Book {
    isbn: i32,
    format: &'static str,
}
impl PartialEq for Book {
    fn eq(&self, other: &Self) -> bool {
        self.isbn == other.isbn
    }
}
impl Eq for Book {}
```

如果自定义的结构未实现`Eq` |  `PartialEq`，则编译报错。

```
error[E0369]: binary operation `==` cannot be applied to type `Book`
 --> src\main.rs:4:23
  |
4 |     println!("{}", b1 == b2);
  |                    -- ^^ -- Book
  |                    |
  |                    Book
  |
  = note: an implementation of `std::cmp::PartialEq` might be missing for `Book`
```

Rust内置了无尽的`trait`，配合派生、泛型可以写出更好架构的代码，并且收到FP Complete的青睐，还是非常有FP的体验的


## Javascript Style

目前Typescript的interface加上一些技巧也可以有一些不错的多态支撑（但有限，

Javascript也有了这方面的[ECMASCript Protocal提案](https://github.com/tc39/proposal-first-class-protocols)，相信未来某一日也会步入正统


感兴趣的可以try一下

## 啰啰嗦嗦

到这里，本质上也可以说明`typeclass`是什么，解决了什么问题，作为一个编程语言体验者的学习看法，它刨根还是解决了【多态】这个复杂问题，并且不依赖继承，也是我们CRUD仔开启另一种样板代码的道路。

## 一些参考

https://xxchan.github.io/cs/2020/08/17/polymorphism.html

https://www.parsonsmatt.org/2017/01/07/how_do_type_classes_differ_from_interfaces.html

[Concepts vs Typeclasses vs Traits vs Protocols](https://www.youtube.com/watch?v=E-2y1qHQvTg)

https://doc.rust-lang.org/book/appendix-02-operators.html