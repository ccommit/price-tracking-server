package com.ccommit.price_tracking_server.enums;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum CurrencyCode {
    USD, // 미국 달러
    KRW, // 대한민국 원
    EUR, // 유로
    JPY, // 일본 엔
    GBP, // 영국 파운드
    CNY, // 중국 위안
    AUD, // 호주 달러
    CAD, // 캐나다 달러
    CHF, // 스위스 프랑
    HKD; // 홍콩 달러

    public static String getAllCurrencyCodes() {
        return Arrays.stream(CurrencyCode.values())
                .map(Enum::name)  // enum 이름만 뽑기
                .collect(Collectors.joining(","));

    }
}
