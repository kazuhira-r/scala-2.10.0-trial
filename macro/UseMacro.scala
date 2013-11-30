object UseMacro {
  def main(args: Array[String]): Unit = {
    import FirstMacro._

    printOnly

    println(triple("Hello"))

    println(varargs(1, 2, 3, 4, 5))

    import CLikeMacro._
    println("Current Line => " + __LINE__)
    println("Current Source => " + __FILE__)
    println("Current Method => " + __METHOD__)
    println("Current Line Complied Time => " + __TIME__)
    println("Current Line Complied Date => " + __DATE__)
  }
}
