package com.studying.fintrack.domain.entities;

import com.studying.fintrack.domain.utils.JsonbConvertor;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.util.Map;

@Entity
@Table(name = "import_jobs")
public class ImportJob {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private int id;
  @Column(name = "status", nullable = false)
  private String status;
  @Column(name = "created_at", nullable = false)
  private Timestamp createdAt;
  @Column(name = "finished_at")
  private Timestamp finishedAt;
  @Convert(converter = JsonbConvertor.class)
  @Column(name = "stats_json", columnDefinition = "jsonb")
  private Map<String, Object> statsJson;

  public ImportJob() {
  }

  public ImportJob(String status, Timestamp createdAt, Timestamp finishedAt,
      Map<String, Object> statsJson) {
    this.status = status;
    this.createdAt = createdAt;
    this.finishedAt = finishedAt;
    this.statsJson = statsJson;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }

  public Timestamp getFinishedAt() {
    return finishedAt;
  }

  public void setFinishedAt(Timestamp finishedAt) {
    this.finishedAt = finishedAt;
  }

  public Map<String, Object> getStatsJson() {
    return statsJson;
  }

  public void setStatsJson(Map<String, Object> statsJson) {
    this.statsJson = statsJson;
  }
}
