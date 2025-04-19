package com.ccommit.price_tracking_server.exception;

public class UserAccountDisableException extends PriceTrackingServerException{
    public UserAccountDisableException() {
        super("USER_ACCOUNT_DISABLED");
    }
}
