import scala.reflect.runtime.universe

object ScalaReflection {
  def main(args: Array[String]): Unit = {
    //val theType = getTypeTag(classOf[Member]).tpe
    val theType = getType(classOf[Member])
    println(s"typeTag.declarations => ${theType.declarations}")
    println(s"typeTag.members => ${theType.members}")

    val vals = theType.declarations.filter(_.asTerm.isVal)
    println(s"vals => $vals")

    val vars = theType.declarations.filter(_.asTerm.isVar)
    println(s"vars => $vars")

    val nameField = theType.declaration(universe.newTermName("name"))
    println(s"nameField => $nameField")

    val methods = theType.declarations.filter(_.isMethod)
    println(s"methods => $methods")
    methods foreach { m => println(s"method name = [${m.name.decoded}]") }

    val implicits = theType.declarations.filter(_.isImplicit)
    println(s"implicits => $implicits")

    val primitives = theType.declarations.filter(isPrimitive)
    println(s"primitives => $primitives")

    println(newInstance(classOf[Member]))

    val m = newInstance(classOf[Member], "Taro")
    m.methodA("[", "]")
    println("getFieldValue => " + getFieldValue(m, "name"))
    setFieldValue(m, "age", 25)
    println(s"setFieldValue age => ${m.age}")
    val result: String = invokeMethod(m, "methodB", "***", "***")
    println(s"invokeMethod => $result")
  }

  def getTypeTag[T: universe.TypeTag](targetClass: Class[T]): universe.TypeTag[T] =
    universe.typeTag[T]

  def getType[T: universe.TypeTag](targetClass: Class[T]): universe.Type =
    universe.typeOf[T]

  def isPrimitive(symbol: universe.Symbol): Boolean =
    if (symbol.isMethod) {
      val rt = symbol.asMethod.returnType
      if (rt =:= universe.definitions.BooleanTpe) true
      else if (rt =:= universe.definitions.ByteTpe) true
      else if (rt =:= universe.definitions.CharTpe) true
      else if (rt =:= universe.definitions.DoubleTpe) true
      else if (rt =:= universe.definitions.FloatTpe) true
      else if (rt =:= universe.definitions.IntTpe) true
      else if (rt =:= universe.definitions.LongTpe) true
      else if (rt =:= universe.definitions.ShortTpe) true
      else false
    } else false

  def newInstance[T: universe.TypeTag](targetClass: Class[T], args: Any*): T = {
    val mirror = universe.runtimeMirror(targetClass.getClassLoader)
    // これでもOK
    // val mirror = universe.typeTag[T].mirror
    val classSymbol = universe.typeOf[T].typeSymbol.asClass

    val classMirror = mirror.reflectClass(classSymbol)
    val constructorSymbol = universe.typeOf[T].declaration(universe.nme.CONSTRUCTOR).filter {
      _.asMethod.paramss match {
        case List(Nil) => args.isEmpty
        case List(List(as @ _*)) => as.size == args.size
      }
    }.asMethod
    val constructorMethodMirror = classMirror.reflectConstructor(constructorSymbol)
    constructorMethodMirror(args: _*).asInstanceOf[T]
  }

  def getFieldValue[A: universe.TypeTag: scala.reflect.ClassTag, B](instance: A, name: String): B = {
    val mirror = universe.runtimeMirror(instance.getClass.getClassLoader)
    val termSymbol = universe.typeOf[A].declaration(universe.newTermName(name)).asTerm

    val instanceMirror = mirror.reflect(instance)
    instanceMirror.reflectField(termSymbol).get.asInstanceOf[B]
  }

  def setFieldValue[A: universe.TypeTag: scala.reflect.ClassTag, B](instance: A, name: String, value: B):Unit = {
    val mirror = universe.runtimeMirror(instance.getClass.getClassLoader)
    val termSymbol = universe.typeOf[A].declaration(universe.newTermName(name)).asTerm

    val instanceMirror = mirror.reflect(instance)
    instanceMirror.reflectField(termSymbol).set(value)
  }

  def invokeMethod[A: universe.TypeTag: scala.reflect.ClassTag, B](instance: A, name: String, args: Any*): B = {
    val mirror = universe.runtimeMirror(instance.getClass.getClassLoader)
    val methodSymbol = universe.typeOf[A].declaration(universe.newTermName(name)).asMethod

    val instanceMirror = mirror.reflect(instance)
    instanceMirror.reflectMethod(methodSymbol)(args: _*).asInstanceOf[B]
  }
}

class Member(val name: String) {
  def this() = this("dummy")

  def this(name: String, age: Int) = {
    this(name)
    this.age = age
  }

  var age: Int = _
  private var counter: Int = _
  implicit val iv: String = "Implicit Value"

  def methodA(pre: String, suf: String): Unit = {
    counter += 1
    println(s"$pre$name$suf")
  }

  def methodB(pre: String, suf: String): String = {
    counter += 1
    s"$pre$name$suf"
  }

  override def toString(): String =
    s"Member name=$name, counter=$counter, iv=$iv"
}
