package com.ccommit.price_tracking_server.exception.handler;

import com.ccommit.price_tracking_server.DTO.CommonResponseDTO;
import com.ccommit.price_tracking_server.exception.ExceptionDetailMessage;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import java.text.MessageFormat;

@Component
public class ValidationErrorHandler {
    // NotBlank 또는 NotNull 오류인지를 확인
    public boolean isNotBlankOrNotNullError(FieldError error) {
        return error.getCodes() != null && (
                error.getCodes()[0].startsWith("NotBlank") || error.getCodes()[0].startsWith("NotNull")
        );
    }

    // FieldError에 따른 오류 처리
    public CommonResponseDTO<?> handleFieldError(FieldError error) {
        String exceptionCode;
        CommonResponseDTO<?> errorResponse;

        if (isNotBlankOrNotNullError(error)) {
            // 필수 필드 누락 오류 처리
            exceptionCode = "MISSING_REQUIRED_FIELD";
            errorResponse = new CommonResponseDTO<>("Failure", "필수 필드가 누락되었습니다.",
                    null, exceptionCode, ExceptionDetailMessage.getExceptionMessage(exceptionCode),
                    0);

            String detailMessage = errorResponse.getErrorDetails();
            detailMessage = MessageFormat.format(detailMessage, error.getField());
            errorResponse.setErrorDetails(detailMessage);
        } else {
            // 형식 오류 처리
            exceptionCode = getErrorCodeForField(error.getField());
            String errorMessage = "{0} 형식이 올바르지 않습니다.";
            errorMessage = MessageFormat.format(errorMessage, error.getField());
            errorResponse = new CommonResponseDTO<>("Failure", errorMessage, null, exceptionCode,
                    ExceptionDetailMessage.getExceptionMessage(exceptionCode), 0);
        }

        return errorResponse;
    }

    // 필드에 따른 오류 코드 설정
    private String getErrorCodeForField(String field) {
        switch (field) {
            case "password":
                return "INVALID_PASSWORD_FORMAT";
            case "username":
                return "INVALID_USERNAME_FORMAT";
            case "phone":
                return "INVALID_PHONE_FORMAT";
            case "email":
                return "INVALID_EMAIL_FORMAT";
            default:
                return "INVALID_FIELD_FORMAT";
        }
    }
}
