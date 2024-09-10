package org.carolina.securingapi.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Here, you'd load the user from the database
        // For demonstration purposes, returning a dummy user
        return new org.springframework.security.core.userdetails.User(
                username, "password", new ArrayList<>() // Roles/Authorities should be set here
        );
    }
}


