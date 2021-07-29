package com.albercafe.rabbitmarket.controller;

import com.albercafe.rabbitmarket.dto.CustomResponse;
import com.albercafe.rabbitmarket.entity.UserProfile;
import com.albercafe.rabbitmarket.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(httpMethod = "GET", value = "Gets specific user's profile information", notes = "Gets only user profile that match a given id")
    @ApiResponse(code = 200, message = "OK", response = CustomResponse.class)
    @GetMapping("/{id}/profile")
    public ResponseEntity<CustomResponse> getUserProfile(@PathVariable Long id) { return userService.getUserProfile(id); }
}
