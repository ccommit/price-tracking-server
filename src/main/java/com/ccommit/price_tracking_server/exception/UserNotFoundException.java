package com.ccommit.price_tracking_server.exception;

public class UserNotFoundException extends PriceTrackingServerException{
    public UserNotFoundException() {
        super("USER_NOT_FOUND");
    }
}