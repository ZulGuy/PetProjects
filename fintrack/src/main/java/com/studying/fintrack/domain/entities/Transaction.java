package com.studying.fintrack.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "transactions")
public class Transaction {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;
  @Column(name = "booked_at", nullable = false)
  private Timestamp bookedAt;
  @Column(name = "amount_decimal", nullable = false)
  private double amountDecimal;
  @Column(name = "currency", nullable = false)
  private String currency;
  @Column(name = "raw_description", nullable = false)
  private String rawDescription;
  @Column(name = "note", nullable = true)
  private String note;
  @ManyToOne
  @JoinColumn(name = "account_id", nullable = false)
  private Account account;
  @ManyToOne
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

  public Transaction() {
  }

  public Transaction(Timestamp bookedAt, double amountDecimal, String currency,
      String rawDescription, Account account) {
    this.bookedAt = bookedAt;
    this.amountDecimal = amountDecimal;
    this.currency = currency;
    this.rawDescription = rawDescription;
    this.account = account;
  }

  public Transaction(int id, Timestamp bookedAt, double amountDecimal, String currency,
      String rawDescription, String note, Account account) {
    this.id = id;
    this.bookedAt = bookedAt;
    this.amountDecimal = amountDecimal;
    this.currency = currency;
    this.rawDescription = rawDescription;
    this.note = note;
    this.account = account;
  }

  public Transaction(int id, Timestamp bookedAt, double amountDecimal, String currency,
      String rawDescription, Account account, Category category) {
    this.id = id;
    this.bookedAt = bookedAt;
    this.amountDecimal = amountDecimal;
    this.currency = currency;
    this.rawDescription = rawDescription;
    this.account = account;
    this.category = category;
  }

  public Transaction(int id, Timestamp bookedAt, double amountDecimal, String currency,
      String rawDescription, String note, Account account, Category category) {
    this.id = id;
    this.bookedAt = bookedAt;
    this.amountDecimal = amountDecimal;
    this.currency = currency;
    this.rawDescription = rawDescription;
    this.note = note;
    this.account = account;
    this.category = category;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Timestamp getBookedAt() {
    return bookedAt;
  }

  public void setBookedAt(Timestamp bookedAt) {
    this.bookedAt = bookedAt;
  }

  public double getAmountDecimal() {
    return amountDecimal;
  }

  public void setAmountDecimal(double amountDecimal) {
    this.amountDecimal = amountDecimal;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getRawDescription() {
    return rawDescription;
  }

  public void setRawDescription(String rawDescription) {
    this.rawDescription = rawDescription;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }
}
