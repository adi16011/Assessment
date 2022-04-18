package com.Bootcamp.project.repos;

import com.Bootcamp.project.Entities.CategoryMetadataField;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryMetadataFieldRepo extends JpaRepository<CategoryMetadataField,Long> {
    CategoryMetadataField findByName(String name);
    CategoryMetadataField findById(long id);
    List<CategoryMetadataField> findAll(Sort sort);
}