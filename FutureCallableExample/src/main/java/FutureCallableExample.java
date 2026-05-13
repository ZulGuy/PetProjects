import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureCallableExample {

  public static void main(String[] args) {

    ExecutorService executorService = Executors.newFixedThreadPool(3);
    Callable<String> callableTask = () -> {

      String threadName = Thread.currentThread().getName();
      System.out.println(threadName + " is executing Callable task");
      Thread.sleep(2000L);
      return "Result from " + threadName;

    };

    Future<String> future = executorService.submit(callableTask);
    try {
      String result = future.get();
      System.out.println("Result: " + result);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
    executorService.shutdown();

  }

}
