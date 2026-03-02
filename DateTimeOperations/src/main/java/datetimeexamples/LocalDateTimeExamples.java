package datetimeexamples;

import java.time.LocalDateTime;

public class LocalDateTimeExamples {

  public static void main(String[] args) {

    LocalDateTime dateTime = LocalDateTime.now();
    System.out.println("Current date time: " + dateTime);

    int year = dateTime.getYear();
    int hour = dateTime.getHour();
    System.out.println("Current year: " + year);
    System.out.println("Current hour: " + hour);

  }

}
