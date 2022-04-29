package com.example.WeibisWeb.security;

public class SecurityConstants {

    public static final String SECRET = "SECRET_KEY";
    public static final long EXPIRATION_TIME = 9_000_000; // 15 Hours
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/api/1.0/authenticate";

}
