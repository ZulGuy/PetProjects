import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceExample {

  public static void main(String[] args) {

    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
    ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);
    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    ExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(4);
    ExecutorService singleScheduledThreadPool = Executors.newSingleThreadScheduledExecutor();


    for (int i = 0; i < 5; i++) {
      Task task = new Task("Task " + i);
      scheduledExecutorService.schedule(task, i*2, TimeUnit.SECONDS);
    }
    scheduledExecutorService.scheduleAtFixedRate(new Task("Fixe Rate Task")
    ,1
    ,5
    ,TimeUnit.SECONDS
    );

    scheduledExecutorService.scheduleWithFixedDelay(
        new Task("Fixed Delay Task"),
        2
        ,5
        ,TimeUnit.SECONDS
    );
    try {
      Thread.sleep(20000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    scheduledExecutorService.shutdown();
    System.out.println("ScheduledExecutorService shutdown");

  }

}
