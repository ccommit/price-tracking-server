package com.ccommit.price_tracking_server.exception.handler;

import com.ccommit.price_tracking_server.DTO.CommonResponseDTO;
import com.ccommit.price_tracking_server.exception.ExceptionDetailMessage;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import java.text.MessageFormat;

@Component
public class ValidationErrorHandler {
    // NotBlank 오류인지를 확인
    public boolean isNotBlankError(FieldError error) {
        return error.getCodes() != null && (error.getCodes()[0].startsWith("NotBlank"));
    }

    // FieldError에 따른 오류 처리
    public CommonResponseDTO<?> handleFieldError(FieldError error) {
        String exceptionCode;
        CommonResponseDTO<?> errorResponse;

        if (isNotBlankError(error)) {
            // 필수 필드 누락 오류 처리
            exceptionCode = "MISSING_REQUIRED_FIELD";
            errorResponse = new CommonResponseDTO<>(
                    null, exceptionCode, ExceptionDetailMessage.getExceptionMessage(exceptionCode),
                    0);

            String detailMessage = errorResponse.getErrorDetails();
            detailMessage = MessageFormat.format(detailMessage, error.getField());
            errorResponse.setErrorDetails(detailMessage);
        } else {
            // 형식 오류 처리
            exceptionCode = "INVALID_" + error.getField().toUpperCase() + "_FORMAT";
            errorResponse = new CommonResponseDTO<>(null, exceptionCode,
                    ExceptionDetailMessage.getExceptionMessage(exceptionCode), 0);
        }

        return errorResponse;
    }
}
