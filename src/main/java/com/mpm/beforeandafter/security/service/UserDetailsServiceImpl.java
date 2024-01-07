package com.mpm.beforeandafter.security.service;

import com.mpm.beforeandafter.role.model.Role;
import com.mpm.beforeandafter.user.model.User;
import com.mpm.beforeandafter.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Email = " + email);
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User Not Found with username: " + email));
//        System.out.println("User = " + user);
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        for (Role role : user.getRoles()) {
            roles.add(new SimpleGrantedAuthority(role.getName().name()));
            System.out.println(role);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), roles);
    }
}
