package serializationdeserealization;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class TestDeserialization {

  public static void main(String[] args) {

    try(FileInputStream fileIn = new FileInputStream("persons.txt"); ObjectInputStream in = new ObjectInputStream(fileIn)) {

      Object personObject = in.readObject();

//      System.out.println(personObject);

      for(Person eachPerson : (ArrayList<Person>) personObject) {

        System.out.println(eachPerson);
        System.out.println();

      }

    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }

  }

}
