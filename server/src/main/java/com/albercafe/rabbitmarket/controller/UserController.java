package com.albercafe.rabbitmarket.controller;

import com.albercafe.rabbitmarket.annotation.CurrentUser;
import com.albercafe.rabbitmarket.dto.CustomResponse;
import com.albercafe.rabbitmarket.dto.UserProfileRequest;
import com.albercafe.rabbitmarket.security.UserPrincipal;
import com.albercafe.rabbitmarket.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(httpMethod = "GET", value = "Gets specific user's profile information", notes = "Gets only user profile that match a given id")
    @ApiResponse(code = 200, message = "OK", response = CustomResponse.class)
    @GetMapping("/{id}/profile")
    public ResponseEntity<CustomResponse> getUserProfile(@PathVariable Long id) { return userService.getUserProfile(id); }

    @ApiOperation(httpMethod = "PATCH", value = "Update Specific User Profile", notes = "Modify only profile that match a given id")
    @ApiResponse(code = 200, message = "OK", response = CustomResponse.class)
    @PatchMapping("/{id}/profile")
    public ResponseEntity<CustomResponse> updateUserProfile(@PathVariable Long id, @Valid @RequestBody UserProfileRequest userProfileRequest) {
        return userService.updateUserProfile(id, userProfileRequest);
    }

    @ApiOperation(httpMethod = "GET", value = "Get Current Login user information", notes = "This method return current user's detail information")
    @ApiResponse(code = 200, message = "OK", response = CustomResponse.class)
    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CustomResponse> getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userService.getCurrentUser(userPrincipal);
    }
}
