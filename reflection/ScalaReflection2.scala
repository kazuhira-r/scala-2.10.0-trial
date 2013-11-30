import scala.reflect.runtime.universe

object ScalaReflection2 {
  def main(args: Array[String]): Unit = {
    println(newInstance("Foo"))
    println(newInstance("Foo", "Taro"))
    println(newInstance("Foo", "Jiro", 25))
    println(newInstance("Foo", 10))
  }

  def newInstance(name: String, args: Any*): Any = {
    val mirror = universe.runtimeMirror(Thread.currentThread.getContextClassLoader)
    val classSymbol = mirror.staticClass(name)
    val classMirror = mirror.reflectClass(classSymbol)

    val argSymbols = args map { a => mirror.staticClass(a.getClass.getName) }

    val constructorSymbol = classSymbol.typeSignature.declaration(universe.nme.CONSTRUCTOR).filter {
      _.asMethod.paramss match {
        case List(Nil) => argSymbols.isEmpty
        case List(List(as @ _*)) =>
          as.size == argSymbols.size &&
          as.zip(argSymbols).forall { case (a1, a2) =>
                                      a1.typeSignature =:= a2.asClass.selfType ||
                                      equalsPrimitive(mirror, a1.typeSignature, a2.asClass.selfType) }
      }
    }.asMethod
    val constructorMethodMirror = classMirror.reflectConstructor(constructorSymbol)
    constructorMethodMirror(args: _*)
  }

  def equalsPrimitive(mirror: universe.Mirror, t1: universe.Type, t2: universe.Type): Boolean = {
    def toScala(t: universe.Type): universe.Type = {
      if (t =:= mirror.staticClass("java.lang.Boolean").asClass.selfType)
        universe.definitions.BooleanTpe
      else if (t =:= mirror.staticClass("java.lang.Byte").asClass.selfType)
             universe.definitions.ByteTpe
      else if (t =:= mirror.staticClass("java.lang.Character").asClass.selfType)
             universe.definitions.CharTpe
      else if (t =:= mirror.staticClass("java.lang.Double").asClass.selfType)
             universe.definitions.DoubleTpe
      else if (t =:= mirror.staticClass("java.lang.Float").asClass.selfType)
             universe.definitions.FloatTpe
      else if (t =:= mirror.staticClass("java.lang.Integer").asClass.selfType)
             universe.definitions.IntTpe
      else if (t =:= mirror.staticClass("java.lang.Long").asClass.selfType)
             universe.definitions.LongTpe
      else if (t =:= mirror.staticClass("java.lang.Short").asClass.selfType)
             universe.definitions.ShortTpe
      else t
    }

    toScala(t1) =:= toScala(t2)
  }
}

class Foo(val name: String, var age: Int) {
  def this() = this("dummy", 20)
  def this(name: String) = this(name, 20)
  def this(age: Int) = this("dummy", age)

  override def toString: String =
    s"This is $name!, $age"
}
