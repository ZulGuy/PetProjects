package com.studying.backendservice.dto;

import com.studying.backendservice.utils.FieldType;

public class CustomFieldDTO {

  private int id;
  private String name;
  private FieldType fieldType;

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

  public FieldType getFieldType() {
    return fieldType;
  }

  public void setFieldType(FieldType fieldType) {
    this.fieldType = fieldType;
  }
}
