package com.ccommit.price_tracking_server.service;

public interface TokenService {
    String refreshAccessToken(String refreshToken);
}