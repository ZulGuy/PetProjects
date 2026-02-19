package exception;

public class ThrowsException {

  public static void divide() throws NumberFormatException, ArithmeticException {
    int a = Integer.parseInt("8");
    int b = Integer.parseInt("0");
    int c = a / b;
    System.out.println("c = " + c);
  }

  public static void main(String[] args) {

    try {
      divide();
    } catch (NumberFormatException e) {
      System.out.println("Invalid number format!");
    } catch (ArithmeticException e) {
      System.out.println("You cannot divide number by zero!");
    }

  }

}
