object ImplicitClasses {
  implicit class StringWrapper(val underlying: String) extends AnyVal {
    def star: String = s"***$underlying***"
  }

  def main(args: Array[String]): Unit = {
    println("Hello World".star)
  }
}
