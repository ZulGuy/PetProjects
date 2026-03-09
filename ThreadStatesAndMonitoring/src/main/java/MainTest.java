public class MainTest {

  public static void main(String[] args) {

    MyThread thread = new MyThread();
    System.out.println("Before start: " + thread.getState());//NEW
    thread.start();
    System.out.println("After start: " + thread.getState());//RUNNABLE

  }

}
