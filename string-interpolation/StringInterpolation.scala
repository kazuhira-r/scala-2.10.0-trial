object StringInterpolation {
  def main(args: Array[String]): Unit = {
    val stringValue = "Hello World"
    val intValue = 1000
    val doubleValue = 10.505

    println(s"$stringValue $$stringValue $$")

    println(s"s format example = $stringValue, $intValue, $doubleValue")
    println(s"s format example = ${stringValue}, ${intValue}, ${doubleValue}")

    println(f"f format example = $stringValue%s, $intValue%,3d, $doubleValue%.2f")
    println(f"f format example = ${stringValue}%s, ${intValue}%06d, ${doubleValue}%.2f")

    println(raw"\r\n\t $stringValue")

    println(s"now = ${new java.util.Date}")
    println(f"now = ${new java.util.Date}")

    println(raw"\d+".r.findFirstIn("100"))

    println(s"\042")

    var qq = '"'
    println(s"this is ${qq}s${qq} string interpolation")

    println(s"this is ${'"'}s${'"'} string interpolation")
    println(f"this is ${'"'}f${'"'} string interpolation")
    println(raw"this is ${'"'}raw${'"'} string interpolation")
  }
}
