package com.ccommit.price_tracking_server.exception;

public class TokenExpiredException extends PriceTrackingServerException {
    public TokenExpiredException() {
        super("세션 만료. 로그인이 필요합니다.", "TOKEN_EXPIRED");
    }
}