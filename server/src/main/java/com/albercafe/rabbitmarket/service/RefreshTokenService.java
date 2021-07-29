package com.albercafe.rabbitmarket.service;

import com.albercafe.rabbitmarket.dto.CustomResponse;
import com.albercafe.rabbitmarket.entity.RefreshToken;
import com.albercafe.rabbitmarket.exception.RabbitMarketException;
import com.albercafe.rabbitmarket.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(OffsetDateTime.now());

        return refreshTokenRepository.save(refreshToken);
    }

    void validateRefreshToken(String token) {
        // TODO : 검증 이후 만료 시간 지났을때 삭제하는 로직이 따로 있어야 함
        refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RabbitMarketException("RefreshToken 검증 중에 오류 발생 (잘못된 토큰)"));
    }

    public ResponseEntity<CustomResponse> deleteRefreshToken(String token) {
        CustomResponse responseBody = new CustomResponse();

        refreshTokenRepository.deleteByToken(token);

        responseBody.setData(token + " is removed !");
        responseBody.setError(null);

        return ResponseEntity.status(200).body(responseBody);
    }
}
