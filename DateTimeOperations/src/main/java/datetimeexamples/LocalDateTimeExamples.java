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

    LocalDateTime twoDaysAgo = dateTime.minusDays(2);
    System.out.println("2 days ago: " + twoDaysAgo);

    LocalDateTime threeHoursLater = dateTime.plusHours(4);
    System.out.println("4 hours later: " + threeHoursLater);

    //LocalDateTime is immutable
    System.out.println("Current date time: " + dateTime);

    dateTime = dateTime.minusHours(1);
    System.out.println("Current date time: " + dateTime);

    LocalDateTime myBirthday = LocalDateTime.of(2004, 8, 10, 10, 30);
    System.out.println("My birthday: " + myBirthday);

    System.out.println("is my birthday after current date time: " + myBirthday.isAfter(dateTime));
    System.out.println("is my birthday before current date time: " + myBirthday.isBefore(dateTime));

  }

}
