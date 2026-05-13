import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrentHashMapExample {

  public static void main(String[] args) {

    Map<String, String> taskResult = new ConcurrentHashMap<>();
    ExecutorService executorService = Executors.newFixedThreadPool(3);

    for (int i = 0; i < 6; i++) {

      String taskName = "Task " + i;
      executorService.submit(() -> {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " is executing " + taskName);
        try {
          Thread.sleep(1000L);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        taskResult.put(taskName, "Completed by: " + threadName);
      });

    }
    executorService.shutdown();
    try {
      executorService.awaitTermination(10, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    System.out.println("Task Result: ");
    taskResult.forEach((task, result) -> System.out.println(task + ": " + result));

  }

}
