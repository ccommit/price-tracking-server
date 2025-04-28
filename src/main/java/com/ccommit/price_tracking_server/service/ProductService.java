package com.ccommit.price_tracking_server.service;

import com.ccommit.price_tracking_server.DTO.GoogleProductResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    // 추후 batch 로 변경 예정
    List<GoogleProductResponseDTO> selectProduct();
}
