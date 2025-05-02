package com.ccommit.price_tracking_server.service;

import com.ccommit.price_tracking_server.DTO.GoogleProductRequest;
import com.ccommit.price_tracking_server.DTO.GoogleProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService{
    List<GoogleProductResponse> selectProduct(GoogleProductRequest googleProductRequest);
}
