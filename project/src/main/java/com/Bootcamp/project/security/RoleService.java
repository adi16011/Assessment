package com.Bootcamp.project.security;


import com.Bootcamp.project.Entities.Role;
import com.Bootcamp.project.Entities.UserEntity;
import com.Bootcamp.project.repos.RoleRepo;
import com.Bootcamp.project.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleService{

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserRepository userRepository;



    public Optional<Role> findById(int id){
        return roleRepo.findById((long) id);

    }

    public void assignUserRole(Long userId,Long roleId){
        UserEntity user = userRepository.findById(userId).orElse(null);
        Role role = roleRepo.findById(roleId).orElse(null);

        Set<Role> roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);




    }


}
