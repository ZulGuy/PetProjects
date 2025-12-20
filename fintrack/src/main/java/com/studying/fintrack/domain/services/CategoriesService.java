package com.studying.fintrack.domain.services;

import com.studying.fintrack.domain.entities.Category;
import com.studying.fintrack.domain.repositories.CategoriesRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriesService {

  CategoriesRepository categoriesRepository;

  @Autowired
  public CategoriesService(CategoriesRepository categoriesRepository) {
    this.categoriesRepository = categoriesRepository;
  }

  public List<Category> getAllCategories() {
    return categoriesRepository.findAll();
  }

  public Category getCategoryById(Integer id) {
    Category category = categoriesRepository.findById(id).orElse(null);
    if (category == null) {
      throw new NullPointerException("Category not found in DB!");
    }
    return category;
  }

  public Category createCategory(Category category) {
    return categoriesRepository.save(category);
  }

  public void deleteCategory(Integer id) {
    categoriesRepository.deleteById(id);
  }

  public Category updateCategory(Category category) {
    return categoriesRepository.save(category);
  }

}
