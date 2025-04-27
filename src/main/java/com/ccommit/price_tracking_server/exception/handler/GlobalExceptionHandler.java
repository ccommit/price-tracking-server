package com.ccommit.price_tracking_server.exception.handler;

import com.ccommit.price_tracking_server.DTO.CommonResponseDTO;
import com.ccommit.price_tracking_server.exception.ExceptionDetailMessage;
import com.ccommit.price_tracking_server.exception.InvalidCategoryLevelException;
import com.ccommit.price_tracking_server.exception.PriceTrackingServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.List;

@Log4j2
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ValidationErrorHandler validationErrorHandler;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponseDTO<?>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        // 첫 번째로 NotBlank 오류를 찾기
        for (FieldError error : fieldErrors) {
            if (validationErrorHandler.isNotBlankError(error)) {
                // NotBlank 오류 처리
                CommonResponseDTO<?> errorResponse = validationErrorHandler.handleFieldError(error);
                log.error(errorResponse.toString());
                return ResponseEntity.ok(errorResponse);
            }
        }

        // NotBlank 오류가 없으면 형식이 잘못된 경우 (Pattern, Email, Size 등)
        if (!fieldErrors.isEmpty()) {
            FieldError error = fieldErrors.getFirst(); // 첫 번째 오류를 처리
            CommonResponseDTO<?> errorResponse = validationErrorHandler.handleFieldError(error);
            log.error(errorResponse.toString());
            return ResponseEntity.ok(errorResponse);
        }

        // 만약 FieldError가 없다면 기본 오류 반환
        CommonResponseDTO<?> defaultErrorResponse = new CommonResponseDTO<>(null, "INVALID_REQUEST", "", 0);
        log.error(defaultErrorResponse.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(defaultErrorResponse);

    }

    @ExceptionHandler({PriceTrackingServerException.class})
    public ResponseEntity<CommonResponseDTO<?>> handlePriceTrackingServerException(PriceTrackingServerException ex) {
        CommonResponseDTO<?> errorResponse = new CommonResponseDTO<>(null,
                ex.getErrorCode(), ExceptionDetailMessage.getExceptionMessage(ex.getErrorCode()), 0);
        log.error(errorResponse.toString());
        return ResponseEntity.ok(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CommonResponseDTO<?>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        InvalidCategoryLevelException invalidCategoryLevelException = new InvalidCategoryLevelException();
        CommonResponseDTO<?> errorResponse = new CommonResponseDTO<>(null,
                invalidCategoryLevelException.getErrorCode(), ExceptionDetailMessage.getExceptionMessage(invalidCategoryLevelException.getErrorCode()), 0);
        log.error(errorResponse.toString());
        return ResponseEntity.ok(errorResponse);
    }

    @ExceptionHandler({RuntimeException.class, SQLException.class, NullPointerException.class})
    public ResponseEntity<CommonResponseDTO<?>> handleException(Exception ex) {

        log.error("서버 오류 발생: {}", ex.getMessage(), ex);
        CommonResponseDTO<?> errorResponse = new CommonResponseDTO<>(null,
                "INTERNAL_SERVER_ERROR", ExceptionDetailMessage.getExceptionMessage("INTERNAL_SERVER_ERROR"), 0);
        log.error(errorResponse.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
