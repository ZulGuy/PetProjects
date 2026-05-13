package com.studying.authapi.controllers;

import com.studying.authapi.dto.ProcessingResponse;
import com.studying.authapi.services.ProcessingLogService;
import com.studying.authapi.dto.ProcessingDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("/api/process")
public class ProcessingController {

  private final ProcessingLogService processingLogService;
  private final String internalKey;
  private RestClient restClient;

  public ProcessingController(ProcessingLogService processingLogService, @Value("${internal.token}") String internalKey) {
    this.processingLogService = processingLogService;
    this.internalKey = internalKey;
    this.restClient = RestClient.builder()
        .baseUrl("http://data-api:8081")
        .defaultHeader("X-Internal-Token", internalKey)
        .build();
  }

  @PostMapping
  public ProcessingResponse process(@RequestBody ProcessingDTO processingDTO) {
    ProcessingResponse response = restClient
        .post()
        .uri("/api/transform")
        .body(processingDTO)
        .retrieve()
        .body(ProcessingResponse.class);
    processingLogService.save(processingDTO.text(), response.result());
    return response;
  }

}
