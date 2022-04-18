package com.Bootcamp.project.Bootstrap;

import com.Bootcamp.project.Entities.Role;
import com.Bootcamp.project.Entities.UserEntity;
import com.Bootcamp.project.Enumerators.RoleEnum;
import com.Bootcamp.project.repos.RoleRepo;
import com.Bootcamp.project.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Component
public class BStrap implements ApplicationRunner {



    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        if(Objects.isNull(roleRepo.findByAuthority(RoleEnum.ROLE_ADMIN)) && Objects.isNull(userRepository.findByEmail("aditya.pushkar@tothenew.com"))){
            Role role = new Role();
            role.setAuthority(RoleEnum.ROLE_ADMIN);
            Role savedRole = roleRepo.save(role);

            Role role2 = new Role();
            role2.setAuthority(RoleEnum.ROLE_SELLER);
            roleRepo.save(role2);


            Role role3 = new Role();
            role3.setAuthority(RoleEnum.ROLE_CUSTOMER);
            roleRepo.save(role3);



            Set<Role> roles = new HashSet<>();
            roles.add(savedRole);


            UserEntity user = new UserEntity();
            user.setEmail("aditya.pushkar@tothenew.com");
            user.setPassword(passwordEncoder.encode("adityapushkar"));
            user.setFirstName("Aditya");
            user.setLastName("Pushkar");
            user.setRoles(roles);
            user.setActive(true);

            userRepository.save(user);
        }

    }
}
