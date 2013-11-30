import language.postfixOps

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.{Failure, Success}

object FutureAndPromises {
  // stop-watch
  def sw[A](body: => A): Unit = {
    val start = System.currentTimeMillis
    try {
      println(s"got [$body], elapsed time [${(System.currentTimeMillis - start) / 1000.0}]msec")
    } catch {
      case th: Throwable =>
        println(s"got Exception[$th], elapsed time [${(System.currentTimeMillis - start) / 1000.0}]msec")
    }
  }

  // wait-and-get
  def wag(waitSec: Int, n: Int): Int = {
    Thread.sleep(waitSec * 1000L)
    n
  }

  def main(args: Array[String]): Unit = {
    /*
    sw {
      val f = future { wag(3, 5) }
      f onComplete {
        case Success(n) => println(s"got success = $n")
        case Failure(e) => println(s"got error = $e")
      }
      Await.ready(f, 1 seconds)
    }

    sw {
      val f = future { throw new Exception }
      f onComplete {
        case Success(n) => println(s"got success = $n")
        case Failure(e) => println(s"got error = $e")
      }
      Await.result(f, Duration.Inf)
    }
    */

    /*
    sw {
      val f = future { wag(3, 5) }
      f onComplete {
        case Success(n) => println(s"1. got success = $n")
        case Failure(e) => println(s"1. got error = $e")
      }

      f onComplete {
        case Success(n) => println(s"2. got success = $n")
        case Failure(e) => println(s"2. got error = $e")
      }

      Await.result(f, Duration.Inf)
    }
    */

    /*
    sw {
      val f = future { wag(3, 5) }
      f.value
    }

    sw {
      val f = future { wag(3, 5) }
      Await.ready(f, Duration.Inf)
      f.value
    }

    sw {
      val f = future { throw new Exception }
      Await.ready(f, Duration.Inf)
      f.value
    }
    */
    
    /*
    //val result = Await.result(f, Duration.Inf)
    Await.ready(f, Duration.Inf)
    //val result = blocking { f.value }
    //println(s"result = $result")
    println(s"result = ${f.value}")
    */

    /*
    sw {
      val f = future { wag(3, 5) }
      f onSuccess {
        case n => println(s"got success $n")
      }
      f onFailure {
        case t => println(s"got failure $t")
      }
      Await.result(f, Duration.Inf)
    }

    sw {
      val f = future { throw new Exception }
      f onSuccess {
        case n => println(s"got success $n")
      }
      f onFailure {
        case t => println(s"got failure $t")
      }
      Await.result(f, Duration.Inf)
    }
    */

    /*
    sw {
      val f = future { wag(3, 5) } map { n => wag(5, 10) }
      f foreach println
      Await.result(f, Duration.Inf)
    }

    sw {
      val f1 = future { wag(3, 5) }
      val f2 = future { wag(5, 10) }
      val fs = f1 flatMap { n1 => f2 map { n2 => n1 + n2 } }
      Await.result(fs, Duration.Inf)
    }
    */

    /*
    sw {
      val f = future[Int] { Thread.sleep(3000L); throw new Exception } map { wag(5, 10) }
      f foreach println
      Await.result(f, Duration.Inf)
    }

    sw {
      val f1 = future[Int] { Thread.sleep(3000L); throw new Exception }
      val f2 = future { wag(5, 10) }
      val fs = f1 flatMap { n1 => f2 map { n2 => n1 + n2 } }
      Await.result(fs, Duration.Inf)
    }
    */

    /*
    sw {
      val f1 = future { wag(3, 5) }
      val f2 = future { wag(5, 10) }
      val fs = for {
        n1 <- f1
        n2 <- f2
      } yield n1 + n2
      Await.result(fs, Duration.Inf)
    }
    */

    /*
    sw {
      val f1 = future { wag(3, 5) }
      val f2 = future { wag(5, 10) }
      val f = for {
        n1 <- f1
        if n1 > 5
        n2 <- f2
      } yield n1 + n2
      Await.result(f, Duration.Inf)
    }
    */

    /*
    sw {
      val f = future {
        1 / 0
      } recover {
        case th: ArithmeticException => 5
      }
      Await.result(f, Duration.Inf)
    }

    sw {
      val f = future {
        1 / 0
      } recoverWith {
        case th: ArithmeticException => future { wag(3, 5) }
      }
      Await.result(f, Duration.Inf)
    }
    */

    /*
    sw {
      val f = future { 1 / 0 } fallbackTo future { wag(3, 5) }
      Await.result(f, Duration.Inf)
    }
    */

    /**
    sw {
      val f = future { wag(3, 5) }
                .andThen { case Success(n) => println(n); 20 }
                .andThen { case Success(n) => println(n); 10 }
      Await.result(f, Duration.Inf)
    }
    **/

    /*
    sw {
      val f = future { wag(3, 5) } zip future { wag(5, 10) }
      Await.result(f, Duration.Inf)
    }
    */

    /*
    sw {
      val f = future { throw new Exception } zip future { wag(5, 10) }
      Await.result(f, Duration.Inf)
    }

    sw {
      val f = future { wag(3, 5) } zip future { throw new Exception }
      Await.result(f, Duration.Inf)
    }
    */

    /*
    sw {
      val f = future { wag(3, 5) }
      f.failed.foreach(th => println(s"got Exception[${th}]"))
      Await.result(f, Duration.Inf)
    }

    sw {
      val f = future { Thread.sleep(3000L); 1 / 0 }
      f.failed.foreach(th => println(s"got Exception[${th}]"))
      Await.result(f, Duration.Inf)
    }

    sw {
      val f = future { Thread.sleep(3000L); 1 / 0 }
      for (th <- f.failed) println(s"got Exception[${th}]")
      Await.result(f, Duration.Inf)
    }
    */

    /*
    sw {
      val f = future { wag(3, 5) } transform ({ n => n * 10}, { th => new RuntimeException(th) })
      Await.result(f, Duration.Inf)
    }

    sw {
      val f = future { 1 / 0 } transform ({ n => n * 10}, { th => new RuntimeException(th) })
      Await.result(f, Duration.Inf)
    }
    */

    val wait3 = wag(3, _: Int)
    //val futures = future { wait3(3) } map { n => wait3(n + 5) } map { n => wait3(n + 2) }
    //println(Await.result(futures, Duration.Inf))

    /*
    val futures = future { wait3(3) } zip future { wait3(5) }
    println(Await.result(futures, Duration.Inf))

    val f1 = future { wait3(3) }
    val f2 = future { wait3(5) }
    //val f = f1 flatMap { n => f2 map { n2 => n + n2 }  }
    val f = for {
      n1 <- f1
      n2 <- f2
    } yield n1 + n2

    println(Await.result(f, Duration.Inf))
    */

    /*
    sw {
    val xs = List(future { wait3(1) }, future { wait3(2) }, future { wait3(3) })
    val fs = Future.sequence(xs)
      //Await.ready(fs, Duration.Inf).value
      Await.result(fs, Duration.Inf)
      //xs.flatten
    //println(Await.result(Future.fold(xs)(0) { case (r, t) => r + t}, Duration.Inf))
    //Await.result(Future.firstCompletedOf(xs), Duration.Inf)
    }
    */
  }
}
