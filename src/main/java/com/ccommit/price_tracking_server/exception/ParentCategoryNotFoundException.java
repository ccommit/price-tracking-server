package com.ccommit.price_tracking_server.exception;

public class ParentCategoryNotFoundException extends PriceTrackingServerException {
    public ParentCategoryNotFoundException() {
        super("PARENT_CATEGORY_NOT_FOUND");
    }
}
