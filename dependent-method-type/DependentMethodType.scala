object DependentMethodType {
  def main(args: Array[String]): Unit = {
    val s = "Hello World"
    val xs = List(1, 2, 3)

    str(identity(s))
    list(identity(xs))

    val any: AnyRef = "Hello World"
    str(identity(any))
  }

  def identity(x: AnyRef): x.type = x

  def str(s: String): Unit = println(s)
  def list[T](xs: List[T]): Unit = println(xs)
}
