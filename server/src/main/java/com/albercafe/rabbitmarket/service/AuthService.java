package com.albercafe.rabbitmarket.service;

import com.albercafe.rabbitmarket.dto.*;
import com.albercafe.rabbitmarket.entity.RefreshToken;
import com.albercafe.rabbitmarket.entity.User;
import com.albercafe.rabbitmarket.entity.UserProfile;
import com.albercafe.rabbitmarket.entity.VerificationToken;
import com.albercafe.rabbitmarket.exception.RabbitMarketException;
import com.albercafe.rabbitmarket.exception.TokenNotFoundException;
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
import java.time.OffsetDateTime;
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
    public ResponseEntity<CustomResponse> signup(RegisterRequest registerRequest) {
        CustomResponse responseBody = new CustomResponse();

        Optional<User> tempUser = userRepository.findByEmail(registerRequest.getEmail());

        if (tempUser.isPresent()) {
            responseBody.setData(null);
            responseBody.setError("Already exist user !");
            return ResponseEntity.badRequest().body(responseBody);
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodePassword(registerRequest.getPassword()));
        user.setEnabled(false);

        UserProfile userProfile = new UserProfile();
        userProfile.setAddress(registerRequest.getAddress());
        userProfile.setPhoneNumber(registerRequest.getPhoneNumber());
        if (registerRequest.getUsername() == null) {
            String email = user.getEmail();
            int endIdx = email.indexOf("@");
            String defaultUsername = email.substring(0, endIdx);
            userProfile.setUsername(defaultUsername);
        } else {
            userProfile.setUsername(registerRequest.getUsername());
        }

        user.setUserProfile(userProfile);
        userProfile.setUser(user);

        String token = generateVerificationToken(user);
        String link = Constants.ACTIVATION_EMAIL + "/" + token;
        String message = mailContentBuilder.build(link);

        try {
            mailService.sendMail(new NotificationEmail("계정 활성화를 실행해주세요.", user.getEmail(), message));
        } catch (Exception e) {
            responseBody.setData(null);
            responseBody.setError(e);
            return ResponseEntity.status(400).body(responseBody);
        }

        responseBody.setData("checkout your email, you must activate your account !");
        responseBody.setError(null);

        userRepository.save(user);
        userProfileRepository.save(userProfile);

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

    public ResponseEntity<CustomResponse> verifyAccount(String token) {
        CustomResponse responseBody = new CustomResponse();

        VerificationToken verificationToken = verificationTokenRepository
                .findByToken(token).orElseThrow(() -> new TokenNotFoundException("Invalid Token !"));

        fetchUserAndEnable(verificationToken);

        responseBody.setData("activated your account !");
        responseBody.setError(null);

        return ResponseEntity.status(200).body(responseBody);
    }

    @Transactional
    public void fetchUserAndEnable(VerificationToken verificationToken) {
        String email = verificationToken.getUser().getEmail();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RabbitMarketException("User not found with " + email));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public ResponseEntity<CustomResponse> login(LoginRequest loginRequest) {
        CustomResponse responseBody = new CustomResponse();

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Optional<User> tempUser = userRepository.findByEmail(email);

        if (tempUser.isEmpty()) {
            responseBody.setData(null);
            responseBody.setError("Wrong Email !");
            return ResponseEntity.status(400).body(responseBody);
        }

        User user = tempUser.get();

        if (!user.getEnabled()) {
            responseBody.setData(null);
            responseBody.setError("This user need to activate, checkout your email !");
            return ResponseEntity.status(400).body(responseBody);
        }

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            responseBody.setData(null);
            responseBody.setError("Wrong Password !");
            return ResponseEntity.status(400).body(responseBody);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String authenticationToken = jwtProvider.generateToken(authentication);

        RefreshToken oldRefreshToken = user.getRefreshToken();
        RefreshToken newRefreshToken = refreshTokenService.generateRefreshToken();

        if (oldRefreshToken == null) {
            user.setRefreshToken(newRefreshToken);

            userRepository.save(user);

            AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                    .authenticationToken(authenticationToken)
                    .refreshToken(newRefreshToken.getToken())
                    .expiresAt(Instant.from(OffsetDateTime.now().plusMinutes(jwtProvider.getJWTExpirationInMillis())))
                    .email(loginRequest.getEmail())
                    .build();

            responseBody.setData(authenticationResponse);

        } else {
            refreshTokenService.validateRefreshToken(oldRefreshToken.getToken());

            AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                    .authenticationToken(authenticationToken)
                    .refreshToken(oldRefreshToken.getToken())
                    .expiresAt(Instant.from(OffsetDateTime.now().plusMinutes(jwtProvider.getJWTExpirationInMillis())))
                    .email(loginRequest.getEmail())
                    .build();

            responseBody.setData(authenticationResponse);

        }
        responseBody.setError(null);
        return ResponseEntity.status(200).body(responseBody);
    }

    public ResponseEntity<CustomResponse> refreshToken(RefreshTokenRequest refreshTokenRequest) {
        CustomResponse responseBody = new CustomResponse();

        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithEmail(refreshTokenRequest.getEmail());

        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.from(OffsetDateTime.now().plusMinutes(jwtProvider.getJWTExpirationInMillis())))
                .email(refreshTokenRequest.getEmail())
                .build();

        responseBody.setData(authenticationResponse);
        responseBody.setError(null);

        return ResponseEntity.status(200).body(responseBody);
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal
                = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userRepository.findByEmail(principal.getUsername()).orElseThrow(() -> new RabbitMarketException("user not exist : " + principal.getUsername()));
    }
}
