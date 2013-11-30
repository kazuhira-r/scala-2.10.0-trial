import scala.concurrent.{Await, future, promise, Promise}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.{Success, Failure}

object PromiseTrial {
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

  def produceSomething: String = {
    Thread.sleep(5 * 1000L)
    "Produced! [Producer]"
  }

  def continueDoingSomethingUnrelated: String = {
    Thread.sleep(5 * 1000L)
    "Completed Do Something! [Producer]"
  }

  def startDoingSomething() {
    Thread.sleep(8 * 1000L)
    println("Completed Do Something! [Consumer]")
  }

  def main(args: Array[String]): Unit = {
    /*
    sw {
      val p = promise[String]
      val f = p.future

      val producer = future {
        val result = produceSomething
        p success result
        // これは、以下と同義
        // p complete Success(result)
        println(continueDoingSomethingUnrelated)
      }

      val consumer = future {
        startDoingSomething()
        f onSuccess { case r => println(s"Received Producer Result[$r]") }
        println("End Consumer")
      }

      Await.result(producer, Duration.Inf)
    }
    */

    /*
    sw {
      val p = promise[String]
      val f = p.future

      val producer = future {
        p failure new Exception("Oops!")
        // これは、以下と同義
        // p complete Failure(new Exception("Oops!"))
      }

      val consumer = future {
        f onComplete {
          case Success(r) => println(s"got [$r]")
          case Failure(th) => println(s"failed reason[$th]")
        }
      }

      Await.result(f, Duration.Inf)
    }
    */

    /*
    sw {
      val other = future { Thread.sleep(3 * 1000L); 10 }
      val p = promise[Int]

      p completeWith other

      val f = p.future
      f onSuccess { case n => println(n) }

      Await.ready(f, Duration.Inf)
    }
    */

    /*
    sw {
      val p = promise[Int]
      
      println(p.tryComplete(Success(10)))  // => true
      println(p.tryComplete(Success(5)))  // => false
      println(p.tryComplete(Failure(new Exception("Oops!"))))  // => false

      p.future.value
    }

    sw {
      val p = promise[Int]

      println(p.tryComplete(Failure(new Exception("Oops!"))))  // => true
      println(p.tryComplete(Success(10)))  // => false
      println(p.tryComplete(Success(5)))  // => false

      p.future.value
    }
    */

    /*
    sw {
      val p = promise[Int]

      println(p.trySuccess(10))  // => true
      println(p.trySuccess(5))  // => false
      println(p.tryFailure(new Exception("Oops!")))  // => false

      p.future.value
    }

    sw {
      val p = promise[Int]

      println(p.tryFailure(new Exception("Oops!")))  // => true
      println(p.trySuccess(10))  // => false
      println(p.trySuccess(5))  // => false

      p.future.value
    }
    */

    sw {
      val p = Promise.successful(10)
      p.future.value
    }

    sw {
      val p = Promise.failed(new Exception("Oops!"))
      p.future.value
    }
  }
}
