package lambdaexpression;

public class MainTest {

  public static void main(String[] args) {

    //Anonymous Class
//    Animals animal = new Animals() {
//      @Override
//      public void show(String animal, int speed) {
//        System.out.println("A " + animal + " can reach speed of " + speed + " kilometers per hour");
//      }
//    };
//
//    animal.show("Cheetah", 90);

    //Lambda Expression
//    Animals animal = (a, s) -> {
//      System.out.println("A " + a + " can reach speed of " + s + " kilometers per hour");
//    };
    Animals animal = (a,s) -> System.out.println("A " + a + " can reach speed of " + s + " kilometers per hour");
    animal.show("Cheetah", 90);
  }

}
