package org.carolina.securingapi.controllers;

import org.carolina.securingapi.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        // Perform authentication
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        // Load user details
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Generate token using username
        String token = jwtTokenUtil.generateToken(userDetails.getUsername());

        return token;
    }
}
