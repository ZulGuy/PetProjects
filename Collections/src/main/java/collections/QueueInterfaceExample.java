package collections;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class QueueInterfaceExample {

  public static void main(String[] args) {

    Queue<String> carsQueue = new LinkedList<>();
    carsQueue.add("Mercedes");//throws an exception
    carsQueue.offer("Ferrari");//doesn`t throw an exception; returns null instead; Краще юзати цей метод
    carsQueue.offer("Ford");
    carsQueue.offer("BMW");
    carsQueue.offer("Opel");

    System.out.println("carsQueue = " + carsQueue);
    System.out.println("size = " + carsQueue.size());
    System.out.println("First element = " + carsQueue.element());//throws an exception
    //doesn`t throw an exception; returns null instead; Краще юзати цей метод
    System.out.println("First element = " + carsQueue.peek());
//    System.out.println("Removed element = " + carsQueue.remove());//throws an exception
    System.out.println("carsQueue = " + carsQueue);
    System.out.println("size = " + carsQueue.size());
    //doesn`t throw an exception; returns null instead; Краще юзати цей метод
//    System.out.println("Removed element = " + carsQueue.poll());
    System.out.println("carsQueue = " + carsQueue);
    System.out.println("size = " + carsQueue.size());

    //FIFO(First in First out)
    int size = carsQueue.size();
    for(int i = 0; i < size; i++) {
      System.out.println("Removed element = " + carsQueue.poll());
    }

    System.out.println("carsQueue = " + carsQueue);
//    System.out.println("First element = " + carsQueue.peek());//doesn`t throw an exception
//    System.out.println("First element = " + carsQueue.element());//throws an exception NoSuchElementException
//    System.out.println("Removed element = " + carsQueue.poll());//doesn`t throw an exception
//    System.out.println("Removed element = " + carsQueue.remove());//throws an exception NoSuchElementException



  }

}
