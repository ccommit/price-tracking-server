package com.ccommit.price_tracking_server.exception;

import lombok.Getter;

@Getter
public class PasswordMismatchException extends PriceTrackingServerException {
    public PasswordMismatchException() {
        super("PASSWORD_MISMATCH");
    }
}
