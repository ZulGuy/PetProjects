package com.studying.fintrack.web.controllers;

import com.studying.fintrack.domain.services.ImportJobsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/import-jobs")
public class ImportJobsController {

  ImportJobsService importJobsService;

  @Autowired
  public ImportJobsController(ImportJobsService importJobsService) {
    this.importJobsService = importJobsService;
  }

}
