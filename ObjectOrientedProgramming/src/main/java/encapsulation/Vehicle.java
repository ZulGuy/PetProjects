package encapsulation;

public class Vehicle {

  private String model;
  private int maxSpeed;

  //constructor
  public Vehicle(String model, int maxSpeed) {
    this.model = model;
    this.maxSpeed = maxSpeed;
  }

  //getter method of maxSpeed
  public int getMaxSpeed() {
    return maxSpeed;
  }

  //setter method of maxSpeed
  public void setMaxSpeed(int maxSpeed) {
    this.maxSpeed = maxSpeed;
  }

  //getter method of model
  public String getModel() {
    return model;
  }

  //setter method of model
  public void setModel(String model) {
    this.model = model;
  }

}
