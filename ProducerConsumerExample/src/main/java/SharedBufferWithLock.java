import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedBufferWithLock {

  private int data;
  private boolean hasData = false;
  private final Lock lock = new ReentrantLock();
  private final Condition condition = lock.newCondition();

  //producer
  public void produce(int value) {

    lock.lock();
    try {
      while (hasData) {
        condition.await();
      }
      data = value;
      System.out.println(Thread.currentThread().getName() + " produced: " + data);
      hasData = true;
      condition.signal();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    } finally {
      lock.unlock();
    }


  }

  //consumer
  public void consume() {

    lock.lock();
    try {
      while(!hasData) {
        condition.await();
      }
      System.out.println(Thread.currentThread().getName() + " consumed: " + data);
      hasData = false;
      condition.signal();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    } finally {
      lock.unlock();
    }

  }

}
