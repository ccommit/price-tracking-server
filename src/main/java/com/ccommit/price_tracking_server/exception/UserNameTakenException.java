package com.ccommit.price_tracking_server.exception;

public class UserNameTakenException extends PriceTrackingServerException{
    public UserNameTakenException() {
        super("닉네임이 중복되었습니다.", "USERNAME_TAKEN");
    }
}
