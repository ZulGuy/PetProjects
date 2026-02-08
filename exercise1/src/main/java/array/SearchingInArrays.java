package array;

import java.util.Arrays;

public class SearchingInArrays {

  public static void main(String[] args) {
    int[] numbers = {0, 12, 4, 16, 18, 10};//unsorted array -> output is unpredictable
    int indexOfEight = Arrays.binarySearch(numbers, 4);//4
    System.out.println("Index of 4 is: " + indexOfEight);

    int indexOfSeven = Arrays.binarySearch(numbers, 7);//-5
    System.out.println("Index of 7 is: " + indexOfSeven);

    Arrays.sort(numbers);

    int indexOfFour = Arrays.binarySearch(numbers, 4);// 0 4 10 12 16 18
    System.out.println("Index of 4 is: " + indexOfFour);//1

    String[] animals = {"Cat", "Cow", "Dog", "Elephant", "Lion", "Monkey"};//sorted array, output is predictable
    int indexOfDog = Arrays.binarySearch(animals, "Dog");//2
    System.out.println("Index of Dog is: " + indexOfDog);

    //Ant ->
    int indexOfAnt = Arrays.binarySearch(animals, "Ant");
    System.out.println("Index of Ant is: " + indexOfAnt);
  }

}
