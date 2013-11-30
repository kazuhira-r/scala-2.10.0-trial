trait Printable extends Any {
  def print(): Unit = println(this)
}

class IntWrapper(val underlying: Int) extends AnyVal with Printable {
  def show(): Unit = println(s"This Value = [$underlying]")
}

object ValueClasses {
  def main(args: Array[String]): Unit = {
    val iw = new IntWrapper(10)
    iw.show()
    iw.print()
  }
}
