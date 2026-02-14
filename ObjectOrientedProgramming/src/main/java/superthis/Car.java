package superthis;

public class Car extends Vehicle {

  boolean isAutomatic = false;

  public Car(String type, String model, int maxSpeed, boolean isAutomatic) {
    super(type, model, maxSpeed);//super call -> Calling the constructor of the superclass
    this.isAutomatic = isAutomatic;
  }

  //getter method
  public boolean isAutomatic() {
    return isAutomatic;
  }
}
