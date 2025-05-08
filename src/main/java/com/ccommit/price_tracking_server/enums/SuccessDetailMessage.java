package com.ccommit.price_tracking_server.enums;

import com.ccommit.price_tracking_server.exception.ExceptionDetailMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessDetailMessage {
    //User
    SUCCESS_USER_REGISTERED("회원가입이 성공적으로 완료되었습니다."),
    SUCCESS_USER_LOGGED_IN("로그인이 성공적으로 완료되었습니다."),
    SUCCESS_USER_UPDATED("회원 정보가 성공적으로 수정되었습니다."),
    SUCCESS_TOKEN_REFRESHED("토큰이 성공적으로 재발급되었습니다."),
    SUCCESS_NICKNAME_CHECKED("닉네임 중복 확인이 완료되었습니다."),
    SUCCESS_USER_LOGGED_OUT("로그아웃이 성공적으로 완료되었습니다."),
    SUCCESS_USER_DELETED("회원 탈퇴가 성공적으로 완료되었습니다."),

    //Category
    SUCCESS_CATEGORY_CREATED("카테고리가 성공적으로 생성되었습니다."),
    SUCCESS_CATEGORY_UPDATED("카테고리가 성공적으로 수정되었습니다."),
    SUCCESS_CATEGORY_DELETED("카테고리가 성공적으로 삭제되었습니다."),

    //Product
    SUCCESS_PRODUCT_SELECTED("상품 정보가 성공적으로 조회되었습니다.");

    private final String message;

    public static String getExceptionMessage(String exceptionCode) {
        try {
            return ExceptionDetailMessage.valueOf(exceptionCode).getMessage();
        } catch (IllegalArgumentException e) {
            return "알 수 없는 코드입니다."; // 성공 코드가 존재하지 않는 경우
        }
    }
}
