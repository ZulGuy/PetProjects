import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicVariablesExample {

  public static void main(String[] args) {

    AtomicInteger atomicInteger = new AtomicInteger(0);
    ExecutorService executorService = Executors.newFixedThreadPool(3);

    for (int i = 0; i < 5; i++) {
      executorService.submit(() -> {

        String threadName = Thread.currentThread().getName();
        int newValueOfCounter = atomicInteger.incrementAndGet();
        System.out.println(threadName + " incremented the value of counter to " + newValueOfCounter);

      });
    }
    executorService.shutdown();
    try {
      executorService.awaitTermination(10, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    System.out.println("Final value of counter is " + atomicInteger.get());

  }

}
