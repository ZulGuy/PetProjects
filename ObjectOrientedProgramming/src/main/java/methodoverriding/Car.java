package methodoverriding;

public class Car extends Vehicle {

  @Override
  protected void start () {
    System.out.println("Car has started");
  }

  @Override
  public void accelerate (int speed) {
    System.out.println("Car accelerates at " + speed);
  }

  @Override
  public void stop () {
    System.out.println("Car has stopped");
  }

}
