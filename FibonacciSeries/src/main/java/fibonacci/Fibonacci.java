package fibonacci;

import java.util.Scanner;

public class Fibonacci {

  public static void main(String[] args) {

    //Fibonacci -> 0 1 1 2 3 5 8 13 21 34 55 89 ...
    Scanner input = new Scanner(System.in);
    System.out.println("Enter the max sequence number: ");
    int number = input.nextInt();
    int fibPrevious = 0, fibonacci = 1, sum = 0;

    //i = 5              number = 5
    for (int i = 1; i <= number; i++) {
      System.out.println(fibPrevious + " ");//0 1 1 2 3
      sum = fibPrevious + fibonacci;
      //1st iteration -> sum = 1
      //2nd iteration -> sum = 2
      //3rd iteration -> sum = 3
      //4th iteration -> sum = 5
      //5th iteration -> sum = 8
      fibPrevious = fibonacci;
      //1st iteration -> fibPrevious = 1
      //2nd iteration -> fibPrevious = 1
      //3rd iteration -> fibPrevious = 2
      //4th iteration -> fibPrevious = 3
      //5th iteration -> fibPrevious = 5
      fibonacci = sum;
      //1st iteration -> fibonacci = 1
      //2nd iteration -> fibonacci = 2
      //3rd iteration -> fibonacci = 3
      //4th iteration -> fibonacci = 5
      //5th iteration -> fibonacci = 8
    }

  }

}
