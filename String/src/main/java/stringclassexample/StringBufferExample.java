package stringclassexample;

public class StringBufferExample {

  public static void main(String[] args) {

    StringBuffer buffer = new StringBuffer("Welcome to");
    buffer.append(" Java");//mutable buffer object (changeable)
    //buffer object points out "Welocme to Java"
    System.out.println(buffer);
    System.out.println(buffer.length());
    System.out.println(buffer.insert(buffer.length(), " World"));
    System.out.println(buffer.reverse());//String class doesn't have reverse method
    System.out.println(buffer.reverse());
    System.out.println(buffer.delete(0, 11));

  }

}
