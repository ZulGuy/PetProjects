package superthis;

public class SupeThisTest {

  public static void main(String[] args) {

    Car car = new Car("Car", "Ferrari", 320, true);
    System.out.println("Type: " + car.getType());
    System.out.println("Model: " + car.getModel());
    System.out.println("MaxSpeed: " + car.getMaxSpeed());
    System.out.println("is car automatic? " + car.isAutomatic());

  }

}
