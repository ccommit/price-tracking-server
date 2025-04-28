package com.ccommit.price_tracking_server.DTO;


import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CommonResponseDTO<T> {
    private T data;
    private String errorCode;
    private String errorDetails;
    private float totalRequestTime;

    public void setTotalRequestTime(float totalRequestTime) {
        this.totalRequestTime = totalRequestTime;
    }

    public void setErrorDetails(String errorDetails) {
        this.errorDetails = errorDetails;
    }

    public CommonResponseDTO(T data, String errorCode, String errorDetails, float totalRequestTime) {
        this.data = data;
        this.errorCode = errorCode;
        this.errorDetails = errorDetails;
        this.totalRequestTime = totalRequestTime;
    }
}