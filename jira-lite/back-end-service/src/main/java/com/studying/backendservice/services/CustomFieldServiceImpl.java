package com.studying.backendservice.services;

import com.studying.backendservice.dto.CustomFieldDTO;
import com.studying.backendservice.models.CustomField;
import com.studying.backendservice.repositories.CustomFieldRepository;
import com.studying.backendservice.utils.FieldType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomFieldServiceImpl implements CustomFieldService{

  private final CustomFieldRepository customFieldRepository;

  @Autowired
  public CustomFieldServiceImpl(CustomFieldRepository customFieldRepository) {
    this.customFieldRepository = customFieldRepository;
  }

  @Override
  public void addCustomField(CustomFieldDTO dto) {
    customFieldRepository.save(new CustomField(dto.getName(), dto.getFieldType()));
  }

  @Override
  public void deleteCustomField(int id) {
    customFieldRepository.deleteById(id);
  }

  @Override
  public void updateCustomField(int id, String name) {
    CustomField existing = customFieldRepository.findById(id).orElseThrow();
    existing.setName(name);
    customFieldRepository.save(existing);
  }

  @Override
  public CustomField getCustomFieldById(int id) {
    return customFieldRepository.findById(id).orElseThrow();
  }

  @Override
  public CustomField[] getAllCustomFields() {
    return customFieldRepository.findAll().toArray(new CustomField[0]);
  }

  @Override
  public CustomField getCustomFieldByName(String name) {
    return customFieldRepository.findByName(name)
        .orElseThrow(() -> new IllegalArgumentException("Custom field not found"));
  }
}
