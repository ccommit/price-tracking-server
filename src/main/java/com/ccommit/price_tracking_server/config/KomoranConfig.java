package com.ccommit.price_tracking_server.config;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KomoranConfig {

    @Bean
    public Komoran komoran() {
        // Komoran의 기본 모델을 FULL로 설정합니다.
        // 현재 3.3.9 버전사용
        return new Komoran(DEFAULT_MODEL.FULL);
    }
}
