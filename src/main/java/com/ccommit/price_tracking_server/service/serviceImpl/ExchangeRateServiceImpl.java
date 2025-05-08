package com.ccommit.price_tracking_server.service.serviceImpl;

import com.ccommit.price_tracking_server.DTO.ExchangeRateDTO;
import com.ccommit.price_tracking_server.enums.CurrencyCode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

@Log4j2
@Service
@RequiredArgsConstructor
public class ExchangeRateServiceImpl {

    private static final String BASE_LIVE_URL = "https://api.exchangerate.host/live?access_key=%s&source=%s&currencies=%s";
    private static final String BASE_HISTORICAL_URL = "https://api.exchangerate.host/historical?date=%s&access_key=%s&source=%s&currencies=%s";
    //추후 API Key는 환경변수로 관리
    private static final String ACCESS_KEY = "76058dabe843af511b38930e806a89e0";

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ExchangeRateDTO getExchangeRates(CurrencyCode currencyCode, LocalDate date) {
        try {
            String url = buildUrl(currencyCode, date);
            String responseBody = sendGetRequest(url);
            return parseResponse(responseBody);
        } catch (Exception ex) {
            // 추후 RabbitMQ로 에러 전송 예정
            log.error("환율 조회 중 예외 발생", ex);
            return null;
        }
    }

    private String buildUrl(CurrencyCode currencyCode, LocalDate date) {
        String currencyList = CurrencyCode.getAllCurrencyCodes();
        if (date == null) {
            return String.format(BASE_LIVE_URL, ACCESS_KEY, currencyCode.name(), currencyList);
        } else {
            return String.format(BASE_HISTORICAL_URL, date, ACCESS_KEY, currencyCode.name(), currencyList);
        }
    }

    private String sendGetRequest(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    private ExchangeRateDTO parseResponse(String responseBody) throws Exception {
        return objectMapper.readValue(responseBody, new TypeReference<>() {});
    }
}
