package com.web.finance.config;

public class JwtConstant {
    public static final String SECRET_KEY = JwtKeyRead.getSecretKey();
    public static final String JWT_HEADER = "Authorization";
}
