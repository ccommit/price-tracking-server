package com.ccommit.price_tracking_server.exception;

public class InvalidTokenFormatException extends PriceTrackingServerException {
    public InvalidTokenFormatException() {
        super("INVALID_TOKEN_FORMAT");
    }
}