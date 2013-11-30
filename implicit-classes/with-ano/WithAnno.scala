object WithAnno {
  @Bar("name")
  implicit class IntWrapper(val underlying: Int) {
    def twice: Int = underlying * 2
  }

  def main(args: Array[String]): Unit =
    println(2.twice)
}
