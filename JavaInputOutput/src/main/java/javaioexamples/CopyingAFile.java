package javaioexamples;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Scanner;

public class CopyingAFile {

  public static final String FILE_NAME = "my file.txt";

  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);
    System.out.println("Please enter a file name");
    String newFileName = scanner.nextLine();

    try (Reader reader = new FileReader(FILE_NAME); Writer writer = new FileWriter(
        newFileName + ".txt")) {

      if (reader.ready()) {
        System.out.println("Data is reading...");

        int characterRead;

        do {
          characterRead = reader.read();
          if (characterRead != -1) {
            writer.write(characterRead);
          }
        } while (characterRead != -1);

        System.out.println("The file is copied");

      }

      scanner.close();

    } catch (IOException e) {
      System.out.println("An error occurred when creating FileReader from a file " + FILE_NAME);
      System.out.println(
          "An error occurred when creating FileWriter from a file " + newFileName + ".txt");
    }

  }

}
