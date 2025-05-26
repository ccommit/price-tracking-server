package com.ccommit.price_tracking_server.aop;

import com.ccommit.price_tracking_server.DTO.CommonResponse;
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
    // 기준 시간은 200ms로 설정하였으며, 해당 시간을 초과하면 INFO 레벨로 로깅하여 병목 위치를 파악할 수 있도록 합니다.
    // 기준은 Google 개발자 문서(https://developers.google.com/speed/docs/insights/Server)를 참고하였습니다.
    private static final long THRESHOLD_TIME = 200;

    // @Around 어노테이션을 사용하여 메서드 실행 전후로 시간 측정을 함
     @Around("execution(* com.ccommit.price_tracking_server..*(..)) && " +
            "(@within(org.springframework.web.bind.annotation.RestControllerAdvice) || @within(org.springframework.web.bind.annotation.RestController))")
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

        // 특정 임계값 이상인 경우에만 로그 기록
        if (millis > THRESHOLD_TIME) {
            log.info("{} executed in {} seconds", methodName, seconds);  // 임계값 초과시 로그 기록
        }

        // response에 실행 시간 정보 추가
        if (result instanceof ResponseEntity<?> responseEntity) {
            Object body = responseEntity.getBody();

            // 본문이 CommonResponse 타입인 경우 실행 시간 추가
            if (body instanceof CommonResponse) {
                CommonResponse<?> responseBody = (CommonResponse<?>) responseEntity.getBody();

                responseBody.setTotalRequestTime(seconds);
                result = ResponseEntity.status(responseEntity.getStatusCode())
                        .headers(responseEntity.getHeaders())
                        .body(responseBody);
            }
        }

        return result;
    }
}

