package com.ccommit.price_tracking_server.exception;

public class InvalidTokenFormatException extends PriceTrackingServerException {
    public InvalidTokenFormatException() {
        super("잘못된 JWT 토큰입니다.", "INVALID_TOKEN_FORMAT");
    }
}