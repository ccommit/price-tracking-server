package com.ccommit.price_tracking_server.controller;

import com.ccommit.price_tracking_server.DTO.CommonResponseDTO;
import com.ccommit.price_tracking_server.DTO.ExchangeRateDTO;
import com.ccommit.price_tracking_server.enums.CurrencyCode;
import com.ccommit.price_tracking_server.service.serviceImpl.ExchangeRateServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping
public class ExchangeRateController {
    private final ExchangeRateServiceImpl exchangeRateService;

    @GetMapping("/exchange-rate/{currency}")
    public ResponseEntity<CommonResponseDTO<ExchangeRateDTO>> getExchangeRate(@PathVariable(value = "currency") CurrencyCode currency) {
        CommonResponseDTO<ExchangeRateDTO> response = new CommonResponseDTO<>(exchangeRateService.getExchangeRates(currency),
                "", "", 0);
        return ResponseEntity.ok(response);
    }
}
