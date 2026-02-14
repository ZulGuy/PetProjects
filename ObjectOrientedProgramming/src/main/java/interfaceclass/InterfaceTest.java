package interfaceclass;

public class InterfaceTest {

  public static void main(String[] args) {

    Bird bird = new Bird();
    bird.canEat();
    bird.canDrink();
    bird.canFly();
//    CanFly.getAge();//Це використання static методу інтерфейсу
    bird.canFly2();//Це використання default методу інтерфейсу

    System.out.println("----------------------------------");

    People people = new People();
    people.canEat();
    people.canDrink();

  }

}
