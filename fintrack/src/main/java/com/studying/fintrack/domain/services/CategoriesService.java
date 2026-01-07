package com.studying.fintrack.domain.services;

import com.studying.fintrack.domain.entities.Category;
import com.studying.fintrack.domain.models.CategoryDTO;
import com.studying.fintrack.domain.repositories.CategoriesRepository;
import jakarta.persistence.EntityNotFoundException;
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

  public List<Category> getCategoriesByUser(int id) {
    return categoriesRepository.findAllByUser_Id(id).orElseThrow(
        () -> new EntityNotFoundException("User not found in DB!")
    );
  }

  public Category createCategory(Category category) {
    return categoriesRepository.save(category);
  }

  public void deleteCategory(Integer id) {
    categoriesRepository.deleteById(id);
  }

  public Category updateCategory(int id, CategoryDTO category) {
    Category updatedCategory = categoriesRepository.findById(id).orElseThrow(
        () -> new EntityNotFoundException("Category not found in DB!")
    );
    toEntity(updatedCategory, category);
    return categoriesRepository.save(updatedCategory);
  }

  private Category toEntity(Category updatedCategory, CategoryDTO category) {
    updatedCategory.setName(category.getName());
    updatedCategory.setType(category.getType());
    return updatedCategory;
  }

}
