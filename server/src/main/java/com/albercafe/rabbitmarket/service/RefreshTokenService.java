package com.albercafe.rabbitmarket.service;

import com.albercafe.rabbitmarket.dto.CustomResponse;
import com.albercafe.rabbitmarket.entity.RefreshToken;
import com.albercafe.rabbitmarket.entity.User;
import com.albercafe.rabbitmarket.exception.InvalidRefreshTokenException;
import com.albercafe.rabbitmarket.exception.TokenNotFoundException;
import com.albercafe.rabbitmarket.repository.RefreshTokenRepository;
import com.albercafe.rabbitmarket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(OffsetDateTime.now());

        return refreshTokenRepository.save(refreshToken);
    }

    void validateRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenNotFoundException(token));

        OffsetDateTime tokenCreatedDate = refreshToken.getCreatedDate();
        OffsetDateTime now = OffsetDateTime.now();

        Duration duration = Duration.between(tokenCreatedDate, now);

        if (duration.toDays() > 1) {
            refreshTokenRepository.deleteByToken(token);
            throw new InvalidRefreshTokenException(token);
        }
    }

    public ResponseEntity<CustomResponse> deleteRefreshToken(String token) {
        CustomResponse responseBody = new CustomResponse();

        RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(() -> new TokenNotFoundException(token));
        User user = refreshToken.getUser();

        user.setRefreshToken(null);

        userRepository.save(user);

        refreshTokenRepository.deleteByToken(token);

        responseBody.setData("refresh token : " + token + " is removed !, you need to login again ! ");
        responseBody.setError(null);

        return ResponseEntity.status(200).body(responseBody);
    }
}
