package mapinterface;

import java.io.Closeable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapInterfaceExample {

  public static void main(String[] args) {

    //TreeMap
    TreeMap<Integer, String> phoneCodes = new TreeMap<>();
    phoneCodes.put(90, "Turkey");
    phoneCodes.put(44, "United Kingdom");
    phoneCodes.put(33, "France");
    phoneCodes.put(49, "Germany");
    phoneCodes.put(20, "Egypt");
    phoneCodes.put(49, "Germany");
    phoneCodes.put(91, "India");

    System.out.println("phoneCodes = " + phoneCodes);
//    System.out.println("Descending map = " + phoneCodes.descendingMap());
//    System.out.println("Descending key set = " + phoneCodes.descendingKeySet());

    phoneCodes.remove(44);
    for(Map.Entry<Integer, String> phoneCodeEntry : phoneCodes.entrySet()) {
      System.out.println(phoneCodeEntry.getKey() + " is phone code of the " + phoneCodeEntry.getValue());
    }

    //LinkedHashMap
//    LinkedHashMap<String, String> colorsMap = new LinkedHashMap<>();
//    colorsMap.put("BMW", "Blue");
//    colorsMap.put("Opel", "Green");
//    colorsMap.put("Ferrari", "Red");
//    colorsMap.put("Ferrari", "White");
//    colorsMap.put("Mercedes", "Black");
//
//    System.out.println("colorsMap = " + colorsMap);

    //HashMap
//    HashMap<String, Integer> agesMap = new HashMap<>();
//    agesMap.put("Alex", 40);
//    agesMap.put("James", 20);
//    agesMap.put("James", 30);
//    agesMap.put("Ronaldo", 35);
//    agesMap.put("Mary", 25);
//
//    System.out.println("agesMap = " + agesMap);

//    Map<String, Integer> carsMap = new HashMap<>();
//    carsMap.put("Mercedes", 40000);
//    carsMap.put("Ferrari", 35000);
//    carsMap.put("BMW", 30000);
//    carsMap.put("BMW", 25000);
//    carsMap.put("Ford", 20000);
//
//    System.out.println("carsMap = " + carsMap);
////    System.out.println("Price of BMW = " + carsMap.get("BMW"));
//
//    carsMap.remove("Ford");
//    carsMap.replace("Mercedes", 45000);
//
//    System.out.println("Last version of carsMap = " + carsMap);
//
////    for(String car : carsMap.keySet()) {
////      System.out.println("Price of " + car + " is " + carsMap.get(car));
////    }
//
//    for(Map.Entry<String, Integer> mapEntry : carsMap.entrySet()) {
//
//      System.out.println("Price of " + mapEntry.getKey() + " is " + mapEntry.getValue());
//
//    }

  }

}
