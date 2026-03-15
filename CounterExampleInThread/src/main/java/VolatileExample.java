public class VolatileExample {

  private volatile boolean running = true;

  public void stopRunning() {
    running = false;
  }

  public void run() {
    while(running){
      System.out.println("Thread is running...");
    }
    System.out.println("Thread is stopped");
  }

}
