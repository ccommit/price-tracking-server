package com.ccommit.price_tracking_server.exception;

public class InvalidSignatureException extends PriceTrackingServerException {
    public InvalidSignatureException() {
        super("INVALID_SIGNATURE");
    }
}