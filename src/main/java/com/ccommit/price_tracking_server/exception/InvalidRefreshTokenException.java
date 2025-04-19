package com.ccommit.price_tracking_server.exception;

public class InvalidRefreshTokenException extends PriceTrackingServerException{
    public InvalidRefreshTokenException() {
        super("INVALID_REFRESH_TOKEN");
    }
}
