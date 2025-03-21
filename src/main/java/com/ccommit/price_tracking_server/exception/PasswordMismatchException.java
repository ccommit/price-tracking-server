package com.ccommit.price_tracking_server.exception;

import lombok.Getter;

@Getter
public class PasswordMismatchException extends RuntimeException {
    private final String errorCode;
    private final String message;

    public PasswordMismatchException() {
        this.message = "비밀번호가 일치하지 않습니다.";
        this.errorCode = "PASSWORD_MISMATCH";
    }
}
