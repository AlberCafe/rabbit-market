package com.albercafe.rabbitmarket.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleType {
    GUEST("ROLE_GUEST", "게스트"),
    USER("ROLE_USER", "일반 유저"),
    ADMIN("ROLE_ADMIN", "관리자");

    private final String key;
    private final String title;
}
