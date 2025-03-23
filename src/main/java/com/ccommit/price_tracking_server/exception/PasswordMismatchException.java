package com.ccommit.price_tracking_server.exception;

import lombok.Getter;

@Getter
public class PasswordMismatchException extends PriceTrackingServerException {
    public PasswordMismatchException() {
        super("비밀번호가 일치하지 않습니다.", "PASSWORD_MISMATCH");
    }
}
