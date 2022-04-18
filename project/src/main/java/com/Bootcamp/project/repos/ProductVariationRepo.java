package com.Bootcamp.project.repos;

import com.Bootcamp.project.Entities.Product;
import com.Bootcamp.project.Entities.ProductVariation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductVariationRepo extends JpaRepository<ProductVariation,Long> {

    List<ProductVariation> findAllByProduct(Product product, Pageable pageable);

    List<ProductVariation> findAllByProduct(Product product);

}
