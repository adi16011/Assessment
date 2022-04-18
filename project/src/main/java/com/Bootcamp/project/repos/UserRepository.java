package com.Bootcamp.project.repos;

import com.Bootcamp.project.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);

    UserEntity findByFirstName(String firstName);

    Optional<UserEntity> findById(Long id);


//    List<UserEntity> findAllById(Long id, Pageable pageable);



    @Modifying
    @Query(value = "update user u set u.is_active=TRUE where u.id=?1",nativeQuery = true)
    @Transactional
    void setActive(@Param("id") Long id);



    @Modifying
    @Query(value = "update user u set u.is_active=FALSE where u.id=?1",nativeQuery = true)
    @Transactional
    void setUnActive(@Param("id") Long id);


    @Query(value = "SELECT u FROM user u JOIN u.address a WHERE u.id = a.user_id",nativeQuery = true)
    List<UserEntity> findAddressAll();

    @Modifying
    @Query(value = "update user u set u.password=:password where u.id=:id",nativeQuery = true)
    @Transactional
    void setPassword(@Param("password") String password, @Param("id") Long id);

    @Modifying
    @Query(value = "update user u set u.first_name=:firstName, u.last_name=:lastName, u.email=:email, u.password=:password where u.id=:id ",nativeQuery = true)
    @Transactional
    void setUser(@Param("firstName") String firstName,@Param("lastName") String lastName,@Param("email") String email, @Param("password") String password,@Param("id") Long id);

//    @Modifying
//    @Query
//    @Transactional
//    void setFirstName(@Param("firstName") String firstName,@Param("id") Long id);













}
