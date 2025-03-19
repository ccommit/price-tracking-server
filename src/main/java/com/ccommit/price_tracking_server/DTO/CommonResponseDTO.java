package com.ccommit.price_tracking_server.DTO;


import lombok.Getter;

@Getter
public class CommonResponseDTO<T> {
    private String status;
    private String message;
    private T responseBody;
    private String errorCode;
    private float totalRequestTime;

    public void setTotalRequestTime(float totalRequestTime) {
        this.totalRequestTime = totalRequestTime;
    }
    public CommonResponseDTO(String status, String message, T responseBody, String errorCode, float totalRequestTime) {
        this.status = status;
        this.message = message;
        this.responseBody = responseBody;
        this.errorCode = errorCode;
        this.totalRequestTime = totalRequestTime;
    }
}