package me.ifmo.lab4webbackend.security.user;

import me.ifmo.lab4webbackend.entities.User;
import me.ifmo.lab4webbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User addUser(User user) {
        return this.userRepository.save(user);
    }

    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
