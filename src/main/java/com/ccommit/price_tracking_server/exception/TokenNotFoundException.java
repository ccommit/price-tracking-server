package com.ccommit.price_tracking_server.exception;

public class TokenNotFoundException extends PriceTrackingServerException {
    public TokenNotFoundException() {
        super("TOKEN_NOT_FOUND");
    }
}
