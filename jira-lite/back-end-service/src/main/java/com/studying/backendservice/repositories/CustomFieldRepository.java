package com.studying.backendservice.repositories;

import com.studying.backendservice.models.CustomField;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomFieldRepository extends JpaRepository<CustomField, Integer> {
  Optional<CustomField> findByName(String name);

}
