public class MainTest {

  public static void main(String[] args) {

    Counter counter = new Counter();

    Thread thread1 = new Thread(() -> {
      for (int i = 0; i < 1000; i++) {
        counter.increment();
      }
    });

    Thread thread2 = new Thread(() -> {
      for (int i = 0; i < 1000; i++) {
        counter.increment();
      }
    });

    thread1.start();
    thread2.start();

    try {
      thread1.join();
      thread2.join();

      System.out.println("Final value of counter: " + counter.getCount());
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }

}
