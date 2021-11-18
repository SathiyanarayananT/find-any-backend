package com.findany.commonservice.constants;

import org.springframework.beans.factory.annotation.Value;

public class AppConstants {

    public static final String X_AUTH_USER_ID = "x-auth-user-id";
    public static final String SECRET = "findAnyWithMe";
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
}
