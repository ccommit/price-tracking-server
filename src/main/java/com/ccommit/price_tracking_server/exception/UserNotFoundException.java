package com.ccommit.price_tracking_server.exception;

public class UserNotFoundException extends PriceTrackingServerException{
    public UserNotFoundException() {
        super("사용자를 찾을 수 없습니다.", "USER_NOT_FOUND");
    }
}