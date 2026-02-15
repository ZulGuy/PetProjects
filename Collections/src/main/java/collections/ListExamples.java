package collections;

import java.util.*;

public class ListExamples {

  public static void main(String[] args) {

    List<String> list1 = new ArrayList<>();
    List<Integer> list2;//Declaration
    list2 = new ArrayList<>();//Initialization
//    List<double> list3 = new ArrayList<>();//we cannot use primitive type in Collections
    List<Double> list3 = new ArrayList<>();
    Collection<Integer> list4 = new ArrayList<>();

    List<String> list5 = new ArrayList<>();
    list5.add("Hello Java Developers");
    String message = list5.get(0);

  }

}
