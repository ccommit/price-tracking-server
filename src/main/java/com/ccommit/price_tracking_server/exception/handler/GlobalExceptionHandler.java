package com.ccommit.price_tracking_server.exception.handler;

import com.ccommit.price_tracking_server.DTO.CommonResponse;
import com.ccommit.price_tracking_server.exception.ExceptionDetailMessage;
import com.ccommit.price_tracking_server.exception.PriceTrackingServerException;
import jakarta.persistence.PersistenceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.List;

@Log4j2
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ValidationErrorHandler validationErrorHandler;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<?>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        // 첫 번째로 NotBlank 오류를 찾기
        for (FieldError error : fieldErrors) {
            if (validationErrorHandler.isNotBlankError(error)) {
                // NotBlank 오류 처리
                CommonResponse<?> errorResponse = validationErrorHandler.handleFieldError(error);
                log.error(errorResponse.toString());
                return ResponseEntity.ok(errorResponse);
            }
        }

        // NotBlank 오류가 없으면 형식이 잘못된 경우 (Pattern, Email, Size 등)
        if (!fieldErrors.isEmpty()) {
            FieldError error = fieldErrors.getFirst(); // 첫 번째 오류를 처리
            CommonResponse<?> errorResponse = validationErrorHandler.handleFieldError(error);
            log.error(errorResponse.toString());
            return ResponseEntity.ok(errorResponse);
        }

        // 만약 FieldError가 없다면 기본 오류 반환
        CommonResponse<?> defaultErrorResponse = new CommonResponse<>(null, "INVALID_REQUEST", "", 0);
        log.error(defaultErrorResponse.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(defaultErrorResponse);

    }

    // ConstraintViolationException은 다음 두 가지 상황에서 발생할 수 있습니다.
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<CommonResponse<?>> handleConstraintViolation(ValidationException ex) {
        Throwable cause = ex.getCause();

        // 1. 커스텀 어노테이션 기반 유효성 검사에서 검증 실패 시 발생
        if (cause instanceof PriceTrackingServerException ptse) {
            return this.handlePriceTrackingServerException(ptse); // 기존 커스텀 예외 처리 핸들러 호출
        }

        // 2. JPA 엔티티 유효성 검사 실패나 데이터베이스 제약 조건 위반 시 발생,
        // 예: @Valid 검증 실패나 UNIQUE, NOT NULL 제약 조건 위반.
        // 이는 PersistenceException과 구분되며, 후자는 주로 데이터베이스 연결 문제나 쿼리 오류와 관련됨.
        log.error("서버 오류 발생: {}", ex.getMessage(), ex);
        CommonResponse<?> errorResponse = new CommonResponse<>(
                null,
                "INTERNAL_SERVER_ERROR",
                ExceptionDetailMessage.getExceptionMessage("INTERNAL_SERVER_ERROR"),
                0
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler({PriceTrackingServerException.class})
    public ResponseEntity<CommonResponse<?>> handlePriceTrackingServerException(PriceTrackingServerException ex) {
        CommonResponse<?> errorResponse = new CommonResponse<>(null,
                ex.getErrorCode(), ExceptionDetailMessage.getExceptionMessage(ex.getErrorCode()), 0);
        log.error(errorResponse.toString());
        return ResponseEntity.ok(errorResponse);
    }

    // DB 관련 예외 처리 (네트워크, 데이터베이스 관련 예외 분리)
    @ExceptionHandler({
            SQLException.class, PersistenceException.class, DataIntegrityViolationException.class,
            ObjectOptimisticLockingFailureException.class
    })
    public ResponseEntity<CommonResponse<?>> handleDatabaseException(Exception ex, HttpServletRequest request, HandlerMethod handlerMethod) {
        // 예외가 발생한 요청 정보 가져오기
        String endpoint = request.getRequestURI();
        String method = request.getMethod();

        // HandlerMethod를 통해 메서드 정보 추출
        String methodName = handlerMethod.getMethod().getName();
        String className = handlerMethod.getBeanType().getName();

        // 로그 메시지 구성
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("서버 오류 발생: ").append(ex.getMessage())
                .append(", 발생 위치 : ").append(className).append(".").append(methodName)
                .append(", HTTP 메서드 : ").append(method)
                .append(", Endpoint : ").append(endpoint);

        // 예외를 로그로 출력
        log.error(logMessage.toString());
        CommonResponse<?> errorResponse = new CommonResponse<>(null,
                "INTERNAL_SERVER_ERROR", ExceptionDetailMessage.getExceptionMessage("INTERNAL_SERVER_ERROR"), 0);
        log.error(errorResponse.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
    // 트랜잭션 관련 예외
    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<String> handleTransactionException(TransactionSystemException ex) {
        log.warn("Transaction failed: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("트랜잭션 처리 중 오류가 발생했습니다.");
    }
    // 네트워크 관련 예외 처리 (Socket, Connect, Timeout 등)
    @ExceptionHandler({
            ConnectException.class, SocketTimeoutException.class, UnknownHostException.class,
            SocketException.class, IOException.class, JDBCConnectionException.class
    })
    public ResponseEntity<CommonResponse<?>> handleNetworkException(Exception ex, HttpServletRequest request) {
        String endpoint = request.getRequestURI();
        String method = request.getMethod();
        log.error("네트워크 오류 발생: {} - 요청 정보: {} {}", ex.getMessage(), method, endpoint);

        CommonResponse<?> errorResponse = new CommonResponse<>(null,
                "NETWORK_ERROR", ExceptionDetailMessage.getExceptionMessage("NETWORK_ERROR"), 0);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorResponse);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<CommonResponse<?>> handleException(Exception ex) {

        log.error("서버 오류 발생: {}", ex.getMessage(), ex);
        CommonResponse<?> errorResponse = new CommonResponse<>(null,
                "INTERNAL_SERVER_ERROR", ExceptionDetailMessage.getExceptionMessage("INTERNAL_SERVER_ERROR"), 0);
        log.error(errorResponse.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

}
