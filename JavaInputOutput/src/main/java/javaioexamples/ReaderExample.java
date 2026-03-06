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
//      char[] charArray = new char[6];
      String text = "";

      if (reader.ready()) {

//        reader.skip(32);

        do {

          characterRead = reader.read();
//          characterRead = reader.read(charArray, 0, 6);

          if(characterRead != -1) {
            text = text.concat(String.valueOf((char)characterRead));

//            String charString = new String(charArray);
//            System.out.println(charString);

//            System.out.print((char) characterRead);
//            Thread.sleep(300L);
          }

        } while (characterRead != -1);

      }

      int index = text.indexOf("Writer");
      System.out.println(text.substring(index, index + 6));

      System.out.println();
      System.out.println("Reading completed");

    } catch (IOException e) {
      e.printStackTrace();
    }


  }

}
