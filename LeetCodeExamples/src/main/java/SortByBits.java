import java.util.Arrays;
import java.util.Comparator;

public class SortByBits {

  public static void main(String[] args) {
    System.out.println(Arrays.toString(sortByBits(new int[]{1024,512,256,128,64,32,16,8,4,2,1})));
  }

  public static int[] sortByBits(int[] arr) {
    return Arrays.stream(arr)
        .boxed()
        .sorted(
            Comparator
                .comparingInt(Integer::bitCount)
                .thenComparingInt(Integer::intValue)
        )
        .mapToInt(Integer::intValue)
        .toArray();
  }

}
