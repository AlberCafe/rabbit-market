package com.albercafe.rabbitmarket.controller;

import com.albercafe.rabbitmarket.dto.AuthenticationResponse;
import com.albercafe.rabbitmarket.dto.LoginRequest;
import com.albercafe.rabbitmarket.dto.RefreshTokenRequest;
import com.albercafe.rabbitmarket.dto.RegisterRequest;
import com.albercafe.rabbitmarket.service.AuthService;
import com.albercafe.rabbitmarket.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping(value = "/signup", produces = "application/json; charset=UTF-8;")
    public ResponseEntity<Map<Object, Object>> signup(@Valid @RequestBody RegisterRequest registerRequest) {
        return authService.signup(registerRequest);
    }

    @GetMapping(value = "/accountVerification/{token}", produces = "application/json; charset=UTF-8;")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>("계정 활성화가 성공적으로 되었습니다.", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<Object, Object>> login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh/token")
    public ResponseEntity<AuthenticationResponse> refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return new ResponseEntity<>(authService.refreshToken(refreshTokenRequest), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body("Refresh Token Deleted !");
    }
}
