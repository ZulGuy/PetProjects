package com.studying.utilitybillsbot;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillsService {

  private final BillsRepository billsRepository;
  private final RatesRepository ratesRepository;

  @Autowired
  public BillsService(BillsRepository billsRepository, RatesRepository ratesRepository) {
    this.billsRepository = billsRepository;
    this.ratesRepository = ratesRepository;
  }

  public Bill findById(int id) {
    return billsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bill not found"));
  }

  public List<Bill> findByChatId(long chatId) {
    return billsRepository.findByChatId(chatId).orElseThrow(() -> new EntityNotFoundException("Bill not found"));
  }

  public Bill findByMonthAndYear(int month, int year) {
    return billsRepository.findByMonthAndYear(month, year).orElseThrow(() -> new EntityNotFoundException("Bill not found"));
  }

  public void save(Bill bill) {
    billsRepository.save(bill);
  }

  public void deleteById(int id) {
    billsRepository.deleteById(id);
  }

  public String calculateTotalCost(long chatId) {
    double totalCost = 0;
    Bill currentBill = billsRepository.findByMonthAndYear(
        LocalDate.now().getMonthValue(), LocalDate.now().getYear()).orElseThrow(() -> new EntityNotFoundException("Bill not found"));
    Rate currentRate = ratesRepository.findByChatId(chatId).orElseThrow(() -> new EntityNotFoundException("Rate not found"));
    currentBill.setElectricityCost(currentBill.getElectricity() * currentRate.getElectricityRate());
    currentBill.setColdWaterCost(currentBill.getColdWater() * currentRate.getColdWaterRate());
    currentBill.setHotWaterCost(currentBill.getHotWater() * currentRate.getHotWaterRate());
    currentBill.setGasCost(currentBill.getGas() * currentRate.getGasRate());
    currentBill.setTotalCost(currentBill.getElectricityCost() + currentBill.getColdWaterCost() + currentBill.getHotWaterCost() + currentBill.getGasCost());
    billsRepository.save(currentBill);
    return String.valueOf(currentBill.getTotalCost());
  }

}
