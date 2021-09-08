package com.albercafe.rabbitmarket.controller;

import com.albercafe.rabbitmarket.dto.CustomResponse;
import com.albercafe.rabbitmarket.dto.OAuthResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OAuthController {

    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    @GetMapping("/loginSuccess")
    public ResponseEntity<CustomResponse> getLoginInfo(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        OAuth2AuthorizedClient client = oAuth2AuthorizedClientService.loadAuthorizedClient(
                oAuth2AuthenticationToken.getAuthorizedClientRegistrationId(),
                oAuth2AuthenticationToken.getName()
        );

        OAuthResponse oAuthResponse = new OAuthResponse();
        oAuthResponse.setOAuth2AccessToken(client.getAccessToken());
        oAuthResponse.setOAuth2RefreshToken(client.getRefreshToken());

        CustomResponse responseBody = new CustomResponse();
        responseBody.setData(oAuthResponse);
        responseBody.setError(null);

        return ResponseEntity.status(200).body(responseBody);
    }
}
