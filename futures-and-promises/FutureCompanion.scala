import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.{Failure, Success}

object FutureCompanion {
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
      val f = Future.successful("Hello World")
      f.value
    }

    sw {
      val f = Future.failed(new Exception)
      f.value
    }
    */

    /*
    sw {
      val xs = List(
        future { wag(3, 1) },
        future { wag(3, 2) },
        future { wag(3, 3) },
        future { wag(3, 4) },
        future { wag(3, 5) },
        future { wag(3, 6) }
      )
      val futures = Future.sequence(xs)
      Await.result(futures, Duration.Inf)
    }

    sw {
      val xs = List(1, 2, 3, 4, 5, 6)
      val futures = Future.traverse(xs)(n => future { wag(3, n) })
      Await.result(futures, Duration.Inf)
    }
    */

    /*
    sw {
      val xs = List(
        future { wag(3, 1) },
        future { wag(3, 2) },
        future { wag(3, 3) },
        future { wag(3, 4) },
        future { wag(3, 5) },
        future { wag(3, 6) }
      )
      val f = Future.fold(xs)(0) { (acc, n) => acc + n }
      Await.result(f, Duration.Inf)
    }

    sw {
      val xs = List(
        future { wag(3, 1) },
        future { wag(3, 2) },
        future { wag(3, 3) },
        future { wag(3, 4) },
        future { wag(3, 5) },
        future { wag(3, 6) }
      )
      val f = Future.reduce(xs) { (acc, n) => acc + n }
      Await.result(f, Duration.Inf)
    }
    */

    /*
    sw {
      val xs = List(
        future { wag(3, 1) },
        future { wag(3, 2) },
        future { wag(5, 3) },
        future { wag(3, 4) }
      )
      val f = Future.find(xs) { n => n > 2 }
      Await.result(f, Duration.Inf)
    }
    */

    sw {
      /*
      val xs = List(
        future { wag(3, 1) },
        future { wag(1, 2) },
        future { wag(3, 3) },
        future { wag(1, 4) },
        future { wag(3, 5) },
        future { wag(1, 6) }
      )
      */

      val xs = List(
        future { throw new Exception },
        future { wag(3, 5) }
      )
      val f = Future.firstCompletedOf(xs)
      Await.result(f, Duration.Inf)
    }
  }
}
