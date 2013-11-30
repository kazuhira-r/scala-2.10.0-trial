@Bar("hoge")
public class Foo {
    public static void main(String[] args) {
        System.out.println(WithAnno.IntWrapper.class.getAnnotation(Bar.class));
    }
}