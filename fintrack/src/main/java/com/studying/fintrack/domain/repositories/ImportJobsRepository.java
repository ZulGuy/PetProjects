package com.studying.fintrack.domain.repositories;

import com.studying.fintrack.domain.entities.ImportJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportJobsRepository extends JpaRepository<ImportJob, Integer> {

}
