package com.albercafe.rabbitmarket.security.oauth2.user;

import java.util.Map;

public class NaverOAuth2UserInfo extends OAuth2UserInfo {
    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("response/id");
    }

    @Override
    public String getName() {
        return (String) attributes.get("response/name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("response/email");
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("response/profile_image");
    }
}
