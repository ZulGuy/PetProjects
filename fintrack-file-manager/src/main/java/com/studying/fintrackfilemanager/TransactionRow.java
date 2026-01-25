package com.studying.fintrackfilemanager;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelCellName;
import java.time.LocalDate;

public class TransactionRow {

  @ExcelCell(1)
  private String bookedAt;

  @ExcelCell(9)
  private double amountDecimal;

  @ExcelCell(8)
  private String currency;

  public String getBookedAt() {
    return bookedAt;
  }

  public void setBookedAt(String bookedAt) {
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
