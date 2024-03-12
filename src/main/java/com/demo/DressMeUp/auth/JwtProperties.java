package com.demo.DressMeUp.auth;

public interface JwtProperties {
    String SECRET = "dressmeup"; // 우리 서버만 알고 있는 비밀값
    int EXPIRATION_TIME = 60000 * 100;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
