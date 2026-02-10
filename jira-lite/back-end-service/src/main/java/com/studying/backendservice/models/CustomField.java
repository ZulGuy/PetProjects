package com.studying.backendservice.models;

import com.studying.backendservice.utils.FieldType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "custom_fields")
public class CustomField {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  int id;
  @Column(name = "name", nullable = false)
  String name;
  @Column(name = "value", nullable = false)
  String value;
  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false)
  private FieldType type;

  public CustomField(String name, String value, FieldType type) {
    this.name = name;
    this.value = value;
    this.type = type;
  }

  public CustomField(String name, FieldType type) {
    this.name = name;
    this.type = type;
  }

  public CustomField() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public FieldType getType() {
    return type;
  }

  public void setType(FieldType type) {
    this.type = type;
  }
}
