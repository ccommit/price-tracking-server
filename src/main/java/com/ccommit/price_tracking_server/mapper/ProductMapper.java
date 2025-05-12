package com.ccommit.price_tracking_server.mapper;


import com.ccommit.price_tracking_server.DTO.GoogleProductResponse;
import com.ccommit.price_tracking_server.entity.Product;
import com.ccommit.price_tracking_server.enums.CurrencyCode;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    private final ModelMapper modelMapper;

    public Product convertToEntity(GoogleProductResponse googleProductResponse) {
        Double beforeDiscountedPrice = googleProductResponse.getExtractedOldPrice() != null
                ? googleProductResponse.getExtractedOldPrice().doubleValue()
                : 0.0;

        Double discountedPrice = googleProductResponse.getExtractedPrice() != null
                ? googleProductResponse.getExtractedPrice().doubleValue()
                : 0.0;

        Double deliveryCost = googleProductResponse.getDelivery() != null && !googleProductResponse.getDelivery().isEmpty()
                ? Double.parseDouble(googleProductResponse.getDelivery().split(" ")[0].substring(1))
                : 0.0;

        Double discountRate = beforeDiscountedPrice != 0
                ? ((beforeDiscountedPrice - discountedPrice) / beforeDiscountedPrice) * 100
                : 0.0;

        Product product = Product.builder()
                .googleProductId(googleProductResponse.getProductId())
                .productName(googleProductResponse.getTitle())
                .url(googleProductResponse.getProductLink())
                .beforeDiscountedPrice(beforeDiscountedPrice)
                .discountedPrice(discountedPrice)
                .discountRate(discountRate)
                .shippingFee(deliveryCost)
                .currency(CurrencyCode.KRW.name())  // 통화 단위 (예: USD, KRW)
                .isNew(true)
                .createdAt(LocalDateTime.now())
                .build();
        return product;
    }
}
