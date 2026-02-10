package com.studying.backendservice.controllers;

import com.studying.backendservice.dto.CustomFieldDTO;
import com.studying.backendservice.models.CustomField;
import com.studying.backendservice.services.CustomFieldService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/custom-fields")
public class CustomFieldController {

  CustomFieldService customFieldService;

  public CustomFieldController(CustomFieldService customFieldService) {
    this.customFieldService = customFieldService;
  }

  @GetMapping
  public CustomField[] getAllCustomFields() {
    return customFieldService.getAllCustomFields();
  }

   @GetMapping("/{id}")
   public CustomField getById(@PathVariable int id) {
     return customFieldService.getCustomFieldById(id);
   }

   @GetMapping("/{name}")
   public CustomField getByName(@PathVariable String name) {
     return customFieldService.getCustomFieldByName(name);
   }

   @PostMapping
  public void addCustomField(@RequestBody CustomFieldDTO customField) {
     customFieldService.addCustomField(customField);
   }

   @DeleteMapping("/{id}")
  public void deleteCustomField(@PathVariable int id) {
     customFieldService.deleteCustomField(id);
   }

   @PostMapping("/{id}/update-name/{newName}")
   public void updateCustomFieldName(@PathVariable int id, @PathVariable String newName) {
     customFieldService.updateCustomField(id, newName);
   }

}
