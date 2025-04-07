package com.ccommit.price_tracking_server.exception;

public class UserAccountDisableException extends PriceTrackingServerException{
    public UserAccountDisableException() {
        super("사용자 계정이 비활성화되었습니다.", "USER_ACCOUNT_DISABLED");
    }
}
