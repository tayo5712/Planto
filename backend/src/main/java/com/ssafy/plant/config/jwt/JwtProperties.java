package com.ssafy.plant.config.jwt;

public interface JwtProperties {
    String SECRET = "Planto";   // 시그니처 해싱 비밀 키
    int EXPIRATION_TIME = 864000000;    // 토큰 만료 기간
    String TOKEN_PREFIX = "Bearer ";     
    String HEADER_STRING = "Authorization";
}
