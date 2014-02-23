object FutureExecutionContext {
  def main(args: Array[String]): Unit = {
    usingGlobalContext()
    usingJavaExecutorService()
    usingJavaForkJoinPool()
  }

  def usingGlobalContext(): Unit = {
    import scala.concurrent._
    import scala.concurrent.ExecutionContext.Implicits.global
    import scala.concurrent.duration.Duration
    import java.util.concurrent.TimeUnit

    val f = future {
      TimeUnit.SECONDS.sleep(3)
      "Hello Future!!"
    }
    
    val result = Await.result(f, Duration.Inf)
    println(result)
    require(result == "Hello Future!!")
  }

  def usingJavaExecutorService(): Unit = {
    import scala.concurrent._
    import scala.concurrent.duration.Duration
    import java.util.concurrent.{Executors, TimeUnit}

    val es = Executors.newCachedThreadPool
    implicit val context = ExecutionContext.fromExecutorService(es)

    val f = future {
      TimeUnit.SECONDS.sleep(3)
      "Hello Future!!"
    }

    val result = Await.result(f, Duration.Inf)
    println(result)
    require(result == "Hello Future!!")

    es.shutdown()
    es.awaitTermination(5, TimeUnit.SECONDS)
  }

  def usingJavaForkJoinPool(): Unit = {
    import scala.concurrent._
    import scala.concurrent.duration.Duration
    import java.util.concurrent.{ForkJoinPool, TimeUnit}

    val pool = new ForkJoinPool
    implicit val context = ExecutionContext.fromExecutorService(pool)

    val f = future {
      TimeUnit.SECONDS.sleep(3)
      "Hello Future!!"
    }

    val result = Await.result(f, Duration.Inf)
    println(result)
    require(result == "Hello Future!!")

    // ForkJoinPoolで作成されるスレッドは、
    // Daemonスレッドなので待たなくても終了できる
  }
}
