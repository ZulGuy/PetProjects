package collections;

import java.util.Deque;
import java.util.LinkedList;

public class DequeInterfaceExample {

  public static void main(String[] args) {

    Deque<String> carsDeque = new LinkedList<>();
    carsDeque.add("Mercedes");
    carsDeque.add("BMW");
    carsDeque.add("Ferrari");
    carsDeque.addFirst("Ford");
    carsDeque.addLast("Opel");
    carsDeque.push("Honda");
    carsDeque.offer("Hyundai");
    carsDeque.offerFirst("Kia");
    carsDeque.offerLast("Renault");

    System.out.println("carsDeque = " + carsDeque);//[Kia, Honda, Ford, Mercedes, BMW, Ferrari, Opel, Hyundai, Renault]

    System.out.println("Removed element = " + carsDeque.removeFirst());
    System.out.println("Last version of carsDeque = " + carsDeque);//[Honda, Ford, Mercedes, BMW, Ferrari, Opel, Hyundai, Renault]

    System.out.println("Removed element = " + carsDeque.removeLast());
    System.out.println("Last version of carsDeque = " + carsDeque);//[Honda, Ford, Mercedes, BMW, Ferrari, Opel, Hyundai]

  }

}
