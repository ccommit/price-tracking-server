package com.ccommit.price_tracking_server.exception;

public class InvalidSignatureException extends PriceTrackingServerException {
    public InvalidSignatureException() {
        super("JWT 서명 검증에 실패했습니다.", "INVALID_SIGNATURE");
    }
}