package com.studying.fintrack.web.controllers;

import com.studying.fintrack.domain.entities.ImportJob;
import com.studying.fintrack.domain.services.ImportJobsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

  @GetMapping
  public List<ImportJob> getAllImportJobs() {
    return importJobsService.getAllImportJobs();
  }

  @PostMapping
  public ImportJob createImportJob(ImportJob importJob) {
    return importJobsService.createImportJob(importJob);
  }

}
