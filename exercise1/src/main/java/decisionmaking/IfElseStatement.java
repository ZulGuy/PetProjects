package decisionmaking;

import java.util.Scanner;

public class IfElseStatement {

  public static void main(String[] args) {

//    Scanner input = new Scanner(System.in);
//    System.out.println("Please enter a number:");
//    int number = input.nextInt();
//
//    if(number % 2 == 0)
//      System.out.println("The number is even");
//    else
//      System.out.println("The number is odd");

//    boolean control = true;
//    if (control)
//      System.out.println("Control is true");
//    else
//      System.out.println("Control is false");

//    input.close();

    int age = 30;
    if (age > 10) System.out.println("Age is greater than 10");
    if (age > 20) System.out.println("Age is greater than 20");
    if (age == 30) System.out.println("Age is 30");
    if (age > 40)
      System.out.println("Age is greater than 40");//inside the if block
    System.out.println("Age is greater than 40");//outside the if block

  }

}
