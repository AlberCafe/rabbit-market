package com.albercafe.rabbitmarket.dto;

import com.albercafe.rabbitmarket.entity.User;
import com.albercafe.rabbitmarket.entity.UserProfile;
import com.albercafe.rabbitmarket.util.AuthProvider;
import com.albercafe.rabbitmarket.util.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class OAuthAttributes {
    private final Map<String, Object> attributes;
    private final String nameAttributeKey;
    private final String username;
    private final String email;
    private final String picture;
    private final AuthProvider authProvider;
    private final Boolean enabled;

    // OAuth2User 에서 리턴하는 사용자 정보가 Map 이어서 하나씩 변환
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {

        if ("naver".equals(registrationId)) {
            return ofNaver(registrationId, userNameAttributeName, attributes);
        }

        if ("kakao".equals(registrationId)) {
            return ofKakao(registrationId, userNameAttributeName, attributes);
        }

        return ofGoogle(registrationId, userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofNaver(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .username((String) response.get("nickname"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .authProvider(AuthProvider.valueOf(registrationId.toUpperCase()))
                .enabled(true)
                .build();
    }

    private static OAuthAttributes ofKakao(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuthAttributes.builder()
                .username((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .picture((String) kakaoProfile.get("profile_image_url"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .authProvider(AuthProvider.valueOf(registrationId.toUpperCase()))
                .enabled(true)
                .build();
    }

    private static OAuthAttributes ofGoogle(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .username((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .authProvider(AuthProvider.valueOf(registrationId.toUpperCase()))
                .enabled(true)
                .build();
    }

    public User toEntity() {
        User user = new User();
        UserProfile userProfile = new UserProfile();

        userProfile.setUsername(username);
        userProfile.setProfilePhoto(picture);

        user.setEmail(email);
        user.setRoleType(RoleType.USER);
        user.setUserProfile(userProfile);
        user.setProvider(authProvider);
        user.setEnabled(enabled);

        return user;
    }
}
