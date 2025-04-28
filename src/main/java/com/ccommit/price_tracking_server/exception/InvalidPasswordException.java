package com.ccommit.price_tracking_server.exception;

public class InvalidPasswordException extends PriceTrackingServerException {
    public InvalidPasswordException() {
        super("INVALID_PASSWORD");
    }
}
