package com.studying.dataapi.controllers;

import com.studying.dataapi.dto.ProcessingRequest;
import com.studying.dataapi.dto.ProcessingResponse;
import com.studying.dataapi.services.ProcessingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transform")
public class ProcessingController {

  private final ProcessingService processingService;
  private final String internalKey;


  @Autowired
  public ProcessingController(ProcessingService processingService,
      @Value("${internal.token}") String internalKey) {
    this.processingService = processingService;
    this.internalKey = internalKey;
  }

  @PostMapping
  public ResponseEntity<ProcessingResponse> process(@RequestBody ProcessingRequest dto,
      HttpServletRequest request) {
    ProcessingResponse response;
    if (request.getHeader("X-Internal-Token") != null && request.getHeader("X-Internal-Token")
        .equals(internalKey)) {
      response = new ProcessingResponse(processingService.process(dto.text()));
      return ResponseEntity.ok(response);
    }
    response = new ProcessingResponse("Access Denied");
    return ResponseEntity.status(403).body(response);
  }
}
