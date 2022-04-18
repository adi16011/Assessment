package com.Bootcamp.project.repos;

import com.Bootcamp.project.Entities.Address;
import com.Bootcamp.project.Enumerators.AddressLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AddressRepo extends JpaRepository<Address,Long> {


    @Query(value = "SELECT * FROM address a WHERE a.user_id=:userid",nativeQuery = true)
    Address findByUserId(@Param("userid") Long id);

    Optional<Address> findById(Long id);


    @Modifying
    @Query(value = "update address a set a.city=:city,a.country=:country,a.state=:state,a.address_line=:addressLine,a.zip_code=:zip,a.label=:#{#addressLabel?.name()} where a.id=:id",nativeQuery = true)
    @Transactional
    void updateAddress(@Param("id") Long id, @Param("city") String city, @Param("state") String state, @Param("country") String country, @Param("addressLine") String addressLine, @Param("zip") Long zip, @Param("addressLabel") AddressLabel addressLabel);

    @Modifying
    @Query(value = "delete from address a where a.id=:id",nativeQuery = true)
    @Transactional
    void deleteAddress(@Param("id") Long id);




}
