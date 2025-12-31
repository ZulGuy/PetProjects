package com.studying.fintrack.web.controllers;

import com.studying.fintrack.domain.entities.ImportJob;
import com.studying.fintrack.domain.services.ImportJobsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  @PatchMapping("/{id}")
  public ImportJob updateImportJob(@PathVariable int id, ImportJob importJob) {
    return importJobsService.updateImportJob(id, importJob);
  }

  @DeleteMapping("/{id}")
  public void deleteImportJob(@PathVariable int id) {
    importJobsService.deleteImportJobById(id);
  }

}
