import scala.util.{Failure, Success, Try}
import scala.util.control.NonFatal

object UseTry {
  def main(args: Array[String]): Unit = {
    println(Try { 10 })  // => Success(10)
    println(Try { throw new Exception("Oops!") })  // => Failure(java.lang.Exception: Oops!)

    println(Try { 10 }.isSuccess)  // => true
    println(Try { 10 }.isFailure)  // => false
    println(Try { throw new Exception("Oops!") }.isSuccess)  // => false
    println(Try { throw new Exception("Oops!") }.isFailure)  // => true

    println(Try { 10 }.get)  // => 10
    try {
      Try { throw new Exception("Oops!") }.get
    } catch {
      case e: Exception => println(e)  // => java.lang.Exception: Oops!
    }

    println(Try { 10 }.getOrElse(50))  // => 10
    println(Try { throw new Exception("Oops!") }.getOrElse(50))  // => 50

    Try { 10 } foreach println  // => 10
    Try { throw new Exception("Oops!") } foreach println  // => 何も出力されない

    Try { 10 } map { _ * 2 } foreach println
    // => 20
    Try[Int] { throw new Exception("Oops!") } map { _ * 2 } foreach println
    // => 何も出力されない

    println(Try { 40 } recover { case th => 20 })
    // => Success(40)
    println(Try[Int] { throw new NullPointerException("Oops!") }
      .recover { case e: NullPointerException => 50 })
    // => Success(50)
    println(Try[Int] { throw new NullPointerException("Oops!") }
      .recover { case e: IllegalArgumentException => 50 })
    // => Failure(java.lang.NullPointerException: Oops!)

    println(Try { 40 } recoverWith { case th => Success(20) })
    // => Success(40)
    println(Try { 40 } recoverWith { case th => Failure(new Exception("Failure?")) })
    // => Success(40)
    println(Try[Int] { throw new NullPointerException("Oops!") }
      .recoverWith { case e: NullPointerException => Success(50) })
    // => Success(50)
    println(Try[Int] { throw new NullPointerException("Oops!") }
      .recoverWith { case e: IllegalArgumentException => Success(50) })
    // => Failure(java.lang.NullPointerException: Oops!)
    println(Try[Int] { throw new NullPointerException("Oops!") }
      .recoverWith { case e: NullPointerException => Failure(new Exception("Failure?")) })
    // => Failure(java.lang.Exception: Failure?)
    println(Try[Int] { throw new NullPointerException("Oops!") }
      .recoverWith { case e: IllegalArgumentException => Failure(new Exception("Failure?")) })
    // => Failure(java.lang.NullPointerException: Oops!)

    for {
      n1 <- Try { 10 }
      n2 <- Try { 20 }
    } println(n1 + n2)  // => 30

    for {
      n1 <- Try { 10 }
      n2 <- Try[Int] { throw new Exception("Oops!") }
    } println(n1 + n2)  // => 何も出力されない

    Try { 10 } map { _ * 20 } match {
      case Success(n) => println(s"Success? => $n")  // => Success? => 200
      case Failure(e) => println(s"Failure? => $e")
    }

    Try[Int] { throw new Exception("Oops!") } map { _ * 20 } match {
      case Success(n) => println(s"Success? => $n")
      case Failure(e) => println(s"Failure? => $e")  // => Failure? => java.lang.Exception: Oops!
    }

    try {
      throw new OutOfMemoryError("dummy")
    } catch {
      case NonFatal(e) => println(s"Non Fatal [$e]")
      case th: Throwable => println(s"Cached! [$th]")  // => Cached! [java.lang.OutOfMemoryError: dummy]
    }

    try {
      throw new NullPointerException("dummy")
    } catch {
      case NonFatal(e) => println(s"Non Fatal [$e]")  // => Non Fatal [java.lang.NullPointerException: dummy]
      case th: Throwable => println(s"Cached! [$th]")
    }
  }
}
