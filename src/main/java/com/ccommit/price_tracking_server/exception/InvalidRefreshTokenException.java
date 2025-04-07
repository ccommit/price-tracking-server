package com.ccommit.price_tracking_server.exception;

public class InvalidRefreshTokenException extends PriceTrackingServerException{
    public InvalidRefreshTokenException() {
        super("유효하지 않은 리프레시 토큰입니다.", "INVALID_REFRESH_TOKEN");
    }
}
