package com.studying.utilitybillsbot;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatesService {

  private final RatesRepository ratesRepository;

  @Autowired
  public RatesService(RatesRepository ratesRepository) {
    this.ratesRepository = ratesRepository;
  }

  public Rate findById(int id) {
    return ratesRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Rate not found"));
  }

  public Rate findByChatId(long chatId) {
    return ratesRepository.findByChatId(chatId).orElseThrow(() -> new EntityNotFoundException("Rate not found"));
  }

  public Rate findByMonthAndYear(int month, int year) {
    return ratesRepository.findByMonthAndYear(month, year).orElseThrow(() -> new EntityNotFoundException("Rate not found"));
  }

  public void save(Rate rate) {
    ratesRepository.save(rate);
  }

  public void deleteById(int id) {
    ratesRepository.deleteById(id);
  }

}
