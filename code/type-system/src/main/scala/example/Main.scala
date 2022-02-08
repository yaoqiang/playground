package example

import cats.effect._
import cats.implicits._
import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import eu.timepit.refined.collection.Contains
import eu.timepit.refined.types.string.NonEmptyString
import io.estatico.newtype.macros._
import shapeless._, nat._, ops.nat._
import eu.timepit.refined.boolean._
import eu.timepit.refined.string._
import eu.timepit.refined.collection._


object normal {
  case class User(username: String, email: String)
  def lookup(username: String, email: String): Option[User] = ???
}


object valueclasses {
  case class User(username: Username, email: Email)

  def lookup(username: Username, email: Email): User =
    User(username, email)

  case class Username private (val value: String) extends AnyVal
  case class Email private (val value: String)    extends AnyVal

  def mkUsername(value: String): Option[Username] =
    if (value.nonEmpty) Username(value).some
    else none[Username]

  def mkEmail(value: String): Option[Email] =
    if (value.contains("@")) Email(value).some
    else none[Email]

  def foo(username: String, email: String) =
    (
      mkUsername(username),
      mkEmail(email)
    ).mapN { case (username, email) =>
      lookup(username, email)
    }

  def bar(username: String, email: String) =
    (mkUsername(username), mkEmail(email)).mapN { case (username, email) =>
      lookup(username.copy(value = ""), email)
    }
}

object newts {
  @newtype case class Username(value: String)
  @newtype case class Email(value: String)

  val foo = Username("fp")

  //val bar = foo.copy(value = "") // copy does not exist
}

object refinement {
  type Username = String Refined Contains['d']

  def foo(username: Username): String =
    username.value

}

object refinednewts {
  type BrandType = String Refined (NonEmpty And Contains['d'] And MinSize[2])

  @newtype case class Brand(value: BrandType)
  @newtype case class Category(value: NonEmptyString)

  case class Vehicle(brand: Brand, category: Category)
}


trait SumEq5[A]

object SumEq5 {
  def apply[L <: HList](implicit ev: SumEq5[L]): SumEq5[L] = ev

  implicit def sumEq5AB[A <: Nat, B <: Nat]
    (implicit ev: Sum.Aux[A, B, _5]): SumEq5[A :: B :: HNil] =
      new SumEq5[A :: B :: HNil] {}
}

object Main extends IOApp.Simple {

  SumEq5[_0 :: _5 :: HNil] 
  

  // valueclasses run
  val run = for {
    foo <- IO(valueclasses.foo("a", "b@example.com"))
    _ <- IO(println(foo))
    bar <- IO(valueclasses.bar("a", "b"))
    _ <- IO(println(bar))
  } yield ()

  // refinement & newtype
  val run2  = for {
    _ <- IO(refinement.foo("ad"))
    _ <- IO(refinednewts.Vehicle(refinednewts.Brand("abcd"), refinednewts.Category("a")))
  } yield ()


}

