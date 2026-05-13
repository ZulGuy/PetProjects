public class MainTest {

  public static void main(String[] args) {

    //Producer Consumer Example
//    SharedBuffer sharedBuffer = new SharedBuffer();
//
//    //Producer Thread
//    Thread producerThread = new Thread(() -> {
//
//      for (int i = 0; i < 5; i++) {
//        sharedBuffer.produce(i);
//        try {
//          Thread.sleep(500L);
//        } catch (InterruptedException e) {
//          e.printStackTrace();
//        }
//      }
//
//    }, "Producer Thread");
//
//    //Consumer Thread
//    Thread consumerThread = new Thread(() -> {
//
//      for (int i = 0; i < 5; i++) {
//        sharedBuffer.consume();
//        try {
//          Thread.sleep(800L);
//        } catch (InterruptedException e) {
//          e.printStackTrace();
//        }
//      }
//
//    }, "Consumer Thread");
//
//    producerThread.start();
//    consumerThread.start();

    //Producer Consumer Example With Lock
    SharedBufferWithLock sharedBufferWithLock = new SharedBufferWithLock();

    //Producer Thread
    Thread producerThread = new Thread(() -> {
      for (int i = 0; i < 5; i++) {
        sharedBufferWithLock.produce(i);
        try {
          Thread.sleep(500L);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    }, "Producer Thread");

    //Consumer Thread
    Thread consumerThread = new Thread(() -> {
      for (int i = 0; i < 5; i++) {
        sharedBufferWithLock.consume();
        try {
          Thread.sleep(800L);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    }, "Consumer Thread");

    producerThread.start();
    consumerThread.start();

  }

}
