package exception;

public class ThrowableMethods {

  public static double divide(double x, double y) {

    if (y == 0) {
      throw new ArithmeticException("Arithmetic Exception occured");
    }
    return x / y;

  }

  public static void main(String[] args) {

    double d;
    try {
      d = divide(5.6, 0);
    } catch (ArithmeticException e) {
      System.out.println("Divider cannot be equal to 0");
      //Useful Throwable Methods
//      System.out.println(e.getMessage());
//      System.out.println(e.toString());
//      e.printStackTrace();
      System.out.println(e.getLocalizedMessage());
    }

  }

}
