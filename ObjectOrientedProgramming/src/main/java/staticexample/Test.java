package staticexample;

public class Test {

  public static void main(String[] args) {

    Car.showCurrentSpeed(Car.currentSpeed);
    Car.speedUp(50);
    Car.speedUp(140);
    Car.speedDown(30);
    Car.stop();

    Car obj = new Car();
    obj.speedUp(50);

  }

}
