package com.ccommit.price_tracking_server.config;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KomoranConfig {

    @Bean
    public Komoran komoran() {
        Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
        return komoran;
    }
}
