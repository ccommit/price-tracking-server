package com.ccommit.price_tracking_server.exception;

public class DuplicateEmailException extends PriceTrackingServerException {
    public DuplicateEmailException() {
        super("이미 사용중인 이메일입니다.", "DUPLICATE_EMAIL");
    }
}
