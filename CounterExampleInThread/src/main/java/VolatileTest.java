public class VolatileTest {

  public static void main(String[] args) {

    VolatileExample volatileExample = new VolatileExample();

    Thread thread = new Thread(volatileExample::run);
    thread.start();

    try{
      Thread.sleep(1000L);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    volatileExample.stopRunning();

  }

}
