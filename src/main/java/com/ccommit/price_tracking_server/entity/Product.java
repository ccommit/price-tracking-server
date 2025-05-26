package com.ccommit.price_tracking_server.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Setter
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "google_product_id")
    private String googleProductId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "url")
    private String url;

    @Column(name = "before_discounted_price")
    private Double beforeDiscountedPrice;

    @Column(name = "discounted_price")
    private Double discountedPrice;

    @Column(name = "discount_rate")
    private Double discountRate;

    @Column(name = "shipping_fee")
    private Double shippingFee;

    @Column(name = "currency")
    private String currency;

    @Column(name = "is_new")
    private Boolean isNew;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
