package javaioexamples;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class ReaderExample {

  public static final String FILE_NAME = "test.txt";

  public static void main(String[] args) {

    try (Reader reader = new FileReader(FILE_NAME)) {
      System.out.print("Reading data... ");

      int characterRead;

      if (reader.ready()) {

        do {

          characterRead = reader.read();

          if(characterRead != -1) {
            System.out.print((char) characterRead);
            Thread.sleep(300L);
          }

        } while (characterRead != -1);

      }

      System.out.println();
      System.out.println("Reading completed");

    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }


  }

}
