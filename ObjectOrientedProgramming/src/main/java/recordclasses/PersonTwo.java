package recordclasses;

import java.util.Objects;

public class PersonTwo {

  private final String name;
  private final int age;

  //two-parameter constructor
  PersonTwo(String name, int age) {
    this.name = name;
    this.age = age;
  }

  //getters
  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof PersonTwo personTwo)) {
      return false;
    }
    return age == personTwo.age && Objects.equals(name, personTwo.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, age);
  }

  @Override
  public String toString() {
    return "PersonTwo{" +
        "name='" + name + '\'' +
        ", age=" + age +
        '}';
  }
}
