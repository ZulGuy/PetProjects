package lambdaexpression;

public class MainTest {

  public static void main(String[] args) {

    //Anonymous Class
//    Animals animal = new Animals() {
//      @Override
//      public void show(String animal, int speed) {
//        System.out.println(animal + " runs at " + speed + " km/h");
//      }
//    };

    //Lambda Expression
    Animals animal = (a,s) -> System.out.println(a + " runs at " + s + " km/h");

  }

}
