package foo {
  object IC {
    implicit class IntWrapper(val underlying: Int) {
      def twice: Int = underlying * 2
    }
  }

  import foo.IC.IntWrapper
  package bar {
    class Bar {
      def twiceTwice(n: Int): Int = n.twice.twice
    }
  }
}

import foo.bar.Bar
package fuga {
  object Main {
    def main(args: Array[String]): Unit = {
      import foo.IC.IntWrapper
      println("twiceTwiced = " + new Bar().twiceTwice(2))
      println(2.twice)
    }
  }
}
