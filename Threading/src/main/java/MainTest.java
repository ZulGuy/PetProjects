public class MainTest {

  public static void main(String[] args) {

//    MyThread myThread = new MyThread();
//    myThread.start();
//    System.out.println("Thread is started");

    MyRunnable myRunnable = new MyRunnable();
    Thread thread = new Thread(myRunnable);
    thread.start();
    System.out.println("Thread is started");

  }

}
