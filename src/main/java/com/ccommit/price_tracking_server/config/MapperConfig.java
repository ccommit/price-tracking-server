package com.ccommit.price_tracking_server.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        // STRICT 전략은 소스 객체와 대상 객체 간에 이름과 타입이 정확히 일치하는 필드만 매핑합니다.
        // 의도하지 않은 매핑 오류(예: 유사한 이름의 필드 매핑)를 줄이기 위해 StrICT 전략을 사용했습니다.
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }
}