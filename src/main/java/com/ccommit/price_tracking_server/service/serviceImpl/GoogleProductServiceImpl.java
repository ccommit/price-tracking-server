package com.ccommit.price_tracking_server.service.serviceImpl;

import com.ccommit.price_tracking_server.DTO.GoogleProductResponseDTO;
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

    @Override
    public List<GoogleProductResponseDTO> selectProduct() {
        try{
            // 추후 API Key는 환경변수로 관리
            String apiKey = "";
            String query = "갤럭시 25";

            String url = String.format(
                    "https://serpapi.com/search.json?engine=google_shopping&q=%s&google_domain=google.co.kr&gl=kr&hl=ko&location=585069a9ee19ad271e9b6c84&api_key=%s",
                    java.net.URLEncoder.encode(query, java.nio.charset.StandardCharsets.UTF_8),
                    apiKey
            );

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.body());
            JsonNode productsNode = rootNode.path("shopping_results");
            List<GoogleProductResponseDTO> productList = objectMapper.readValue(productsNode.toString(), new TypeReference<>(){});

            return productList;
        } catch (Exception ex) {
            System.out.println("Exception:");
            System.out.println(ex.toString());
        }
        return null;
    }
}
