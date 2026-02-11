package branching;

public class ContinueStatement {

  public static void main(String[] args) {

//    int age = 20;
//    age++;
//    if (age == 25) continue;

    String[] animals = {"Monkey", "Bee", "Cat", "Dog", "Cow"};
    int index = 0;
    String animal;

    while (index < animals.length) {

//      index++;//ArrayIndexOutOfBoundsException
      animal = animals[index];

      index++;
      if (animal == "Cow") continue;
      System.out.println(animal);

    }

//    String[] animals = {"Monkey", "Bee", "Cat", "Dog", "Cow"};
//
//    for (String animal : animals) {
//
//      if (animal == "Dog") continue;
//      System.out.println(animal);
//
//    }

    //print even numbers from 10 to 0
//    for (int num = 10; num >= 0; num--) {
//
//      if (num % 2 == 1) continue;
//      System.out.println(num);
//
//    }

  }

}
