package com.ccommit.price_tracking_server.aop;

import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.sql.SQLException;

@Aspect
@Component
@Log4j2
public class GlobalExceptionAspect {

    // DB 관련 예외를 처리하는 AOP
    @Around("execution(* com.ccommit.price_tracking_server.repository.*.save*(..)) || " +
            "execution(* com.ccommit.price_tracking_server.repository.*.delete*(..))")
    @Transactional(rollbackOn = {SQLException.class, PersistenceException.class, DataIntegrityViolationException.class,
            ObjectOptimisticLockingFailureException.class})
    public Object handleDatabaseException(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();  // 실제 메서드 실행
        } catch (SQLException ex) { // JDBC 레벨의 예외 처리
            log.error("SQL error in method: {}. Error: {}", joinPoint.getSignature(), ex.getMessage());
            throw ex; // SQLException 그대로 던지기
        } catch (PersistenceException ex) {
            // JPA 예외 처리
            log.error("Persistence error in method: {}. Error: {}", joinPoint.getSignature(), ex.getMessage());
            throw ex; // PersistenceException 그대로 던지기
        } catch (DataIntegrityViolationException ex) {
            // 무결성 제약 조건 위반 등
            log.error("Data integrity violation in method: {}. Error: {}", joinPoint.getSignature(), ex.getMessage());
            throw ex; // DataIntegrityViolationException 그대로 던지기
        } catch (ObjectOptimisticLockingFailureException ex) {
            // 버전 충돌 등으로 인한 낙관적 락 실패
            log.error("Optimistic locking failure in method: {}. Error: {}", joinPoint.getSignature(), ex.getMessage());
            throw ex; // ObjectOptimisticLockingFailureException 그대로 던지기
        } catch (Exception ex) {
            // 기타 예외 처리
            log.error("Unexpected error in method: {}. Error: {}", joinPoint.getSignature(), ex.getMessage());
            throw ex; // 기타 예외 그대로 던지기
        }
    }

    // 읽기 작업에 대해 예외를 처리하는 AOP
    @Around("execution(* com.ccommit.price_tracking_server.repository.*.find(..)) ||" +
            "execution(* com.ccommit.price_tracking_server.repository.*.get(..)) ||" +
            "execution(* com.ccommit.price_tracking_server.repository.*.exists(..))")
    public Object handleReadException(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();  // 실제 메서드 실행
        } catch (ConnectException ex) {
            // 서버에 연결할 수 없는 경우 (ex. 서버 다운, 포트 미개방 등)
            log.error("Connection refused in method: {}. Error: {}", joinPoint.getSignature(), ex.getMessage());
            throw ex; // ConnectException 그대로 던지기
        } catch (SocketTimeoutException ex) {
            // 응답 지연 등으로 연결이 시간 초과된 경우
            log.error("Timeout occurred in method: {}. Error: {}", joinPoint.getSignature(), ex.getMessage());
            throw ex; // SocketTimeoutException 그대로 던지기
        } catch (UnknownHostException ex) {
            // 도메인 이름을 찾을 수 없을 때
            log.error("Unknown host in method: {}. Error: {}", joinPoint.getSignature(), ex.getMessage());
            throw ex; // UnknownHostException 그대로 던지기
        } catch (SocketException ex) {
            // 연결이 끊기는 일반적인 네트워크 오류
            log.error("Socket exception in method: {}. Error: {}", joinPoint.getSignature(), ex.getMessage());
            throw ex; // SocketException 그대로 던지기
        } catch (IOException ex) {
            // 그 외 기타 입출력 오류
            log.error("I/O error in method: {}. Error: {}", joinPoint.getSignature(), ex.getMessage());
            throw ex; // IOException 그대로 던지기
        }
    }

}
