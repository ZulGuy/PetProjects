package loop;

public class LabeledLoops {

  public static void main(String[] args) {

    System.out.println("---------------------Unlabeled loop---------------------");
    for (int i = 1; i <= 3; i++) {

      for(int j = 1; j <= 3; j++) {

        System.out.println("Outer loop: " + i + ", Ineer loop: " + j);
        if (i == 2 && j == 2) break;

      }

    }

    System.out.println("---------------------Labeled loop---------------------");
    outerLoop:
    for (int i = 1; i <= 3; i++) {

      innerLoop:
      for(int j = 1; j <= 3; j++) {

        System.out.println("Outer loop: " + i + ", Ineer loop: " + j);
        if (i == 2 && j == 2) break outerLoop;

      }

    }

//    String[] animals = {"Cat", "Dog", "Cow", "Lion", "Rabbit"};
//    int index = 0;
//    String animal = "null";
//    animalsLoop:
//    while (index < animals.length) {
//
//      animal = animals[index];
//      if (animal == "Lion") {
//        System.out.println(animal);
//        break animalsLoop;
//      }
//      index++;

    }

  }

