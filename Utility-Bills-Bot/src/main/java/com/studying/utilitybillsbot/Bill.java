package com.studying.utilitybillsbot;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity(name = "bills")
public class Bill {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(unique = true, name = "chat_id")
  private long chatId;
  @Column(name = "electricity")
  private int electricity;
  @Column(name = "electricity_cost")
  private double electricityCost;
  @Column(name = "cold_water")
  private int coldWater;
  @Column(name = "cold_water_cost")
  private double coldWaterCost;
  @Column(name = "hot_water")
  private int hotWater;
  @Column(name = "hot_water_cost")
  private double hotWaterCost;
  @Column(name = "gas")
  private int gas;
  @Column(name = "gas_cost")
  private double gasCost;
  @Column(name = "total_cost")
  private double totalCost;
  @Column(name = "month")
  private int month;
  @Column(name = "year")
  private int year;

  public Bill(int id, long chatId, int electricity, double electricityCost, int coldWater,
      double coldWaterCost, int hotWater, double hotWaterCost, int gas, double gasCost, double totalCost) {
    this.id = id;
    this.chatId = chatId;
    this.electricity = electricity;
    this.electricityCost = electricityCost;
    this.coldWater = coldWater;
    this.coldWaterCost = coldWaterCost;
    this.hotWater = hotWater;
    this.hotWaterCost = hotWaterCost;
    this.gas = gas;
    this.gasCost = gasCost;
    this.totalCost = totalCost;
    this.month = LocalDate.now().getMonthValue();
    this.year = LocalDate.now().getYear();
  }

  public Bill(long chatId, int eletricity, double electricityCost, int coldWater, double coldWaterCost, int hotWater, double hotWaterCost, int gas, double gasCost, double totalCost) {
    this.chatId = chatId;
    this.electricity = eletricity;
    this.electricityCost = electricityCost;
    this.coldWater = coldWater;
    this.coldWaterCost = coldWaterCost;
    this.hotWater = hotWater;
    this.hotWaterCost = hotWaterCost;
    this.gas = gas;
    this.gasCost = gasCost;
    this.month = LocalDate.now().getMonthValue();
    this.year = LocalDate.now().getYear();
  }

  public Bill(long chatId) {
    this.chatId = chatId;
    this.month = LocalDate.now().getMonthValue();
    this.year = LocalDate.now().getYear();
  }

  public Bill() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public long getChatId() {
    return chatId;
  }

  public void setChatId(long chatId) {
    this.chatId = chatId;
  }

  public int getElectricity() {
    return electricity;
  }

  public void setElectricity(int electricity) {
    this.electricity = electricity;
  }

  public double getElectricityCost() {
    return electricityCost;
  }

  public void setElectricityCost(double electricityCost) {
    this.electricityCost = electricityCost;
  }

  public int getColdWater() {
    return coldWater;
  }

  public void setColdWater(int coldWater) {
    this.coldWater = coldWater;
  }

  public double getColdWaterCost() {
    return coldWaterCost;
  }

  public void setColdWaterCost(double coldWaterCost) {
    this.coldWaterCost = coldWaterCost;
  }

  public int getHotWater() {
    return hotWater;
  }

  public void setHotWater(int hotWater) {
    this.hotWater = hotWater;
  }

  public double getHotWaterCost() {
    return hotWaterCost;
  }

  public void setHotWaterCost(double hotWaterCost) {
    this.hotWaterCost = hotWaterCost;
  }

  public int getGas() {
    return gas;
  }

  public void setGas(int gas) {
    this.gas = gas;
  }

  public double getGasCost() {
    return gasCost;
  }

  public void setGasCost(double gasCost) {
    this.gasCost = gasCost;
  }

  public double getTotalCost() {
    return totalCost;
  }

  public void setTotalCost(double totalCost) {
    this.totalCost = totalCost;
  }

  public int getMonth() {
    return month;
  }

  public void setMonth(int month) {
    this.month = month;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }
}
