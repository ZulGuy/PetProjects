package examplethis;

public class ThisKeyword {

  String car;
  int year;

  //default constructor (empty / parameterless constructor)
  public ThisKeyword () {
    this("BMW", 2026);//calling two-parameter constructor
  }

  //two-parameter constructor
  public ThisKeyword (String car, int model) {

//    this();

    this.car = car;
    year = model;
  }

  public void printHello () {
    System.out.print("Hello ");
    this.printWorld();
  }

  public void printWorld () {
//    this();
    System.out.println("World");
  }

  public static void main(String[] args) {

    ThisKeyword myObject = new ThisKeyword();
    System.out.println("Car: " + myObject.car);
    System.out.println("Year: " + myObject.year);

    myObject.printHello();

  }

}
