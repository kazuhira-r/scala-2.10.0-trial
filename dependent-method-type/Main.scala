object Main {
  def main(args: Array[String]): Unit = {
    val i: Int = Foo.withFoo(Foo1)
    val s: String = Foo.withFoo(Foo2)

    println(i == 10)  // => true
    println(s == "Hello World")  // => true

    val i2: Foo1.Bar = Foo.withFoo(Foo1)
    val s2: Foo2.Bar = Foo.withFoo(Foo2)

    println(classOf[Foo1.Bar])  // => int
    println(classOf[Foo2.Bar])  // => class java.lang.String

    println(Out.intFoo(Foo1)(Foo1.v))  // => true
    println(Out.stringFoo(Foo2)(Foo2.v))  // => true

    // val i3: String = Foo.withFoo(Foo1)  // <= コンパイルエラー
    // val s3: Int = Foo.withFoo(Foo2)  // <= コンパイルエラー
    // println(Out.intFoo(Foo1)(Foo2.v))  // <= コンパイルエラー
    // println(Out.stringFoo(Foo2)(Foo1.v))  // <= コンパイルエラー
  }
}

object Out {
  def intFoo(f: Foo)(b: f.Bar) = b == 10
  def stringFoo(f: Foo)(b: f.Bar): Boolean = b == "Hello World"
}

object Foo {
  def withFoo(f: Foo): f.Bar = f.v
}

trait Foo {
  type Bar
  def v: Bar
}

object Foo1 extends Foo {
  type Bar = Int
  def v: Bar = 10
}

object Foo2 extends Foo {
  type Bar = String
  def v: Bar = "Hello World"
}
