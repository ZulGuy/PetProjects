package com.studying.backendservice.services;

import com.studying.backendservice.dto.CustomFieldDTO;
import com.studying.backendservice.models.CustomField;

public interface CustomFieldService {
  void addCustomField(CustomFieldDTO dto);
  void deleteCustomField(int id);
  void updateCustomField(int id, String name);
  CustomField getCustomFieldById(int id);
  CustomField[] getAllCustomFields();
  CustomField getCustomFieldByName(String name);
}
