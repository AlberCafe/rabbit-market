package com.albercafe.rabbitmarket.controller;

import com.albercafe.rabbitmarket.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ResolvableType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class OAuthController {

    private final ClientRegistrationRepository clientRegistrationRepository;

    @GetMapping("/api/oauth2/login")
    public Map getLoginLink(Model model) {
        Map<String, String> oauth2AuthenticationUrls = new HashMap<>();

        Iterable<ClientRegistration> clientRegistrations = null;

        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository).as(Iterable.class);

        if (type != ResolvableType.NONE && ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }

        assert clientRegistrations != null;
        clientRegistrations.forEach(registration ->
                oauth2AuthenticationUrls.put(
                        registration.getClientName(), Constants.AUTHORIZATION_REQUEST_BASE_URL + "/" + registration.getRegistrationId()
                )
        );

        model.addAttribute("urls", oauth2AuthenticationUrls);

        return oauth2AuthenticationUrls;
    }

    @GetMapping("/api/oauth2/success")
    public OAuth2User loginSuccess(@AuthenticationPrincipal OAuth2User user) {
        return user;
    }

    @GetMapping("/api/oauth2/fail")
    public String loginFail() {
        return "oauth2 login failed";
    }
}
