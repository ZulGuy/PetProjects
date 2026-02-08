package decisionmaking;

import java.util.Scanner;

public class SwitchCaseStatement {

  public static void main(String[] args) {

    Scanner number = new Scanner(System.in);
    System.out.println("Please enter a day number of the week: ");
    int dayNumber = number.nextInt();
    String day;

    switch (dayNumber) {
      case 1: case 2: case 3:
      case 4: case 5:
        if (dayNumber == 3)
          day = "Weekday Wednesday";
        else
          day = "Weekday other days";
        break;
      case 6: case 7:
        day = "Weekend";
        break;
      default:
        day = "Invalid day choice";
        break;
    }

    System.out.println(day);

//    switch (dayNumber) {
//      case 1:
//        day = "Monday";
//        break;
//      case 2:
//        day = "Tuesday";
//        break;
//      case 3:
//        day = "Wednesday";
//        break;
//      case 4:
//        day = "Thursday";
//        break;
//      case 5:
//        day = "Friday";
//        break;
//      case 6:
//        day = "Saturday";
//        break;
//      case 7:
//        day = "Sunday";
//        break;
//      default:
//        day = "Invalid day choice";
//        break;
//    }

    number.close();

  }

}
