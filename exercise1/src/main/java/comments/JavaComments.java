package comments;

public class JavaComments {

  /*
  * This is the multi-line comment in Java
  * */

  /**
   *
   * @param args
   */
  public static void main(String[] args) {
    //This is the single-line comment in Java
    //Prints Gello Java Developers
    System.out.println("Hello Java Developers");

    /*
    int x = 4;
    int y = 3;
    int z = 0;
    z = x + y;

    System.out.println("z = " + z);
    */

    doMultiplication(3,4);
  }

  /**
   * Multiplies two integers and returns the result.
   *
   * @param x the first integer to multiply
   * @param y the second integer to multiply
   * @return the product of the two integers
   */
  public static int doMultiplication(int x, int y) {
    return x * y;
  }

}
