package example

trait B {
  this: A with Cake =>
  def b = ???
}

trait A {
  this: B =>
  def a = ???
}

trait Cake {
  this: B =>
  def cake = ???
}

class AClass extends A with B {

}

