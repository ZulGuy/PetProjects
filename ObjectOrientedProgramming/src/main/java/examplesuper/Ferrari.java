package examplesuper;

public class Ferrari extends Cars {
  boolean isAutomatic = true;

  public Ferrari () {
    super();//invokes the constructor of the superclass
    System.out.println("Default constructor of the subclass");
  }

  public void printGear () {
    System.out.println(isAutomatic);
    System.out.println(super.isAutomatic);
  }

  public void accelerate () {
    System.out.println("Ferrari accelerates faster");
  }

  public void speed () {
    System.out.println("Ferrari reaches 300 speed");
  }

  public void display () {
    speed();
    super.accelerate();
    accelerate();
  }

}
