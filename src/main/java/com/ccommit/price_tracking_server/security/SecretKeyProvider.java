package com.ccommit.price_tracking_server.security;

public class SecretKeyProvider {
    private static final String SECRET_KEY = System.getenv("JWT_SECRET_KEY");

    private static final SecretKeyProvider instance = new SecretKeyProvider();

    public static SecretKeyProvider getInstance() {
        return instance;
    }

    public String getSecretKey() {
        return SECRET_KEY;
    }
}