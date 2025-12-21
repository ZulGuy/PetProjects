package com.studying.fintrack.domain.repositories;

import com.studying.fintrack.domain.entities.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends JpaRepository<Category, Integer> {

  Optional<List<Category>> findAllByUser_Id(int userId);

}
