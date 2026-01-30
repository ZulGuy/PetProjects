package operator;

public class ArithmeticOperator {

  public static void main(String[] args) {
     double number1 = 8.4, number2 = 4.2, result = 0.0;

     result = number1 + number2;
    System.out.println("number1 + number2 = " + result);

    result = number1 - number2;
    System.out.println("number1 - number2 = " + result);

    result = number1 * number2;
    System.out.println("number1 * number2 = " + result);

    result = number1 / number2;
    System.out.println("number1 / number2 = " + result);

    result = number1 % number2;
    System.out.println("number1 % number2 = " + result);

    String str1 = "Java ";
    String str2 = "Programming";
    String str3 = str1 + str2;
    System.out.println("str3 = " + str3);
  }

}
