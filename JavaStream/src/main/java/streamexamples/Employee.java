package streamexamples;

public class Employee {

  String name;
  int salary;
  public enum Sex{ MALE, FEMALE}
  Sex gender;

  //Constructor
  public Employee(String name, int salary, Sex gender) {
    this.name = name;
    this.salary = salary;
    this.gender = gender;
  }


  //getter methods
  public String getName() {
    return name;
  }

  public int getSalary() {
    return salary;
  }

  public Sex getGender() {
    return gender;
  }
}
