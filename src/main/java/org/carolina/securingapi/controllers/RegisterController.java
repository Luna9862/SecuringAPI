package org.carolina.securingapi.controllers;

import org.carolina.securingapi.models.User;
import org.carolina.securingapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        // Check if the username already exists
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Username is already taken");
        }

        // Encrypt the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");  // Default role is USER

        // Save the user in the SQLite database
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }
}



