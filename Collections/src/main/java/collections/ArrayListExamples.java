package collections;

import java.util.ArrayList;

public class ArrayListExamples {

  public static void main(String[] args) {

    ArrayList<Integer> ages = new ArrayList<>();
    ages.add(20);
    ages.add(25);
    ages.add(30);

    System.out.println("ages: " + ages);

    ArrayList<Object> mixedList = new ArrayList<>();
    mixedList.add("Java");
    mixedList.add(10);
    mixedList.add(true);

    System.out.println("mixedList: " + mixedList);

    ArrayList<String> animals = new ArrayList<>();
    animals.add("Lion");
    animals.add("Cat");
    animals.add(2, "Dog");
    animals.add(1, "Cow");
    //Lion, Cow, Cat, Dog
    System.out.println("animals: " + animals);

    animals.remove(2);//removes element at index 2

    System.out.println("animals: " + animals);

    animals.remove("Lion");

    System.out.println("animals: " + animals);

    animals.set(1, "Ant");

    System.out.println("animals: " + animals);

    ArrayList<String> cars = new ArrayList<>();
    System.out.println("size: " + cars.size());
    System.out.println("is cars empty? " + cars.isEmpty());
    cars.add("Mercedes");
    cars.add("Ferrari");
    cars.add("BMW");
    cars.add("Ford");

    System.out.println("size: " + cars.size());
    System.out.println("is cars empty? " + cars.isEmpty());

    System.out.println("is cars contain Opel? " + cars.contains("Opel"));//false
    System.out.println("is cars contain BMW? " + cars.contains("BMW"));//true

    cars.clear();
    System.out.println("size: " + cars.size());
    System.out.println("is cars empty? " + cars.isEmpty());

    ArrayList<String> names = new ArrayList<>();
    names.add("David");
    names.add("Andy");
    names.add("Clark");

    ArrayList<Object> names2 = new ArrayList<>();
    names2.add("Clark");
    names2.add("Andy");
    names2.add("David");

    System.out.println(names.equals(names2));//false

//    ArrayList<Integer> myList1 = {1, 2, 3, 4, 5};//we cannot initialize the ArrayList like Array

  }

}
