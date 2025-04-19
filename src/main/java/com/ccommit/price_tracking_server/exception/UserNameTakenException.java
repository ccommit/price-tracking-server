package com.ccommit.price_tracking_server.exception;

public class UserNameTakenException extends PriceTrackingServerException{
    public UserNameTakenException() {
        super("USERNAME_TAKEN");
    }
}
