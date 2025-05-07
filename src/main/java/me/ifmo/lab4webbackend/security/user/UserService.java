package me.ifmo.lab4webbackend.security.user;

import me.ifmo.lab4webbackend.entities.User;
import me.ifmo.lab4webbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
}
