package com.Bootcamp.project.repos;

import com.Bootcamp.project.Entities.Customer;
import com.Bootcamp.project.Entities.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer,Long> {

    Customer findByUserId(UserEntity user);

//    List<Customer> findAll(Pageable pageable);



    @Modifying
    @Query(value = "update customer c set c.contact=:contact where c.id=:id",nativeQuery = true)
    @Transactional
    void setCustomer(@Param("id") Long id, @Param("contact") String contact);
}
