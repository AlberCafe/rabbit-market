package com.albercafe.rabbitmarket.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    public static final String ACTIVATION_EMAIL = "http://ec2-3-35-167-70.ap-northeast-2.compute.amazonaws.com:8080/api/auth/accountVerification";
    public static final String STATIC_FILE_LINK = "https://rabbit-market-static-files.s3.ap-northeast-2.amazonaws.com";
    public static final String MISSING_USER_INFO_URI_ERROR_CODE = "missing_user_info_uri";
    public static final String MISSING_USER_NAME_ATTRIBUTE_ERROR_CODE = "missing_user_name_attribute";
    public static final String INVALID_USER_INFO_RESPONSE_ERROR_CODE = "invalid_user_info_response";
}
