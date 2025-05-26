package com.ccommit.price_tracking_server.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionDetailMessage {

    // Common
    INTERNAL_SERVER_ERROR("서버에서 예기치 않은 오류가 발생했습니다."),
    MISSING_REQUIRED_FIELD("필수 필드 {0}이(가) 누락되었습니다."),
    NETWORK_ERROR("네트워크 오류가 발생했습니다. 잠시 후 다시 시도해주세요."),
    INVALID_REQUEST("잘못된 요청입니다. 요청 형식을 확인해주세요."),

    // User
    INVALID_PASSWORD_FORMAT("비밀번호는 최소 8자 이상이어야 하며, 소문자, 숫자, 특수기호를 포함해야 합니다."),
    PASSWORD_MISMATCH("입력한 비밀번호와 확인 비밀번호가 일치하지 않습니다."),
    INVALID_USERNAME_FORMAT("닉네임은 4자 이상, 20자 이하의 영문자 또는 숫자만 포함할 수 있습니다."),
    INVALID_PHONE_FORMAT("휴대폰 번호는 010으로 시작하는 11자리 숫자여야 합니다."),
    INVALID_EMAIL_FORMAT("이메일 형식이 잘못되었습니다. 이메일은 최소 5자 이상의 문자, 숫자 또는 +, _, ., -를 포함해야 하며, " +
            "@ 뒤 도메인은 최소 3자 이상, 최상위 도메인(TLD)은 최소 2자 이상이어야 합니다."),
    INVALID_PASSWORD("입력한 비밀번호가 올바르지 않습니다."),
    USER_NOT_FOUND("입력한 이메일로 가입된 사용자가 없습니다. 이메일을 확인해주세요."),
    USER_ACCOUNT_DISABLED("입력한 이메일에 해당하는 사용자 계정이 비활성화되었습니다. 고객 지원에 문의해주세요."),
    USERNAME_TAKEN("이미 사용 중인 닉네임입니다."),
    DUPLICATE_EMAIL("이미 사용 중인 이메일입니다."),

    // Security
    TOKEN_EXPIRED("JWT 토큰이 만료되었습니다. 다시 로그인하여 새 토큰을 발급받아주세요."),
    INVALID_TOKEN_FORMAT("제공된 JWT 토큰 형식이 올바르지 않습니다. 유효한 토큰을 제공해주세요."),
    INVALID_SIGNATURE("제공된 JWT 토큰의 서명 검증에 실패했습니다. 유효한 토큰을 제공해주세요."),
    UNAUTHORIZED("유효한 JWT 토큰이 필요합니다. 로그인 후 다시 시도해주세요."),
    ROLE_ACCESS_DENIED("해당 리소스에 접근할 권한이 없습니다."),
    INVALID_REFRESH_TOKEN("유효하지 않은 리프레시 토큰입니다. 제공된 리프레시 토큰이 Redis에 저장된 토큰과 일치하지 않습니다. " +
            "유효한 리프레시 토큰을 사용해주세요."),
    TOKEN_NOT_FOUND("제공된 JWT 토큰이 Redis에 존재하지 않습니다. 유효한 토큰을 사용해주세요."),

    // Category
    PARENT_CATEGORY_NOT_FOUND("부모 카테고리를 찾을 수 없습니다."),
    INVALID_CATEGORY_LEVEL("잘못된 카테고리 레벨입니다."),
    CATEGORY_NOT_FOUND("카테고리를 찾을 수 없습니다."),
    CATEGORY_NAME_TOO_LONG("카테고리 이름은 1자 이상, 20자 이하이어야 합니다."),
    CATEGORY_HAS_CHILDREN("하위 카테고리가 존재해 삭제할 수 없습니다.");

    private final String message;

    // 예외 코드에 해당하는 메시지를 반환하는 정적 메서드
    public static String getExceptionMessage(String exceptionCode) {
        try {
            return ExceptionDetailMessage.valueOf(exceptionCode).getMessage();
        } catch (IllegalArgumentException e) {
            return "알 수 없는 예외입니다."; // 예외 코드가 존재하지 않는 경우
        }
    }

    public static ExceptionDetailMessage exceptionCodeToEnum(String exceptionCode) {
        try {
            return ExceptionDetailMessage.valueOf(exceptionCode);
        } catch (IllegalArgumentException e) {
            return null; // 예외 코드가 존재하지 않는 경우
        }
    }
}
