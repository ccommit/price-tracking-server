package com.ccommit.price_tracking_server.service.serviceImpl;


import com.ccommit.price_tracking_server.entity.Category;
import com.ccommit.price_tracking_server.repository.CategoryRepository;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class MorphologyServiceImpl {
    private final Komoran komoran;
    private final CategoryRepository categoryRepository;
    private static final Map<String, String> wordMap = new HashMap<>();

    static {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(MorphologyServiceImpl.class.getClassLoader().getResourceAsStream("dictionary.txt"))))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || !line.contains("=")) continue;
                String[] parts = line.split("=");
                wordMap.put(parts[0].trim(), parts[1].trim());
            }
        } catch (Exception e) {
            throw new RuntimeException("치환 사전(dictionary.txt)을 불러오는 데 실패했습니다.", e);
        }
    }

    public int countMatchingTokens(List<Token> tokens1, List<Token> tokens2) {
        Set<String> tokenSet1 = tokens1.stream().map(Token::getMorph).collect(Collectors.toSet());
        Set<String> tokenSet2 = tokens2.stream().map(Token::getMorph).collect(Collectors.toSet());
        tokenSet1.retainAll(tokenSet2);  // 교집합
        return tokenSet1.size();  // 겹치는 토큰의 수 반환
    }

    public Long assignCategory(String productName) {
        String modifiedProductName = this.applyReplacement(productName);
        List<Token> productTokens = komoran.analyze(modifiedProductName).getTokenList();
        List<Category> leafCategories = categoryRepository.findLeafCategories();

        System.out.println("productName = " + modifiedProductName);
        System.out.println(komoran.analyze(modifiedProductName).getTokenList());
        Category bestMatchCategory = null;
        int maxMatchingTokens = 0;

        for (Category c : leafCategories) {
            int productTokenCount = productTokens.size();
            List<Token> categoryTokens = komoran.analyze(c.getCategoryName()).getTokenList();
            int categoryTokenCount = categoryTokens.size();
            if (categoryTokenCount > productTokenCount) {
                continue;
            }
            int matchingTokens = countMatchingTokens(productTokens, categoryTokens);
            if (matchingTokens > maxMatchingTokens && matchingTokens >= categoryTokenCount) {
                maxMatchingTokens = matchingTokens;
                bestMatchCategory = c;
            }
        }
        if (bestMatchCategory != null) {
            return bestMatchCategory.getCategoryId();
        } else {
            // 추후 기타 카테고리로 매칭 or 새로운 카테고리 생성하여 분류
            return null;
        }
    }

    private String applyReplacement(String input) {
        for (Map.Entry<String, String> entry : wordMap.entrySet()) {
            input = input.replace(entry.getKey(), entry.getValue());
        }
        return input;
    }
}
