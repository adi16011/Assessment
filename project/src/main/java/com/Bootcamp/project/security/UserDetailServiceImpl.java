package com.Bootcamp.project.security;

import com.Bootcamp.project.Entities.Role;
import com.Bootcamp.project.Entities.UserEntity;
import com.Bootcamp.project.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username);

        if (user==null){
            throw new UsernameNotFoundException("User not Found");

        }
         return user;

//        return null;


    }

    private Collection<GrantedAuthority> getAuthorities(UserEntity user){
        Set<Role> roles = user.getRoles();
        Collection<GrantedAuthority> authorities = new ArrayList<>(roles.size());

        for(Role role : roles ){
            authorities.add(new SimpleGrantedAuthority(String.valueOf(role.getAuthority()).toUpperCase()));

        }


        return authorities;


    }

}
