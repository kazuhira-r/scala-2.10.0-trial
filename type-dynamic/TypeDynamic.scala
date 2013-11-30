import scala.language.dynamics

    /*
    dc.method("blah")
    dc.method(x = "blah")
    dc.method(x = 1, 2)
    println(dc.field)
    dc.array(10)
    dc.array(10) = 13
    */

object TypeDynamic {
  def main(args: Array[String]): Unit = {
    val dc = new DynaClass
    println(dc.method(10))
  }
}


class DynaClass extends Dynamic {
  def applyDynamic[T](name: String)(value: T): T =
    value
}

  /*
  def update(index: Int, value: Int): Unit =
    dump("update", index.toString, value)

  def applyDynamic(name: String)(values: Any*): Unit =
    dump("applyDynamic", name, values: _*)

  def applyDynamicNamed(name: String)(pairs: (String, Any)*): Unit =
    dump("applyDynamicNamed", name, pairs: _*)

  def selectDynamic(name: String): this.type = {
    dump("selectDynamic", name)
    this
  }

  def updateDynamic(name: String)(values: Any*): Unit =
    dump("updateDynamic", name, values: _*)

  private def dump(prefix: String, name: String, values: Any*): Unit =
    values.toList match {
      case Nil => println(s"$prefix: name = $name")
      case _ => println(s"$prefix: name = $name, values = $values")
    }
}
*/
