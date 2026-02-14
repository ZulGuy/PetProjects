package interfaceclass;

public interface CanFly {

  //Код нижче доступний з Java 8 - це дефолтні методи в інтерфейсах
  //Це означає, що вони можуть мати певну логіку, а не просто бути абстрактними
  default void canFly2() {
    System.out.println("Can fly");
  }

  void canFly();

  //З static теж працює
  public static int getAge () {
    return 5;
  }

}
