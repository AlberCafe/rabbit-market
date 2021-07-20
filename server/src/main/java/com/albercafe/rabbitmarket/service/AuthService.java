package com.albercafe.rabbitmarket.service;

import com.albercafe.rabbitmarket.dto.*;
import com.albercafe.rabbitmarket.entity.User;
import com.albercafe.rabbitmarket.entity.UserProfile;
import com.albercafe.rabbitmarket.entity.VerificationToken;
import com.albercafe.rabbitmarket.exception.RabbitMarketException;
import com.albercafe.rabbitmarket.repository.UserProfileRepository;
import com.albercafe.rabbitmarket.repository.UserRepository;
import com.albercafe.rabbitmarket.repository.VerificationTokenRepository;
import com.albercafe.rabbitmarket.security.JWTProvider;
import com.albercafe.rabbitmarket.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public ResponseEntity<Map<Object, Object>> signup(RegisterRequest registerRequest) {
        Optional<User> tempUser = userRepository.findByEmail(registerRequest.getEmail());

        if (tempUser.isPresent()) {
            Map<Object, Object> responseBody = new HashMap<>();
            responseBody.put("data", null);
            responseBody.put("error", "already exists user !");
            return ResponseEntity.badRequest().body(responseBody);
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodePassword(registerRequest.getPassword()));
        user.setEnabled(false);

        UserProfile userProfile = new UserProfile();
        userProfile.setAddress(registerRequest.getAddress());
        userProfile.setPhoneNumber(registerRequest.getPhoneNumber());
        userProfile.setUsername(registerRequest.getUsername());

        user.setUserProfile(userProfile);
        userProfile.setUser(user);

        userRepository.save(user);
        userProfileRepository.save(userProfile);

        String token = generateVerificationToken(user);
        String link = Constants.ACTIVATION_EMAIL + "/" + token;
        String message = mailContentBuilder.build(link);

        mailService.sendMail(new NotificationEmail("계정 활성화를 실행해주세요.", user.getEmail(), message));

        Map<Object, Object> responseBody = new HashMap<>();
        responseBody.put("data", "checkout your email, you must activate your account !");
        responseBody.put("error", null);

        return ResponseEntity.ok().body(responseBody);
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    private String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationTokenOptional = verificationTokenRepository.findByToken(token);
        verificationTokenOptional.orElseThrow(() -> new RabbitMarketException("Invalid Token"));
        fetchUserAndEnable(verificationTokenOptional.get());
    }

    @Transactional
    public void fetchUserAndEnable(VerificationToken verificationToken) {
        String email = verificationToken.getUser().getEmail();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RabbitMarketException("User not found with " + email));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public ResponseEntity<Map<Object, Object>> login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Optional<User> user = userRepository.findByEmail(email);

        Map<Object, Object> responseBody = new HashMap<>();

        if (!user.isPresent()) {
            responseBody.put("data", null);
            responseBody.put("error", "wrong email");
            return ResponseEntity.badRequest().body(responseBody);
        }

        if (!user.get().getEnabled()) {
            responseBody.put("data", null);
            responseBody.put("error", "this user need to activate, checkout your email !");
            return ResponseEntity.badRequest().body(responseBody);
        }

        if (!bCryptPasswordEncoder.matches(password, user.get().getPassword())) {
            responseBody.put("data", null);
            responseBody.put("error", "wrong password");
            return ResponseEntity.badRequest().body(responseBody);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String authenticationToken = jwtProvider.generateToken(authentication);

        AuthenticationResponse authenticationResponse =  AuthenticationResponse.builder()
                .authenticationToken(authenticationToken)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJWTExpirationInMillis()))
                .email(loginRequest.getEmail())
                .build();

        responseBody.put("data", authenticationResponse);
        responseBody.put("error", null);

        return ResponseEntity.ok().body(responseBody);
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithEmail(refreshTokenRequest.getEmail());

        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJWTExpirationInMillis()))
                .email(refreshTokenRequest.getEmail())
                .build();
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal
                = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userRepository.findByEmail(principal.getUsername()).orElseThrow(() -> new RabbitMarketException("user not exist : " + principal.getUsername()));
    }
}
