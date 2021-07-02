package com.albercafe.rabbitmarket.security;

import com.albercafe.rabbitmarket.exception.RabbitMarketException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.Date;

@Service
public class JWTProvider {

    private KeyStore keyStore;

    @Value("${jks.password}")
    private String keyStorePassword;

    private JwtParser parser;

    @Value("${jwt.expiration.time}")
    private Long JWTExpirationInMillis;

    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/rabbit-market.jks");
            keyStore.load(resourceAsStream, keyStorePassword.toCharArray());
            parser = Jwts.parserBuilder().setSigningKey(getPublicKey()).build();
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new RabbitMarketException("Keystore 를 로딩하는중 에러 발생");
        }
    }

    public String generateToken(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(getPrivateKey())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(JWTExpirationInMillis)))
                .compact();
    }

    public String generateTokenWithEmail(String email) {
        return Jwts.builder()
                .setSubject(email)
                .signWith(getPrivateKey())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(JWTExpirationInMillis)))
                .compact();
    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("rabbit-market", keyStorePassword.toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new RabbitMarketException("keystore 로 부터 개인키를 가져오는 중 에러 발생");
        }
    }

    public boolean validateToken(String jwt) {
        parser.parseClaimsJws(jwt);
        return true;
    }

    private PublicKey getPublicKey() {
        try {
            return keyStore.getCertificate("rabbit-market").getPublicKey();
        } catch (KeyStoreException e) {
            throw new RabbitMarketException("keystore 로 부터 공개키를 가져오는 중 에러 발생");
        }
    }

    public String getEmailFromJWT(String token) {
        Claims claims = parser
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public Long getJWTExpirationInMillis() {
        return JWTExpirationInMillis;
    }
}
