package com.ccommit.price_tracking_server.repository;

import com.ccommit.price_tracking_server.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
