package com.Bootcamp.project.repos;

import com.Bootcamp.project.Entities.ConfirmToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ConfirmTokenRepo extends JpaRepository<ConfirmToken,Long> {

    ConfirmToken findByConfirmationToken(String confirmationToken);

}
