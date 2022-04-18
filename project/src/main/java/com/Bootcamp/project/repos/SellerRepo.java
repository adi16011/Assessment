package com.Bootcamp.project.repos;

import com.Bootcamp.project.Entities.Seller;
import com.Bootcamp.project.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SellerRepo extends JpaRepository<Seller,Long> {


    Seller findByUserId(UserEntity user);

    @Modifying
    @Query(value = "update seller s set s.company_contact=:companyContract, s.company_name=:companyName, s.gst=:gst where s.id=:id",nativeQuery = true)
    @Transactional
    void setSeller(@Param("id") Long id,@Param("companyContract") String companyContract, @Param("companyName") String companyName,@Param("gst") String gst);



}
