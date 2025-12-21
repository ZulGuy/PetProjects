package com.studying.fintrack.web.controllers;

import com.studying.fintrack.domain.entities.Category;
import com.studying.fintrack.domain.services.CategoriesService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {

  CategoriesService categoriesService;

  @Autowired
  public CategoriesController(CategoriesService categoriesService) {
    this.categoriesService = categoriesService;
  }

  @GetMapping
  public List<Category> getAllCategories() {
    return categoriesService.getAllCategories();
  }

  @PostMapping
  public Category createCategory(Category category) {
    return categoriesService.createCategory(category);
  }

  @GetMapping("/users/{id}")
  public List<Category> getCategoriesByUser(@PathVariable int id) {
    return categoriesService.getAllCategories();
  }

  @PutMapping("/{id}")
  public Category updateCategory(@PathVariable int id, Category category) {
    return categoriesService.updateCategory(id, category);
  }

  @DeleteMapping("/{id}")
  public void deleteCategory(@PathVariable int id) {
    categoriesService.deleteCategory(id);
  }

}
