// 以下のimport文を入れないと、Scala 2.10.0では警告が出ます
import scala.language.implicitConversions

class IntWrapper(val underlying: Int) {
  def twice: Int = underlying * 2
}

object IntWrapper {
  implicit def int2wrapper(i: Int): IntWrapper =
    new IntWrapper(i)
}

import IntWrapper._
object ImplicitConversionsExample {
  def main(args: Array[String]): Unit = {
    println("twiced = " + 2.twice)
  }
}
