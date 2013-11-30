import scala.language.experimental.macros

import scala.reflect.macros.Context

import java.text.SimpleDateFormat
import java.util.Date

object CLikeMacro {
  def __LINE__ : Int = macro srcLineImpl
  def srcLineImpl(c: Context): c.Expr[Int] =
    c.literal(c.enclosingPosition.line)

  def __FILE__ : String = macro fileImpl
  def fileImpl(c: Context): c.Expr[String] =
    c.literal(c.enclosingUnit.source.file.name)

  def __METHOD__ : String = macro methodImpl
  def methodImpl(c: Context): c.Expr[String] =
    c.literal(c.enclosingMethod.symbol.name.decoded)

  def __DATE__ : String = macro dateImpl
  def dateImpl(c: Context): c.Expr[String] =
    c.literal(new SimpleDateFormat("yyyy/MM/dd").format(new Date))

  def __TIME__ : String = macro timeImpl
  def timeImpl(c: Context): c.Expr[String] =
    c.literal(new SimpleDateFormat("HH:mm:ss.S").format(new Date))
}
