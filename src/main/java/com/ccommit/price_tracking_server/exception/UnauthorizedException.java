package com.ccommit.price_tracking_server.exception;

public class UnauthorizedException extends PriceTrackingServerException {
    public UnauthorizedException() {
        super("UNAUTHORIZED");
    }
}
