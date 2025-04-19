package com.ccommit.price_tracking_server.exception;

public class RoleAccessDeniedException extends PriceTrackingServerException{
    public RoleAccessDeniedException() {
        super("ROLE_ACCESS_DENIED");
    }
}
