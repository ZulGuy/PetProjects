package com.studying.utilitybillsbot;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity(name = "rates")
public class Rate {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(unique = true, name = "chat_id")
  private long chatId;
  @Column(name = "electricity_rate")
  private double electricityRate;
  @Column(name = "cold_water_rate")
  private double coldWaterRate;
  @Column(name = "hot_water_rate")
  private double hotWaterRate;
  @Column(name = "gas_rate")
  private double gasRate;
  @Column(name = "month")
  private int month;
  @Column(name = "year")
  private int year;

  public Rate(int id, double electricityRate, double coldWaterRate, double hotWaterRate, double gasRate) {
    this.id = id;
    this.electricityRate = electricityRate;
    this.coldWaterRate = coldWaterRate;
    this.hotWaterRate = hotWaterRate;
    this.gasRate = gasRate;
    this.month = LocalDate.now().getMonthValue();
    this.year = LocalDate.now().getYear();
  }

  public Rate(long chatId, double electricityRate, double coldWaterRate, double hotWaterRate, double gasRate) {
    this.chatId = chatId;
    this.electricityRate = electricityRate;
    this.coldWaterRate = coldWaterRate;
    this.hotWaterRate = hotWaterRate;
    this.gasRate = gasRate;
    this.month = LocalDate.now().getMonthValue();
    this.year = LocalDate.now().getYear();
  }

  public Rate(long chatId) {
    this.chatId = chatId;
    this.month = LocalDate.now().getMonthValue();
    this.year = LocalDate.now().getYear();
  }

  public Rate() {
    this.month = LocalDate.now().getMonthValue();
    this.year = LocalDate.now().getYear();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public double getElectricityRate() {
    return electricityRate;
  }

  public void setElectricityRate(double electricityRate) {
    this.electricityRate = electricityRate;
  }

  public double getColdWaterRate() {
    return coldWaterRate;
  }

  public void setColdWaterRate(double coldWaterRate) {
    this.coldWaterRate = coldWaterRate;
  }

  public double getHotWaterRate() {
    return hotWaterRate;
  }

  public void setHotWaterRate(double hotWaterRate) {
    this.hotWaterRate = hotWaterRate;
  }

  public double getGasRate() {
    return gasRate;
  }

  public void setGasRate(double gasRate) {
    this.gasRate = gasRate;
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
