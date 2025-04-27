package com.ccommit.price_tracking_server.controller;

import com.ccommit.price_tracking_server.DTO.CommonResponseDTO;
import com.ccommit.price_tracking_server.DTO.GoogleProductResponseDTO;
import com.ccommit.price_tracking_server.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<CommonResponseDTO<List<GoogleProductResponseDTO>>> selectGoogleProduct() {

        List<GoogleProductResponseDTO> googleProductResponseDTOs = productService.selectProduct();
        CommonResponseDTO<List<GoogleProductResponseDTO>> response = new CommonResponseDTO<>(googleProductResponseDTOs, "", "", 0);

        return ResponseEntity.ok(response);
    }
}
