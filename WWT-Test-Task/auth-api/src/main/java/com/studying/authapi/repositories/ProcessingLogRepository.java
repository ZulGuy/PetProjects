package com.studying.authapi.repositories;

import com.studying.authapi.models.ProcessingLog;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessingLogRepository extends JpaRepository<ProcessingLog, UUID> {

}
