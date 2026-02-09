package loop;

import java.util.Scanner;

public class DoWhileLoop {

  public static void main(String[] args) {

    Scanner input = new Scanner(System.in);
    int result = 0;
    char continueToCalculate;

    do {
      System.out.println("Enter a number: ");
      int number = input.nextInt();
      result += number;//result = result + number
      System.out.println("Do you want to continue? (Y/N): ");
      continueToCalculate = input.next().charAt(0);
    } while (continueToCalculate == 'Y' || continueToCalculate == 'y');

    System.out.println("The sum of th numbers are " + result);
    System.out.println("----------------------Program has terminated----------------------");
    input.close();

//    int i = 5;
//    do {
//      System.out.println(i--);
//    } while (i > 5);
//    System.out.println("Last value of i: " + i);//4

//    int age = 15;
//    do {
//      age--;//14
//      System.out.println("Age in do block: " + age);//14
//      while (age < 20) {//true
//        age++;//15, --, 20
//        System.out.println("Age in while loop: " + age);//15
//      }
//    } while (age < 15);//true 20 < 15 -> false
//
//    System.out.println("Age: " + age);//20

  }

}
