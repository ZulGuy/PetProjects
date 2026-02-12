package oopconcept;

public class Person {

  private String name;
  private double weight;
  private int age;
  private int salary;

  //This is a two-parameters constructor
  public Person (String personName, double weight) {

    this.name = personName;
    this.weight = weight;

  }

  //This is a four-parameters constructor
  public Person (String name, double weight, int age, int salary) {
    this.name = name ;
    this.weight = weight;
    this.age = age;
    this.salary = salary;
  }

  public void wakeUp () {
    System.out.println(this.name + " woke up");
  }

  public void eat () {
    System.out.println(this.name + " had breakfast");
    this.weight += 1;
  }

  public void doExrecise () {
    System.out.println(this.name + " did exercise");
    this.weight -= 1;
  }

  public void showWeight () {
    System.out.println("The current weight of " + this.name + " is " + this.weight + " kg");
  }

  public String toString () {
    return "Name: " + this.name + " Age: " + this.age + " Weight: " + this.weight + " Salary: " + this.salary;
  }

}
