package com.Bootcamp.project.Auditing;

import com.Bootcamp.project.Entities.UserEntity;
import com.Bootcamp.project.repos.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class SpringAuditorAware implements AuditorAware<String> {


    @Autowired
    private AuthenticationFacade authenticationFacade;
    @Override
    public Optional<String> getCurrentAuditor() {

        Authentication authentication = authenticationFacade.getAuthentication();

        if(authentication == null){

            return Optional.ofNullable("Admin").filter(s -> !s.isEmpty());


        }






//        UserEntity user = (UserEntity) authentication.getPrincipal();

        String currentAuditor;

        currentAuditor = authentication.getName();










        return Optional.ofNullable(currentAuditor).filter(s -> !s.isEmpty());

    }
}
