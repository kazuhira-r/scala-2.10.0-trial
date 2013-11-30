import scala.language.experimental.macros

import scala.reflect.macros.Context

import java.text.SimpleDateFormat
import java.util.Date

object FirstMacro {
  def printOnly: Unit = macro printOnlyImpl
  def printOnlyImpl(c: Context): c.Expr[Unit] =
    c.universe.reify(println("My First Macro"))

  def triple(msg: String): String = macro tripleImpl
  def tripleImpl(c: Context)(msg: c.Expr[String]): c.Expr[String] = {
    import c.universe._
    val Literal(Constant(m: String)) = msg.tree
    c.Expr(Literal(Constant(m * 3)))
    // 上記は、以下でもOK
    // c.literal(m * 3)
    
    // さらに、全部まとめて以下でもOK
    // c.universe.reify(msg.splice * 3)
  }

  def v(xs: List[Int]): Unit = macro vImpl
  def vImpl(c: Context)(xs: c.Expr[List[Int]]): c.Expr[Unit] =
    c.universe.reify(println(xs.splice))

  def varargs(args: Int*): Int = macro varargsImpl
  def varargsImpl(c: Context)(args: c.Expr[Int]*): c.Expr[Int] = {
    import c.universe._
    val sum = args.toList.map { i =>
      val Literal(Constant(iv: Int)) = i.tree
      iv
    }.sum

    c.literal(sum)
  }
}
