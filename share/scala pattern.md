---
marp: true
---

Scala里的设计模式
===

![h:250](https://www.scala-lang.org/resources/img/frontpage/scala-spiral.png)

![h:300 bg right 95%](https://4.bp.blogspot.com/-Mrv55XLP0y4/WwwaPGRIunI/AAAAAAAACV8/s3JTDambNs82GpY0_ZiITuqOrQy5ilG9QCLcBGAs/w1200-h630-p-k-no-nu/gof-design-pattern-category-diagram.PNG)

##### OOP(Java)常见设计模式在scala是怎样存在

###### by yao

---

# 聊聊这些

- :key: 单例模式
- :tokyo_tower: Builder模式
- :tv: 装饰器模式
- :cake: Cake pattern
- and more...

`有工程的地方就有设计模式`

---

# 单例模式

Java中的单例模式

1. 饿汉模式
2. 懒汉模式
3. 双重检验锁模式
4. 静态内部类
5. 枚举实现

![bg right 95%](https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fbpic.588ku.com%2Fart_origin_min_pic%2F19%2F12%2F17%2F59e7b6ad7deee8c569f60ac745c31837.jpg&refer=http%3A%2F%2Fbpic.588ku.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1614677744&t=81a00d3a0522a46ff1b0a9faf71a2545)

---

Scala可以这样

```scala
class Instance {

}

object Instance {
    lazy val INSTANCE = new Instance()
}

```

`是不是再也不用担心面试官问单例了?`
> scala里没有`static`关键字

---

# Builder模式

Java里是这样

```java
public final class Car {
    final String carBody;
    final String tyre;
    public Car() {
        this(new Builder());
    }
    public Car(Builder builder) {
        this.carBody = builder.carBody;
        this.tyre = builder.tyre;
    }
    public static final class Builder {
        String carBody;
        String tyre;
        public Builder() {
            this.carBody = "黑色";
            this.tyre = "米其林";
        }
        public Builder carBody(String carBody) {
            this.carBody = carBody;
            return this;
        }
        public Builder tyre(String tyre) {
            this.tyre = tyre;
            return this;
        }
        public Car build() {
            return new Car(this);
        }
    }

    public static void main(String[] args) {
        new Car.Builder().carBody("白色").tyre("倍力耐").build();
    }
}
```

![bg right 95%](https://media.geeksforgeeks.org/wp-content/uploads/uml-of-builedr.jpg)

---

Scala的实现

```scala
case class Car(carBody: String = "黑色", typo: String = "米其林")

obejct Main extends App {
    val car = new Car()
    car.copy(carBody = "白色", typo = "米其林)
}
```

`case class的福利:`
> 1.帮你实现了equals/toString/hashCode/immutable/Serializable
> 2.它也是Prototype模式的一种载体

---

# 装饰器模式

装饰器模式（Decorator Pattern）允许向一个现有的对象添加新的功能，同时又不改变其结构

```java
public interface Shape {
    void draw();
}
public class Circle implements Shape {
    @Override
    public void draw() {
       System.out.println("Shape: Circle");
    }
}
public class Rectangle implements Shape {
   @Override
   public void draw() {
       System.out.println("Shape: Rectangle");
   } 
}

...

Shape circle = new Circle();
Shape redCircle = new RedShapeDecorator(new Circle());
Shape redRectangle = new RedShapeDecorator(new Rectangle());

...

```

![bg right 95%](https://www.tutorialspoint.com/design_pattern/images/decorator_pattern_uml_diagram.jpg)

---

Scala可以这样

```scala
object MixinPattern extends App {
  trait ShapeDecorator extends Shape { self =>
    private def setRedBorder(s: Shape): Unit = println("red border.")
    abstract override def draw(): Unit = {
      super.draw()
      setRedBorder(self)
    }
  }
  val circle = new Circle with ShapeDecorator
  circle.draw()
}
```

`What??? Mixin!!! trait真香`:ox:

> Self-types: Scala编程语言有许多方法来处理类型之间的依赖关系。所谓的`self-type`允许我们使用traits和mixin的概念来声明依赖关系

---

# Cake pattern

这是啥模式???
> Scala中比较经典的一种依赖注入的模式,属于「编译时依赖注入」的一种，她不需要依赖 DI 框架

1. Self-type
2. Cake pattern = Dependency Injection using self-type
3. 更多的DI选择手工注入/Guice/Macwire

![bg right 95%](https://miro.medium.com/max/1400/0*_Zbyhqw2SCjFEoZB)

---

## Self-type

```scala
trait MyTrait { self => }
// is equivalent to
trait MyTrait2 {
  private val self = this
}
```

```scala
trait Function1Option[-A, +B] { self =>
  def apply(a: A): Option[B]
  final def andThen[C](that: Function1Option[B, C]): Function1Option[A, C] =
    new Function1Option[A, C] {
      override def apply(a: A): Option[C] = this.apply(a) match {
        case Some(b) => that.apply(b)
        case None => None
      }
    }
}
// <console>:21: error: type mismatch;
//  found   : b.type (with underlying type C)
//  required: B
//                case Some(b) => that.apply(b)
//                                           ^       
...
override def apply(a: A): Option[C] = self.apply(a) match { ...
```

---

## Cake pattern example

```scala
trait C {
  this: A with Cake =>
  def c = ???
  //cyclic dependency, access trait Cake
  cake
}
trait B {
  this: C =>
  def b = ???
}

trait A {
  this: B =>
  def a = ???
  // access members of trait B
  b
  // access members of trait C???
  c
}
// 如果Cake依赖了A, 则在Cake的实现中必须把A mixin进去, 否则编译失败(error: illegal inheritance, self-type xxx),以此类推
class Cake extends A with B with C {
  def cake = ???
}
```

> 在playframework中默认的DI是Guice(runtime DI), 也可以用cake来实现一个play的DI
---

# Enjoy scala! :v: <!--fit-->

**一些参考**

https://docs.scala-lang.org/tour/singleton-objects.html

https://www.geeksforgeeks.org/builder-design-pattern/

https://levelup.gitconnected.com/dependency-injection-using-self-types-in-scala-cake-pattern-67c76e603e5b

https://www.tutorialspoint.com/design_pattern
