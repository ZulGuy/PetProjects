package streamexamples;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReductionOperationsWithCollect {

  public static void main(String[] args) {

    List<String> names = Arrays.asList("David","Clark","Marry","Sara","Andy");
    names.stream()
        .filter(n -> n.length() > 4)
        .collect(Collectors.toList());
    
  }

}
