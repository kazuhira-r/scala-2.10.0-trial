//class IntWrapper(val underlying: Int) extends AnyVal

object Outer {
  println(this.getClass.getName)
  //class Inner(val x: Int) extends AnyVal
  class Inner {
    println("hello")
  }

}

Outer
new Outer.Inner
