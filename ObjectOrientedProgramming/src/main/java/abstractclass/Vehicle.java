package abstractclass;

sealed public abstract class Vehicle permits Car {

  String type;
  String model;

  //constructor
  public Vehicle(String type, String model) {
    this.type = type;
    this.model = model;
  }

  //non-abstract methods
  public void start () {
    System.out.println("Vehicle has started");
  }

  public void stop () {
    System.out.println("Vehicle has stopped");
  }

  //abstract method
  abstract int getMaxSpeed();

}
