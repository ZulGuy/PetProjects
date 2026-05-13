package com.studying.dataapi.services;

import org.springframework.stereotype.Service;

@Service
public class ProcessingService {

  public String process(String text) {
    return text.toUpperCase();
  }

}
