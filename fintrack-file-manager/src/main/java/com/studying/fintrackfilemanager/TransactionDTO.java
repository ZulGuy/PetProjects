package com.studying.fintrackfilemanager;

import com.poiji.annotation.ExcelCellName;
import java.sql.Timestamp;

public class TransactionDTO {

  private Timestamp bookedAt;
  private double amountDecimal;
  private String currency;

  public TransactionDTO() {
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

}

