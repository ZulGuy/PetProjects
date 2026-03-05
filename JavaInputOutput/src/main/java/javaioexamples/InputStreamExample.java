package javaioexamples;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamExample {

  public static final String FILE_NAME = "example.txt";

  public static void main(String[] args) {

    try(InputStream inputStream = new FileInputStream(FILE_NAME)) {
      int length = inputStream.available();
      System.out.println("Length: " + length);

      System.out.print("Reading data... ");

      byte[] characterArray = new byte[11];
      inputStream.skip(5);
      int characterRead = inputStream.read(characterArray, 0, 11);

      for (int offset = 0; offset < 11; offset++) {
        String characterString = new String(characterArray, offset, 1);
        System.out.print(characterString);
        Thread.sleep(500L);
      }

//      System.out.print("Reading data... ");
//      int eachCharAsDecimal = inputStream.read();
//
//      while(eachCharAsDecimal != -1) {
//        char eachCharacter = (char) eachCharAsDecimal;
//        String eachCharAsString = String.valueOf(eachCharacter);
//        System.out.print(eachCharAsString);
//        Thread.sleep(1000);
//        eachCharAsDecimal = inputStream.read();
//      }

      System.out.println();
      System.out.println("Reading completed");

    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }

  }

}
