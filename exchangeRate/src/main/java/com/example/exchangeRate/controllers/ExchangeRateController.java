package com.example.exchangeRate.controllers;

import com.example.exchangeRate.services.ExchangeRateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExchangeRateController {

  @GetMapping("/exchange")
  public String getExchangeRate(@RequestParam float sum, @RequestParam String currency) {
    float exchangeRate = ExchangeRateService.exchange(currency, sum);
    return"excahnged sum:" + exchangeRate + currency;
  }

}
