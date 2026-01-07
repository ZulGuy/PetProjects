package com.studying.fintrack.domain.models;

public class ImportJobDTO {

  private String status;
  private String statsJson;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getStatsJson() {
    return statsJson;
  }

  public void setStatsJson(String statsJson) {
    this.statsJson = statsJson;
  }
}
