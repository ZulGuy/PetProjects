package serializationdeserealization;

import java.io.Serializable;

public class Person implements Serializable {

  String name, surname;
  int age;
  boolean married;
  transient long personID;
  transient Weights weight;
  double personWeight;

  //constructor
  public Person(String name, String surname, int age, boolean married, long personID, double weight) {
    this.name = name;
    this.surname = surname;
    this.age = age;
    this.married = married;
    this.personID = personID;
    this.weight = new Weights(weight);
    this.personWeight = this.weight.weight;
  }

  @Override
  public String toString() {
    return "\n" + "Name: " + name + "\nSurname: " + surname + "\nAge: " + age + "\nMarried: "
        + married + "\nPersonID: " + personID + "\nWeight: " + personWeight;
  }
}
