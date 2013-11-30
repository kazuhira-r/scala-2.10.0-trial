//import scala.language.postfixOps
/*
def printTail(): Unit = {
  implicit val postfixOps = scala.language.postfixOps
  //import scala.language.postfixOps
  println(List(1, 2, 3) tail)
}

printTail()
*/

//import scala.language._
println(List(1, 2, 3) tail)
println(4 twice)

trait Foo {
  type MyMap[A, B] <: Map[A, B]
}

class IntWrapper(val underlying: Int) {
  def twice: Int = underlying * 2
}

implicit def intToWrapper(i: Int): IntWrapper = new IntWrapper(i)
