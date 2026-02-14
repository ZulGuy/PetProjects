package recordclasses;

public class OuterPerson {

  public int a = 10;

  //nested or local record classes are static
  public record InnerPersonClass(String name) {

    public void show() {
      System.out.println("a: " + a);
    }

  }

}
