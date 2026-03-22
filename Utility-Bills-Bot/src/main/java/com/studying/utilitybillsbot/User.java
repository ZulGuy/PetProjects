package com.studying.utilitybillsbot;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(unique = true, name = "chat_id")
  private long chatId;
  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private Status status;
  @Column(name = "bills_status")
  @Enumerated(EnumType.STRING)
  private BillsStatus billsStatus;
  @Column(name = "rates_status")
  @Enumerated(EnumType.STRING)
  private RatesStatus ratesStatus;

  public User(int id, long chatId) {
    this.id = id;
    this.chatId = chatId;
    this.status = Status.NONE;
    this.billsStatus = BillsStatus.NONE;
    this.ratesStatus = RatesStatus.NONE;
  }

  public User(long chatId) {
    this.chatId = chatId;
    this.status = Status.NONE;
    this.billsStatus = BillsStatus.NONE;
    this.ratesStatus = RatesStatus.NONE;
  }

  public User() {
    this.status = Status.NONE;
    this.billsStatus = BillsStatus.NONE;
    this.ratesStatus = RatesStatus.NONE;
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

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public BillsStatus getBillsStatus() {
    return billsStatus;
  }

  public void setBillsStatus(BillsStatus billsStatus) {
    this.billsStatus = billsStatus;
  }

  public RatesStatus getRatesStatus() {
    return ratesStatus;
  }

  public void setRatesStatus(RatesStatus ratesStatus) {
    this.ratesStatus = ratesStatus;
  }
}
