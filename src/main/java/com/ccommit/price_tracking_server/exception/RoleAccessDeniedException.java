package com.ccommit.price_tracking_server.exception;

public class RoleAccessDeniedException extends PriceTrackingServerException{
    public RoleAccessDeniedException() {
        super("권한이 없습니다.", "ROLE_ACCESS_DENIED");
    }
}
