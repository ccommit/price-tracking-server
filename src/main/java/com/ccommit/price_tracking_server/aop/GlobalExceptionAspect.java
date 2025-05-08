package com.ccommit.price_tracking_server.aop;

import jakarta.persistence.PersistenceException;
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
    @Around("execution(* com.ccommit.price_tracking_server.repository.*.save*(..)) || " +
            "execution(* com.ccommit.price_tracking_server.repository.*.delete*(..))")
    public Object handleDatabaseAndNetworkException(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            // 비즈니스 로직 실행
            return joinPoint.proceed();
        } catch (SQLException ex) {
            // SQL 예외 처리 및 RuntimeException으로 감싸기
            log.error("메서드에서 SQL 오류 발생: {}. 오류 메시지: {}", joinPoint.getSignature(), ex.getMessage());
            throw new RuntimeException("메서드에서 SQL 오류 발생: " + joinPoint.getSignature(), ex);
        } catch (PersistenceException ex) {
            // PersistenceException 처리 및 RuntimeException으로 감싸기
            log.error("메서드에서 JPA 오류(Persistence) 발생: {}. 오류 메시지: {}", joinPoint.getSignature(), ex.getMessage());
            throw new RuntimeException("메서드에서 JPA 오류(Persistence) 발생: " + joinPoint.getSignature(), ex);
        } catch (DataIntegrityViolationException ex) {
            // 데이터 무결성 위반 예외 처리 및 RuntimeException으로 감싸기
            log.error("메서드에서 데이터 무결성 위반 발생: {}. 오류 메시지: {}", joinPoint.getSignature(), ex.getMessage());
            throw new RuntimeException("메서드에서 데이터 무결성 위반 발생: " + joinPoint.getSignature(), ex);
        } catch (ObjectOptimisticLockingFailureException ex) {
            // 낙관적 잠금 실패 처리 및 RuntimeException으로 감싸기
            log.error("메서드에서 낙관적 잠금 실패 발생: {}. 오류 메시지: {}", joinPoint.getSignature(), ex.getMessage());
            throw new RuntimeException("메서드에서 낙관적 잠금 실패 발생: " + joinPoint.getSignature(), ex);
        } catch (ConnectException ex) {
            // 연결 실패 예외 처리 및 RuntimeException으로 감싸기
            log.error("메서드에서 연결 거부 오류 발생: {}. 오류 메시지: {}", joinPoint.getSignature(), ex.getMessage());
            throw new RuntimeException("메서드에서 연결 거부 오류 발생: " + joinPoint.getSignature(), ex);
        } catch (SocketTimeoutException ex) {
            // 타임아웃 예외 처리 및 RuntimeException으로 감싸기
            log.error("메서드에서 시간 초과(Timeout) 오류 발생: {}. 오류 메시지: {}", joinPoint.getSignature(), ex.getMessage());
            throw new RuntimeException("메서드에서 시간 초과(Timeout) 오류 발생: " + joinPoint.getSignature(), ex);
        } catch (UnknownHostException ex) {
            // 알 수 없는 호스트 예외 처리 및 RuntimeException으로 감싸기
            log.error("메서드에서 알 수 없는 호스트 오류 발생: {}. 오류 메시지: {}", joinPoint.getSignature(), ex.getMessage());
            throw new RuntimeException("메서드에서 알 수 없는 호스트 오류 발생: " + joinPoint.getSignature(), ex);
        } catch (SocketException ex) {
            // 소켓 예외 처리 및 RuntimeException으로 감싸기
            log.error("메서드에서 소켓 오류 발생: {}. 오류 메시지: {}", joinPoint.getSignature(), ex.getMessage());
            throw new RuntimeException("메서드에서 소켓 오류 발생: " + joinPoint.getSignature(), ex);
        } catch (IOException ex) {
            // I/O 예외 처리 및 RuntimeException으로 감싸기
            log.error("메서드에서 입출력(I/O) 오류 발생: {}. 오류 메시지: {}", joinPoint.getSignature(), ex.getMessage());
            throw new RuntimeException("메서드에서 입출력(I/O) 오류 발생: " + joinPoint.getSignature(), ex);
        } catch (Exception ex) {
            // 예기치 않은 예외 처리 및 RuntimeException으로 감싸기
            log.error("메서드에서 예기치 않은 오류 발생: {}. 오류 메시지: {}", joinPoint.getSignature(), ex.getMessage());
            throw new RuntimeException("메서드에서 예기치 않은 오류 발생: " + joinPoint.getSignature(), ex);
        }
    }

    // 읽기 작업용 AOP
    @Around("execution(* com.ccommit.price_tracking_server.repository.*.find*(..)) ||" +
            "execution(* com.ccommit.price_tracking_server.repository.*.get*(..)) ||" +
            "execution(* com.ccommit.price_tracking_server.repository.*.exists*(..))")
    public Object handleReadException(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();  // 실제 메서드 실행
        } catch (ConnectException ex) {
            // 서버에 연결할 수 없는 경우 (ex. 서버 다운, 포트 미개방 등)
            log.error("메서드에서 연결 거부 오류 발생: {}. 오류 메시지: {}", joinPoint.getSignature(), ex.getMessage());
            throw ex; // ConnectException 그대로 던지기
        } catch (SocketTimeoutException ex) {
            // 응답 지연 등으로 연결이 시간 초과된 경우
            log.error("메서드에서 시간 초과(Timeout) 오류 발생: {}. 오류 메시지: {}", joinPoint.getSignature(), ex.getMessage());
            throw ex; // SocketTimeoutException 그대로 던지기
        } catch (UnknownHostException ex) {
            // 도메인 이름을 찾을 수 없을 때
            log.error("메서드에서 알 수 없는 호스트 오류 발생: {}. 오류 메시지: {}", joinPoint.getSignature(), ex.getMessage());
            throw ex; // UnknownHostException 그대로 던지기
        } catch (SocketException ex) {
            // 연결이 끊기는 일반적인 네트워크 오류
            log.error("메서드에서 소켓 오류 발생: {}. 오류 메시지: {}", joinPoint.getSignature(), ex.getMessage());
            throw ex; // SocketException 그대로 던지기
        } catch (IOException ex) {
            // 그 외 기타 입출력 오류
            log.error("메서드에서 입출력(I/O) 오류 발생: {}. 오류 메시지: {}", joinPoint.getSignature(), ex.getMessage());
            throw ex; // IOException 그대로 던지기
        }
    }
}
