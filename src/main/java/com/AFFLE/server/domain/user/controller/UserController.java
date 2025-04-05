package com.AFFLE.server.domain.user.controller;

import com.AFFLE.server.domain.user.entity.User;
import com.AFFLE.server.domain.user.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public User create(@RequestParam String name) {
        return userRepository.save(new User(name));
    }

    @GetMapping
    public List<User> getAll() {
        return userRepository.findAll();
    }
}