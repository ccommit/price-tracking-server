package com.ccommit.price_tracking_server.exception;

public class InvalidCategoryLevelException  extends PriceTrackingServerException {
    public InvalidCategoryLevelException() {
        super("INVALID_CATEGORY_LEVEL");
    }
}
