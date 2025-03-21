package com.ccommit.price_tracking_server.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionDetailMessage {

    // Common
    INTERNAL_SERVER_ERROR("서버에서 예기치 않은 오류가 발생했습니다."),
    MISSING_REQUIRED_FIELD("필수 필드 {0}이(가) 누락되었습니다."),

    // User
    INVALID_PASSWORD_FORMAT("비밀번호는 최소 8자 이상이어야 하며, 소문자, 숫자, 특수기호를 포함해야 합니다."),
    PASSWORD_MISMATCH("입력한 비밀번호와 확인 비밀번호가 일치하지 않습니다."),
    INVALID_USERNAME_FORMAT("닉네임은 4자 이상, 20자 이하의 영문자 또는 숫자만 포함할 수 있습니다."),
    INVALID_PHONE_FORMAT("휴대폰 번호는 010으로 시작하는 11자리 숫자여야 합니다."),
    INVALID_EMAIL_FORMAT("이메일 형식이 잘못되었습니다. 유효한 이메일 주소를 입력해 주세요.");

    private final String message;

    // 예외 코드에 해당하는 메시지를 반환하는 정적 메서드
    public static String getExceptionMessage(String exceptionCode) {
        try {
            return ExceptionDetailMessage.valueOf(exceptionCode).getMessage();
        } catch (IllegalArgumentException e) {
            return "알 수 없는 예외입니다."; // 예외 코드가 존재하지 않는 경우
        }
    }
}
