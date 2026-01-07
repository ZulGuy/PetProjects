package com.studying.fintrack.domain.services;

import com.studying.fintrack.domain.entities.ImportJob;
import com.studying.fintrack.domain.models.ImportJobDTO;
import com.studying.fintrack.domain.repositories.ImportJobsRepository;
import com.studying.fintrack.domain.utils.JsonbConvertor;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImportJobsService {

  ImportJobsRepository importJobsRepository;

  @Autowired
  public ImportJobsService(ImportJobsRepository importJobsRepository) {
    this.importJobsRepository = importJobsRepository;
  }

  public List<ImportJob> getAllImportJobs() {
    return importJobsRepository.findAll();
  }

  public ImportJob getImportJobById(int id) {
    return importJobsRepository.findById(id).orElseThrow(
        () -> new EntityNotFoundException("Import Job Not Found!")
    );
  }

  public ImportJob createImportJob(ImportJob importJob) {
    return importJobsRepository.save(importJob);
  }

  public ImportJob updateImportJob(int id, ImportJob importJob) {
    ImportJob updatedImportJob = importJobsRepository.findById(id).orElse(null);
    if (updatedImportJob == null)
      return importJobsRepository.save(importJob);
    updatedImportJob.setStatus(importJob.getStatus());
    updatedImportJob.setUser(importJob.getUser());
    updatedImportJob.setStatsJson(importJob.getStatsJson());
    updatedImportJob.setCreatedAt(importJob.getCreatedAt());
    updatedImportJob.setFinishedAt(importJob.getFinishedAt());
    return importJobsRepository.save(updatedImportJob);
  }

  public void deleteImportJobById(int id) {
    importJobsRepository.deleteById(id);
  }

  private ImportJob toEntity(ImportJob updatedImportJob, ImportJobDTO importJob) {
    JsonbConvertor jsonbConvertor = new JsonbConvertor();
    updatedImportJob.setStatus(importJob.getStatus());
    updatedImportJob.setStatsJson(jsonbConvertor.convertToEntityAttribute(importJob.getStatsJson()));
    return updatedImportJob;
  }

}
