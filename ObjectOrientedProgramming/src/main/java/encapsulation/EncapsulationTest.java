package encapsulation;

public class EncapsulationTest {

  public static void main(String[] args) {

    Car car = new Car("Ferrari", 320, false);
//    car.isAutomatic;
    car.setAutomatic(true);
    System.out.println("Model: " + car.getModel());
    System.out.println("MaxSpeed: " + car.getMaxSpeed());
    System.out.println("Car is automatic: " + car.isAutomatic());
  }

}
