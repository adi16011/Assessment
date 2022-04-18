package com.Bootcamp.project.repos;

import com.Bootcamp.project.Entities.Category;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
    Category findByName(String name);
    Optional<Category> findById(Long id);

    List<Category> findAllByParentCategoryId(Long id);

    List<Category> findAll(Sort sort);
}