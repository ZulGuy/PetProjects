package stringclassexample;

public class ImmutabilityOfStrings {

  public static void main(String[] args) {

    int age = 20;
    age++;
    System.out.println(age);//21

    String message = "Hello";
    System.out.println(System.identityHashCode(message));
    message.concat( " World");
    System.out.println(System.identityHashCode(message));
    System.out.println(message);//Hello
    message += " World";
    System.out.println(System.identityHashCode(message));
    System.out.println(message);//Hello World
    message = message.concat( " World");
    System.out.println(System.identityHashCode(message));
    System.out.println(message);//Hello World World

    String s = "android";
    s.toUpperCase();
    System.out.println(s);//adnroid
    String s2 = s.toUpperCase();
    System.out.println(s2);//ANDROID

  }

}
