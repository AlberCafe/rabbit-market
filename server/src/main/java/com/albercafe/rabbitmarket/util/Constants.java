package com.albercafe.rabbitmarket.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    public static final String ACTIVATION_EMAIL = "http://ec2-3-35-167-70.ap-northeast-2.compute.amazonaws.com:8080/api/auth/accountVerification";
    public static final String STATIC_FILE_LINK = "https://rabbit-market-static-files.s3.ap-northeast-2.amazonaws.com";
    public static final String AUTHORIZATION_REQUEST_BASE_URL = "oauth2/authorization";
    public static final String CLIENT_PROPERTY_KEY = "spring.security.oauth2.client.registration.";
    public static final String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
    public static final String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";
}
