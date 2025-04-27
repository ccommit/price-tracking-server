package com.ccommit.price_tracking_server.exception;

public class CategoryHasChildrenException extends PriceTrackingServerException {
    public CategoryHasChildrenException() {
        super("CATEGORY_HAS_CHILDREN");
    }
}
