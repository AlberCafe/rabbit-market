package com.albercafe.rabbitmarket.exception;

import com.albercafe.rabbitmarket.dto.CustomResponse;
import com.albercafe.rabbitmarket.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class APIExceptionAdvice {

    private final RefreshTokenService refreshTokenService;

    @ExceptionHandler
    public ResponseEntity<CustomResponse> TokenNotFoundExceptionHandler(TokenNotFoundException e) {
        String token = e.getMessage();

        CustomResponse responseBody = new CustomResponse();

        responseBody.setData(null);
        responseBody.setError("Can't find token : " + token + " check out your token ! ");

        return ResponseEntity.status(400).body(responseBody);
    }

    @ExceptionHandler
    public ResponseEntity<CustomResponse> ExpiredRefreshTokenExceptionHandler(InvalidRefreshTokenException e) {
        String token = e.getMessage();
        return refreshTokenService.deleteRefreshToken(token);
        // 삭제만 하고 끝나는게 아니라 다시 재 생성하도록 유도해야함 (서버내에 코드를 써줘야 할 듯)
    }

    @ExceptionHandler
    public ResponseEntity<CustomResponse> ExpiredJWTExceptionHandler(ExpiredJWTException e) {
        CustomResponse responseBody = new CustomResponse();

        String jwt = e.getMessage();

        responseBody.setData(null);
        responseBody.setError("JWT : " + jwt + " has expired !, please regenerate JWT, login again !");

        return ResponseEntity.status(400).body(responseBody);
    }

    @ExceptionHandler
    public ResponseEntity<CustomResponse> MalformedJWTExceptionHandler(MalformedJWTException e) {
        CustomResponse responseBody = new CustomResponse();

        String jwt = e.getMessage();

        responseBody.setData(null);
        responseBody.setError("jwt : " + jwt + " seems to be malformed !, please regenerate JWT !");

        return ResponseEntity.status(400).body(responseBody);
    }

    @ExceptionHandler
    public ResponseEntity<CustomResponse> CustomExceptionHandler(RabbitMarketException e) {
        CustomResponse responseBody = new CustomResponse();

        String customMessage = e.getMessage();

        responseBody.setData(null);
        responseBody.setError(customMessage);

        return ResponseEntity.status(400).body(responseBody);
    }
}
