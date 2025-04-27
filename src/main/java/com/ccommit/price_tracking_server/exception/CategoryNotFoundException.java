package com.ccommit.price_tracking_server.exception;

public class CategoryNotFoundException  extends PriceTrackingServerException {
    public CategoryNotFoundException() {
        super("CATEGORY_NOT_FOUND");
    }
}
