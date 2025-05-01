package com.ccommit.price_tracking_server.DTO;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class CommonResponse<T> {
    private final String code;

    @Setter
    private String detailsMessage;

    private final T data;

    @Setter
    private float totalRequestTime;

    public CommonResponse(String code, String detailsMessage, T data, float totalRequestTime) {
        this.code = code;
        this.detailsMessage = detailsMessage;
        this.data = data;
        this.totalRequestTime = totalRequestTime;
    }
}