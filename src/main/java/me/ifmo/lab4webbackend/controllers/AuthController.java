package me.ifmo.lab4webbackend.controllers;

import me.ifmo.lab4webbackend.DTO.JwtDTO;
import me.ifmo.lab4webbackend.DTO.UserDTO;
import me.ifmo.lab4webbackend.entities.User;
import me.ifmo.lab4webbackend.security.jwt.JwtManager;
import me.ifmo.lab4webbackend.security.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private JwtManager jwtManager;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody UserDTO userDTO) {
        try {
            User user = (User) this.userService.loadUserByUsername(userDTO.getUsername());
            if (user == null) {
                throw new IllegalArgumentException();
            } else if (!this.passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
                throw new IllegalAccessException();
            } else {
                String jwt = this.jwtManager.generateToken(userDTO.getUsername());
                return ResponseEntity.ok(new JwtDTO(userDTO.getUsername(), jwt));
            }
        } catch (IllegalArgumentException | IllegalAccessException exception) {
            return ResponseEntity.badRequest().body("Incorrect username or password!");
        }
    }
}
