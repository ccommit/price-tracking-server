package com.ccommit.price_tracking_server.exception;

public class DuplicateEmailException extends PriceTrackingServerException {
    public DuplicateEmailException() {
        super("DUPLICATE_EMAIL");
    }
}
