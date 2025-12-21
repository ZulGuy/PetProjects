package com.studying.fintrack.domain.models;

import com.studying.fintrack.domain.utils.Type;

public class CategoryDTO {

  private String name;
  private Type type;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }
}
