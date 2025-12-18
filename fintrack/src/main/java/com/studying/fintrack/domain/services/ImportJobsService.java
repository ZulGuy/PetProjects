package com.studying.fintrack.domain.services;

import com.studying.fintrack.domain.repositories.ImportJobsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImportJobsService {

  ImportJobsRepository importJobsRepository;

  @Autowired
  public ImportJobsService(ImportJobsRepository importJobsRepository) {
    this.importJobsRepository = importJobsRepository;
  }

}
