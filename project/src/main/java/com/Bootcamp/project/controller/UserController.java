package com.Bootcamp.project.controller;



import com.Bootcamp.project.Entities.UserEntity;
import com.Bootcamp.project.repos.IAuthenticationFacade;
import com.Bootcamp.project.repos.UserRepository;
import com.Bootcamp.project.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController("/user")
public class UserController {

    @Autowired
    private IAuthenticationFacade authenticationFacade;


    @Autowired
    private SecurityService securityService;

    @Autowired
    private PasswordEncoder encoder;


    @Autowired
    private UserRepository userRepo;



    @GetMapping("/user/{user}")
    public UserEntity getUser(@PathVariable String user){
        UserEntity userFound = userRepo.findByFirstName(user);
        return userFound;


    }


    @RequestMapping(value = "/current",method = RequestMethod.GET)
    @ResponseBody
    public String getCurrentUser(){
        Authentication authentication = authenticationFacade.getAuthentication();

        return authentication.getPrincipal().toString();






    }




}
