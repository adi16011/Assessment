package com.Bootcamp.project.repos;

import com.Bootcamp.project.Entities.Role;
import com.Bootcamp.project.Enumerators.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role,Long> {


//    @Query("SELECT r FROM Role r WHERE r.rid=:id")
    public Optional<Role> findById(Long id);

    public Role findByAuthority(RoleEnum roleEnum);



}
