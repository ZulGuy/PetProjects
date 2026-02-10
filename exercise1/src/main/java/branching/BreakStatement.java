package branching;

public class BreakStatement {

  public static void main(String[] args) {

//    int age = 50;
//    age--;
//    if (age == 40) break;

    String[] animals = {"Monkey", "Bee", "Cat", "Dog", "Cow"};

    int index = 0;
    String animal = "null";
    while (index < animals.length) {

      animal = animals[index];

      if (animal == "Dog") break;
      index++;

    }

    System.out.println(animal);

//    for (String animal : animals) {
//      if (animal == "Dog") break;
//      System.out.println(animal);
//    }

//    for (int x = 15; x >= 0; x--) {
//
//      if (x == 5) break;
//
//      System.out.println("x: " + x);// 15 -> 6
//
//    }

//    int a = 0;
//    switch (a) {
//      case 0:
//        System.out.println("Hi...");
//      case 1:
//        System.out.println("Hello...");
//        break;
//    }
  }

}
