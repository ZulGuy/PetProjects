package operator;

public class ConditionalOperator {

  public static void main(String[] args) {
    int number1 = 5, number2 = 8, number3 = 12;
    boolean result;//default value is false

    //Conditional AND (&&) operator
    result = (number1 > number2) && (number3 > number1);
    System.out.println("result = " + result);

    //Conditional OR (||) operator
    result = (number1 > number2) || (number3 > number2);
    System.out.println("result = " + result);

    //Conditional Ternary (?:) operator
    result = number1 < number2 ? true : false;
    System.out.println("result = " + result);
  }

}
