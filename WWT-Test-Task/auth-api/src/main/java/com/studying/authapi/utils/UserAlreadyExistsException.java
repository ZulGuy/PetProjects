package com.studying.authapi.utils;

public class UserAlreadyExistsException extends RuntimeException{

  public UserAlreadyExistsException(String message) {
    super(message);
  }

}
