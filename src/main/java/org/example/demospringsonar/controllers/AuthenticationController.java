package org.example.demospringsonar.controllers;

import org.example.demospringsonar.JwtUtil;
import org.example.demospringsonar.entities.Users;
import org.example.demospringsonar.interfaces.UsersRepository;
import org.example.demospringsonar.models.AuthenticationRequest;
import org.example.demospringsonar.models.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            System.out.println("Username: " + authenticationRequest.getUsername());
            System.out.println("Password: " + authenticationRequest.getPassword());

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody AuthenticationRequest authenticationRequest) {
        // Check if the user already exists
        if (usersRepository.findByUsername(authenticationRequest.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Username is already taken");
        }

        // Encode the password
        String encodedPassword = passwordEncoder.encode(authenticationRequest.getPassword());

        // Create and save the new user
        Users newUser = new Users(authenticationRequest.getUsername(), encodedPassword, authenticationRequest.getName(), authenticationRequest.getEmail());
        usersRepository.save(newUser);

        return ResponseEntity.ok("Users registered successfully");
    }
}
