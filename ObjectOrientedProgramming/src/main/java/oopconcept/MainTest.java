package oopconcept;

public class MainTest {

  public static void main(String[] args) {

    Person person = new Person("David", 60);

    System.out.println(person);

    person.wakeUp();
    person.showWeight();
    person.eat();
    person.showWeight();
    person.doExrecise();
    person.showWeight();

    //toString()
    Person person2 = new Person("David", 60, 30, 2000 );
    System.out.println(person2);


  }

}
