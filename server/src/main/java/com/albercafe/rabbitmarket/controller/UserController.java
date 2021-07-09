package com.albercafe.rabbitmarket.controller;

import com.albercafe.rabbitmarket.entity.UserProfile;
import com.albercafe.rabbitmarket.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/{id}/profile")
    public UserProfile getUserProfile(@PathVariable Long id) { return userService.getUserProfile(id); }
}
