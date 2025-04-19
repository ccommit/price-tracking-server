package com.ccommit.price_tracking_server.exception;

public class TokenExpiredException extends PriceTrackingServerException {
    public TokenExpiredException() {
        super("TOKEN_EXPIRED");
    }
}