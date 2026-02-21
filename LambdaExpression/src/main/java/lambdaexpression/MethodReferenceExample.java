package lambdaexpression;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Supplier;

public class MethodReferenceExample {

  public static void main(String[] args) {

    //Static method reference
    Function<String, Integer> stringIntegerFunction = Integer::parseInt;
    Integer result = stringIntegerFunction.apply("123");
    System.out.println(result);

    //Method reference via an object
    String str = "Hello";
    Supplier<String> sutringSupplier =  str::toUpperCase;
    System.out.println(sutringSupplier.get());

    //Calling constructor of a class
    Supplier<ArrayList<String>> arrayListSupplier = ArrayList::new;
    ArrayList<String> list = arrayListSupplier.get();

//    Integer.parseInt("123");

//    List<String> animals = List.of("Cat", "Cow", "Dog", "Horse");
//    Consumer<String> animalConsumer = System.out::println;
//    animals.forEach(animalConsumer);

//    List<String> animals = List.of("Cat", "Cow", "Dog", "Horse");
//    Consumer<String> animalConsumer = System.out::println;
//    animals.forEach(animal -> {
//      if(animal.startsWith("C")) {
//        System.out.println(animal);
//      }
//    });

  }

}
