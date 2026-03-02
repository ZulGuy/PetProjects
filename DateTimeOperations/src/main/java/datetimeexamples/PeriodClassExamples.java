package datetimeexamples;

import java.time.LocalDate;
import java.time.Period;

public class PeriodClassExamples {

  public static void main(String[] args) {

    Period period = Period.of(5, 11, 20);
    System.out.println("Period: " + period);

    LocalDate date = LocalDate.now();
    System.out.println("Current date: " + date);

    LocalDate afterPeriod = date.plus(period);
    System.out.println("A fter period: " + afterPeriod);

    LocalDate myBirthday = LocalDate.of(2026, 8, 10);
    System.out.println("Remaining time to my birthday: " + Period.between(myBirthday, date));

    //Period with loop
    Period period2 = Period.ofDays(5);
    int i = 10;
    while(i > 0) {
      date = date.plus(period2);
      System.out.println("After 5 days: " + date);
      i--;
    }

  }

}
