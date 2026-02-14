package recordclasses;

public record Person(String name, int age) implements Drink {

//  public Person() {}// не можемо мати default конструктор в record класах

  //не має сенсу створювати об'єкт record класу без полів
//  public Person() {
//
//    this(null, 0);
//
//  }

  //Compact canonical constructor
  public Person{
    if (age < 0) {
      System.out.println("Age cannot be negative");
    }
  }

  @Override
  public void canDrink(String drinkName) {

  }

//  static final int age = 10;// Працює в record класах

  //custom method
  public String isAdult() {
    return age > 18 ? "you are an adult" : "you are not an adult";
  }

}
