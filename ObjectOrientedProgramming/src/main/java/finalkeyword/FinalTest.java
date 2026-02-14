package finalkeyword;

import java.util.ArrayList;

public class FinalTest {

  public final static int NUMBER = 10;

  String[] animalList = new String[NUMBER];
  //ArrayList
  public final static ArrayList<String> animals = new ArrayList<>();

  public static void main(String[] args) {

//    Car car = new Car();
//    car.model = "Ferrari";
//    car.showMaxSpeed();

//    int number2 = NUMBER--;//invalid usage
    int number2 = NUMBER;
    int number3 = number2--;

    int result = NUMBER / 2;

    for (int x = 0; x < NUMBER; x++) {
      System.out.print(x + " ");
    }

    animals.add("Cat");
    animals.add("Dog");
    animals.add("Lion");

//    animals = new ArrayList<>();//invalid usage

  }

}
