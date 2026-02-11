package branching;

public class ReturnStatement {

  public static void main(String[] args) {

//    return;

    boolean isCheck = true;
    System.out.println("Before the return statement");
    if (isCheck) return;
    System.out.println("This statement will not be executed");

    String[] cars = {"BMW", "Mercedes", "Ferrari", "Opel", "Ford"};
    for (String car : cars) {

      if (car == "Opel") return;
      System.out.println(car);

    }
    System.out.println("This statement will not be executed");

//    int k = 2;
//    switch (k) {
//      case 1:
//        System.out.println("k is one");
//        break;
//      case 2:
//        System.out.println("k is two");
//        return;
//    }
//    System.out.println("Outside the switch statement");

  }

}
