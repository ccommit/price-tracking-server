package com.ccommit.price_tracking_server.controller;

import com.ccommit.price_tracking_server.DTO.CommonResponse;
import com.ccommit.price_tracking_server.DTO.ExchangeRateDTO;
import com.ccommit.price_tracking_server.enums.CurrencyCode;
import com.ccommit.price_tracking_server.enums.SuccessDetailMessage;
import com.ccommit.price_tracking_server.service.serviceImpl.ExchangeRateServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping
public class ExchangeRateController {
    private final ExchangeRateServiceImpl exchangeRateService;

    //환율 확인용 api (추후 삭제 예정)
    @GetMapping("/exchange-rate/{currency}")
    public ResponseEntity<CommonResponse<ExchangeRateDTO>> getExchangeRate(@PathVariable(value = "currency") CurrencyCode currency,
                                                                           @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        // 환율 조회 API는 삭제 예정이라 SUCCESS_PRODUCT_SELECTED으로 성공 메시지 대체
        SuccessDetailMessage message = SuccessDetailMessage.SUCCESS_PRODUCT_SELECTED;
        CommonResponse<ExchangeRateDTO> response = new CommonResponse<>(message.name(), message.getMessage(),
                exchangeRateService.getExchangeRates(currency, date));
        return ResponseEntity.ok(response);
    }
}
