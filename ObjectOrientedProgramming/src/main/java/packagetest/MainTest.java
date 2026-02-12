package packagetest;

//Built-in package
import java.util.Scanner;//Scanner class is in the util subpackage in the java main package

//User-defined package
import packageoperation.*;
import packageoperation2.Sum;

public class MainTest {

  public static void main(String[] args) {

    Scanner input = new Scanner(System.in);
    System.out.println("Please enter first number");
    int number1 = input.nextInt();
    System.out.println("Please enter the second number");
    int number2 = input.nextInt();

    packageoperation.Sum sum = new packageoperation.Sum();
    System.out.println(number1 + " + " + number2 + " = " + sum.sumNumbers(number1, number2));

    Multiply multi = new Multiply();
    System.out.println(number1 + " * " + number2 + " = " + multi.multiplyNumbers(number1, number2));
    
    //name conflict
//    packageoperation2.Sum

  }

}
