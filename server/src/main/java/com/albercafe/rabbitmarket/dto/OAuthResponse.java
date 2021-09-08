package com.albercafe.rabbitmarket.dto;

import lombok.Data;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;

@Data
public class OAuthResponse {
    private OAuth2AccessToken oAuth2AccessToken;
    private OAuth2RefreshToken oAuth2RefreshToken;
}
