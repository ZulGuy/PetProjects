package abstractclass;

non-sealed public class Car extends Vehicle {

  public Car (String type, String model) {
    super(type, model);
  }

  @Override
  int getMaxSpeed() {
    final int MAX_CAR_SPEED = 320;
    System.out.println("Max speed of car is " + MAX_CAR_SPEED);
    return MAX_CAR_SPEED;
  }

  //overriding non-abstract methods
  @Override
  public void start() {
    System.out.println("Car has started");
  }

  @Override
  public void stop() {
    System.out.println("Car has stopped");
  }
}
