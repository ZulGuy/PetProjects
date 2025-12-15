package com.example.exchangeRate.schedulers;

import jakarta.annotation.PostConstruct;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ExchangeRateScheduler {

  private final String usdUrl = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?valcode=USD&date=YYYYMMDD&json";
  private final String eurUrl = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?valcode=EUR&date=YYYYMMDD&json";

  public static String replaceOccurrence(String text, String replaceFrom, String replaceTo, int occurrenceIndex)
  {
    StringBuffer sb = new StringBuffer();
    Pattern p = Pattern.compile(replaceFrom);
    Matcher m = p.matcher(text);
    int count = 0;
    while (m.find())
    {
      if (count++ == occurrenceIndex - 1)
      {
        m.appendReplacement(sb, replaceTo);
      }
    }
    m.appendTail(sb);
    return sb.toString();
  }

  @PostConstruct
  private void init() {
    updateExchangeRate();
  }

  @Scheduled(cron = "0 0 0 * * *")
  public void updateExchangeRate() {
    String date = LocalDate.now().toString();
    date = date.replaceAll("-", "");
    String usd = usdUrl.replaceAll("YYYYMMDD", date);
    String eur = eurUrl.replaceAll("YYYYMMDD", date);
    RestTemplate restTemplate = new RestTemplate();
    String usdToEur =  restTemplate.getForObject(usd, String.class);
    String eurToUsd =  restTemplate.getForObject(eur, String.class);
    String all = usdToEur + eurToUsd;
    all = replaceOccurrence(replaceOccurrence(all, "]", ",", 1), "[\\[]", "", 2);
    try(Writer writer =  new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/main/resources/exchangeRate.json")))) {
      writer.write(all);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
