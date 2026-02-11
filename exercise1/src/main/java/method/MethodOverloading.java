package method;

public class MethodOverloading {

  public static void main(String[] args) {

    System.out.println("Sum of two numbers: " + add(10, 20));
    System.out.println("Sum of three numbers: " + add(10, 20, 30));
    System.out.println("Sum of two double numbers: " + add(4.6, 8.4));

  }

  public static int add (int x, int y) {
    return x + y;
  }

  public static int add (int x, long y) {
    return (int) (x + y);
  }

  private static int add (int x, long y, int z) {
    return (int) (x + y + z);
  }

  protected static double add (double x, double y) {
    return x + y;
  }

}
