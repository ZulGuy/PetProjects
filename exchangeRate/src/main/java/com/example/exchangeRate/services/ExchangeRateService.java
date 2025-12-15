package com.example.exchangeRate.services;

import com.example.exchangeRate.models.Currency;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Objects;

public class ExchangeRateService {

  private static final String DATA = "src/main/resources/exchangeRate.json";

  public static float exchange(String code, float sum) {
    ObjectMapper objectMapper = new ObjectMapper();
    File file = new File(DATA);
    Currency[] currency = new Currency[2];
    try {
      currency = objectMapper.readValue(file, Currency[].class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    float rateUsd = currency[0].getRate();
    float rateEur = currency[1].getRate();
    if(Objects.equals(code, "EUR")) {
      return sum * rateUsd / rateEur;
    } else if(Objects.equals(code, "USD")) {
      return sum * rateEur / rateUsd;
    }
    throw new InvalidParameterException("Unsupported currency");
  }

}
