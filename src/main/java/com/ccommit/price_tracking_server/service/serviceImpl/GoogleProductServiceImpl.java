package com.ccommit.price_tracking_server.service.serviceImpl;

import com.ccommit.price_tracking_server.DTO.GoogleProductRequest;
import com.ccommit.price_tracking_server.DTO.GoogleProductResponse;
import com.ccommit.price_tracking_server.service.ProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class GoogleProductServiceImpl implements ProductService {

    // 추후 batch 로 변경 예정
    @Override
    public List<GoogleProductResponse> selectProduct(GoogleProductRequest googleProductRequest) {
        try{

            StringBuilder url = new StringBuilder();

            url.append("https://serpapi.com/search.json?")
                    .append(googleProductRequest.toQueryString());

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url.toString()))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.body());
            JsonNode productsNode = rootNode.path("shopping_results");

            return objectMapper.readValue(productsNode.toString(), new TypeReference<>(){});
        } catch (Exception ex) {
            System.out.println("Exception:");
            System.out.println(ex.toString());
        }
        return null;
    }
}
