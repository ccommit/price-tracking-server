package com.ccommit.price_tracking_server.exception.handler;

import com.ccommit.price_tracking_server.DTO.CommonResponse;
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
    public CommonResponse<?> handleFieldError(FieldError error) {
        String fieldName = error.getField();
        String[] errorCodes = error.getCodes();
        boolean isCategoryName = "categoryName".equals(fieldName);
        boolean isSizeViolation = errorCodes != null && errorCodes[0].startsWith("Size");
        ExceptionDetailMessage message = null;
        String detailMessage = "";

        if (isNotBlankError(error)) {
            message = ExceptionDetailMessage.MISSING_REQUIRED_FIELD;
            detailMessage = MessageFormat.format(message.getMessage(), error.getField());
            // 필수 필드 누락 오류 처리
        }
        else if (isCategoryName && isSizeViolation) {
            // 카테고리 이름 오류 처리
            message = ExceptionDetailMessage.CATEGORY_NAME_TOO_LONG;
            detailMessage = message.getMessage();

        }else {
            // 형식 오류 처리
             String exceptionCode = new StringBuilder()
                            .append("INVALID_")
                            .append(error.getField().toUpperCase())
                            .append("_FORMAT")
                            .toString();
            message = ExceptionDetailMessage.exceptionCodeToEnum(exceptionCode);
            detailMessage = message.getMessage();
        }

        return new CommonResponse<>(message.name(), detailMessage, null, 0);
    }
}
