public class Counter {

  private int count = 0;

  public synchronized void increment() {
    count++;
  }

  //getter method of count
  public int getCount() {
    return count;
  }

}
