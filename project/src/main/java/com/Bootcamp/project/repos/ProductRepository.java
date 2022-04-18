package com.Bootcamp.project.repos;

import com.Bootcamp.project.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {


    List<Product> findAllByBrand(String brand);


    @Query(value = "select * from product p where p.brand=:brand and p.category_id=:category and p.seller_id=:seller",nativeQuery = true)
    List<Product> findSameBrandCategorySeller(@Param("brand") String brand,@Param("category") Long category,@Param("seller") Long seller);


    List<Product> findAllByName(String name);



}
