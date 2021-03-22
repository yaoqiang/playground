---
marp: true
---

# Algebraic Data Types(ADTs) - 代数数据类型

代数数据类型（简称ADT）是一种结构化数据的方法。它们在Scala中的广泛应用，主要是由于它们在模式匹配方面的出色工作，以及使用它们使非法状态无法表示是多么容易

ADT有两种基本类型

- product types

```scala
final case class Foo(b1: Boolean, b2: Boolean)

Foo(true, true)
Foo(true, false)
Foo(false, true)
Foo(false, false)
```

- sum types
