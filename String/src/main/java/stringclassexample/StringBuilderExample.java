package stringclassexample;

public class StringBuilderExample {

  public static void main(String[] args) {

    StringBuilder builder = new StringBuilder("Welcome to");
    builder.append(" Java");//mutable buffer object (changeable)
    //builder object points out "Welocme to Java"
    System.out.println(builder);
    System.out.println(builder.length());
    System.out.println(builder.insert(builder.length(), " World"));
    System.out.println(builder.reverse());//String class doesn't have a reverse method
    System.out.println(builder.reverse());
    System.out.println(builder.delete(0, 11));

  }

}
