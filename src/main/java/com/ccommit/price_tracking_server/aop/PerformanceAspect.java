package com.ccommit.price_tracking_server.aop;

import com.ccommit.price_tracking_server.DTO.CommonResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;


@Log4j2
@Aspect
@Component
public class PerformanceAspect {

    // @Around 어노테이션을 사용하여 메서드 실행 전후로 시간 측정을 함
    @Around("execution(* com.ccommit.price_tracking_server..*(..)) && " +
            "@within(org.springframework.web.bind.annotation.RestControllerAdvice) || " +
            "@within(org.springframework.web.bind.annotation.RestController)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start(); // 메서드 실행 전 시간을 측정

        // 서비스 메서드 실행
        Object result = joinPoint.proceed();
        stopWatch.stop(); // 정상적으로 실행된 후 시간을 측정

        // 실행 시간을 로그로 기록
        String methodName = joinPoint.getSignature().getName();
        long millis = stopWatch.getTotalTimeMillis();
        float seconds = millis / 1000.0f;
        log.info(methodName + " executed in " + seconds + " ms");

        // response에 실행 시간 정보 추가
        if (result instanceof ResponseEntity<?>) {
            ResponseEntity<?> responseEntity = (ResponseEntity<?>) result;
            Object body = responseEntity.getBody();

            // 본문이 CommonResponseDTO 타입인 경우 실행 시간 추가
            if (body instanceof CommonResponseDTO) {
                CommonResponseDTO<?> responseBody = (CommonResponseDTO<?>) responseEntity.getBody();

                responseBody.setTotalRequestTime(seconds);
                result = ResponseEntity.status(responseEntity.getStatusCode())
                        .headers(responseEntity.getHeaders())
                        .body(responseBody);
            }
        }

        return result;
    }
}

