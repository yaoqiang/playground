package example

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
  // c
}

class Cake extends A with B with C {
  def cake = ???
}

object Main {
  def main(args: Array[String]) {
    val cake = new Cake
  }
}

trait Shape {
  def draw(): Unit
}
class Circle extends Shape {
  override def draw() = println("circle")
}



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
