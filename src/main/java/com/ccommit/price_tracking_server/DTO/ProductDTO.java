package com.ccommit.price_tracking_server.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long categoryId;
    private String googleProductId;
    private String productName;
    private String url;
    private Double beforeDiscountedPrice;
    private Double discountedPrice;
    private Double discountRate;
    private Double shippingFee;
    private String currency;
    private Boolean isNew;
    private LocalDateTime createdAt;
}
