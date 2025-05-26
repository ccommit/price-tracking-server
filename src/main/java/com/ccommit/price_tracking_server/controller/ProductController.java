package com.ccommit.price_tracking_server.controller;

import com.ccommit.price_tracking_server.DTO.CommonResponse;
import com.ccommit.price_tracking_server.DTO.GoogleProductRequest;
import com.ccommit.price_tracking_server.DTO.GoogleProductResponse;
import com.ccommit.price_tracking_server.enums.SuccessDetailMessage;
import com.ccommit.price_tracking_server.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    //추후 batch 로 변경 예정
    @GetMapping("/select")
    public ResponseEntity<CommonResponse<List<GoogleProductResponse>>> selectGoogleProduct(@ModelAttribute GoogleProductRequest googleProductRequest) {

        SuccessDetailMessage message = SuccessDetailMessage.SUCCESS_PRODUCT_SELECTED;
        CommonResponse<List<GoogleProductResponse>> response = new CommonResponse<>(message.name(), message.getMessage(),
                productService.selectProduct(googleProductRequest));

        return ResponseEntity.ok(response);
    }
}
