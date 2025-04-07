package com.ccommit.price_tracking_server.exception;

public class InvalidPasswordException extends PriceTrackingServerException {
    public InvalidPasswordException() {
        super("비밀번호가 일치하지 않습니다.", "INVALID_PASSWORD");
    }
}
