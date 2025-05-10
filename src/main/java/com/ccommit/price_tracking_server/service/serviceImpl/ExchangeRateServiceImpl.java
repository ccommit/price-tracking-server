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

@Log4j2
@Service
@RequiredArgsConstructor
public class ExchangeRateServiceImpl {

    private static final String BASE_URL = "https://api.exchangerate.host/live?access_key=%s&source=%s&currencies=%s";
    //추후 API Key는 환경변수로 관리
    private static final String ACCESS_KEY = "a89cf6136c8a06f7ee3b9430d7d17fa0";


    public ExchangeRateDTO getExchangeRates(CurrencyCode currencyCode) {

        try{
            String url = String.format(
                    BASE_URL,
                    ACCESS_KEY,
                    currencyCode,
                    CurrencyCode.getAllCurrencyCodes()
            );

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.readValue(response.body(), new TypeReference<>() {});
        } catch (Exception ex) {
            System.out.println("Exception:");
            System.out.println(ex.toString());
        }
        return null;
    }
}
