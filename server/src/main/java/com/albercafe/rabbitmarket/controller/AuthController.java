package com.albercafe.rabbitmarket.controller;

import com.albercafe.rabbitmarket.dto.*;
import com.albercafe.rabbitmarket.service.AuthService;
import com.albercafe.rabbitmarket.service.RefreshTokenService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @ApiOperation(httpMethod = "POST", value = "Signup", notes = "After Signup, you must check out email !")
    @ApiResponse(code = 200, message = "OK", response = CustomResponse.class)
    @PostMapping(value = "/signup", produces = "application/json; charset=UTF-8;")
    public ResponseEntity<CustomResponse> signup(@Valid @RequestBody RegisterRequest registerRequest) {
        return authService.signup(registerRequest);
    }

    @ApiOperation(httpMethod = "GET", value = "Activate account", notes = "Activate account")
    @ApiResponse(code = 200, message = "OK", response = CustomResponse.class)
    @GetMapping(value = "/accountVerification/{token}", produces = "application/json; charset=UTF-8;")
    public RedirectView verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new RedirectView("/api/auth/accountVerification/" + token);
    }

    @ApiOperation(httpMethod = "POST", value = "login", notes = "When login, you must check out email, password !")
    @ApiResponse(code = 200, message = "OK", response = CustomResponse.class)
    @PostMapping("/login")
    public ResponseEntity<CustomResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @ApiOperation(httpMethod = "POST", value = "Create Refresh Token", notes = "Create refresh token, refresh token need to validate jwt")
    @ApiResponse(code = 200, message = "OK", response = CustomResponse.class)
    @PostMapping("/refresh/token")
    public ResponseEntity<CustomResponse> refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @ApiOperation(httpMethod = "POST", value = "logout", notes = "When logout, refreshToken will be removed !")
    @ApiResponse(code = 200, message = "OK", response = CustomResponse.class)
    @PostMapping("/logout")
    public ResponseEntity<CustomResponse> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
    }
}
