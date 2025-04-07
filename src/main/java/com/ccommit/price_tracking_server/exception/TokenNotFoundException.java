package com.ccommit.price_tracking_server.exception;

public class TokenNotFoundException extends PriceTrackingServerException {
    public TokenNotFoundException() {
        super("토큰을 찾지 못했습니다.", "TOKEN_NOT_FOUND");
    }
}
