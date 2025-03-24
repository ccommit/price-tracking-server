package com.ccommit.price_tracking_server.exception;

public class UnauthorizedException extends PriceTrackingServerException {
    public UnauthorizedException() {
        super("로그인이 필요합니다.", "UNAUTHORIZED");
    }
}
