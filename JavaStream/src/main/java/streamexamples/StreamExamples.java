package streamexamples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class StreamExamples {

  public static void main(String[] args) {

    List<Integer> numbers = Arrays.asList(1,4,10,3,6,1,15,4,9,16,5);
    numbers.stream()
        .filter(n -> n % 2 == 0)
        .map(n -> ++n)
        .sorted()
        .distinct()
        .forEach(System.out::println);

    //Terminal Operation Methods -> do not return a stream (stream is closed)
    //Intermediate Operation Method -> returns a stream

    List<String> animals = new ArrayList<>();
    animals.add("Cat");
    animals.add("Dog");
    animals.add("Cow");

    Stream<String> animalStream = animals.stream();
    animalStream.map(String::toUpperCase).forEach(System.out::println);
//    IllegalStateException because animalStream is terminated
//    animalStream.map(String::toLowerCase).forEach(System.out::println);


  }

}
