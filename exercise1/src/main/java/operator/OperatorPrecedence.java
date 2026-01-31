package operator;

public class OperatorPrecedence {

  public static void main(String[] args) {
    int result = 5 + 2 * 4;//2 * 4 = 8 -> 5 + 8 = 13
    System.out.println("result: " + result);

    result = (5 + 2) * 4;//5 + 2 = 7 -> 7 * 4 = 28
    System.out.println("result: " + result);

    result += 80 / 20 % 8 * 3 + -5;//80 / 20 = 4 -> 4 % 8 = 4 -> 4 * 3 = 12 -> 12 + -5 = 7 -> 28 + 7 = 35
    System.out.println("result: " + result);

    int x = 8, y = 4, z = 2, sum = 0;
    /*
    sum = x + --y + --z;//x=8, y=3, z=1 -> 8 + 3 + 1 = 12
    System.out.println("sum: " + sum);
    */

    /*
    sum = x + y-- + --z;//x=8, y=4, z=1 -> 8 + 4 + 1 = 13
    System.out.println("sum: " + sum);
     */

    /*
    sum = x + y-- + --z + y;//x=8, y=4, z=1, y(new value)=3 -> 8 + 4 + 1 + 3 = 16
    System.out.println("sum: " + sum);
     */

    sum = x + y-- + --z * y;//x=8, y=4, z=1, y(new value)=3 -> 8 + 4 + 1 * 3 = 15
    System.out.println("sum: " + sum);
  }

}
