package com.ccommit.price_tracking_server.exception;

import lombok.Getter;

@Getter
public class PriceTrackingServerException extends RuntimeException{
    private final String errorCode;

    public PriceTrackingServerException(String errorCode) {
        this.errorCode = errorCode;
    }
}
