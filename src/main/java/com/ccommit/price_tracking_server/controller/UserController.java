package com.ccommit.price_tracking_server.controller;

import com.ccommit.price_tracking_server.DTO.CommonResponse;
import com.ccommit.price_tracking_server.DTO.UserDTO;
import com.ccommit.price_tracking_server.DTO.UserLoginResponse;
import com.ccommit.price_tracking_server.DTO.UserProfileResponse;
import com.ccommit.price_tracking_server.aop.annotation.CheckToken;
import com.ccommit.price_tracking_server.enums.SuccessDetailMessage;
import com.ccommit.price_tracking_server.enums.UserStatus;
import com.ccommit.price_tracking_server.service.UserService;
import com.ccommit.price_tracking_server.utils.EmailMaskingUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<UserProfileResponse>> registerUser(@RequestBody @Validated({UserDTO.SignUp.class}) UserDTO userDTO) {
        log.info("회원가입 요청: email={}", EmailMaskingUtil.maskEmail(userDTO.getEmail()));

        UserProfileResponse signUpDTO = userService.registerUser(userDTO);

        SuccessDetailMessage message = SuccessDetailMessage.SUCCESS_USER_REGISTERED;
        CommonResponse<UserProfileResponse> response = new CommonResponse<>(message.name(), message.getMessage(), signUpDTO);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<UserLoginResponse>> loginUser(@RequestBody @Validated({UserDTO.Login.class}) UserDTO userDTO) {
        log.info("로그인 요청: email={}", EmailMaskingUtil.maskEmail(userDTO.getEmail()));

        SuccessDetailMessage message = SuccessDetailMessage.SUCCESS_USER_LOGGED_IN;
        CommonResponse<UserLoginResponse> response = new CommonResponse<>(message.name(), message.getMessage(), userService.loginUser(userDTO));

        return ResponseEntity.ok(response);
    }

    @CheckToken(roles = {UserStatus.ACTIVE})
    @PatchMapping
    public ResponseEntity<CommonResponse<UserProfileResponse>> updateUser(String email, @RequestBody @Validated({UserDTO.Update.class}) UserDTO userDTO) {
        log.info("회원 정보 수정 요청: email={}", EmailMaskingUtil.maskEmail(email));

        SuccessDetailMessage message = SuccessDetailMessage.SUCCESS_USER_UPDATED;
        CommonResponse<UserProfileResponse> response = new CommonResponse<>(message.name(), message.getMessage(), userService.updateUser(email, userDTO));

        return ResponseEntity.ok(response);
    }

    @CheckToken(roles = {UserStatus.ACTIVE})
    @PostMapping("/refresh-token")
    public ResponseEntity<CommonResponse<String>> refreshToken() {
        log.info("토큰 재발급 요청");

        // SuccessDetailMessage 사용하여 응답 메시지 설정
        SuccessDetailMessage message = SuccessDetailMessage.SUCCESS_TOKEN_REFRESHED;
        CommonResponse<String> response = new CommonResponse<>(message.name(), message.getMessage());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/username-check")
    public ResponseEntity<CommonResponse<Boolean>> nicknameCheck(@RequestParam("userName") String userName) {
        log.info("닉네임 중복 확인 요청: nickname={}", userName);

        // SuccessDetailMessage 사용하여 응답 메시지 설정
        SuccessDetailMessage message = SuccessDetailMessage.SUCCESS_NICKNAME_CHECKED;
        CommonResponse<Boolean> response = new CommonResponse<>(message.name(), message.getMessage(), userService.checkNickname(userName));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    @CheckToken(roles = {UserStatus.ACTIVE})
    public ResponseEntity<CommonResponse<Boolean>> logout(String email) {
        log.info("로그아웃 요청");

        SuccessDetailMessage message = SuccessDetailMessage.SUCCESS_USER_LOGGED_OUT;
        CommonResponse<Boolean> response = new CommonResponse<>(message.name(), message.getMessage(), userService.logoutUser(email));

        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    @CheckToken(roles = {UserStatus.ACTIVE})
    public ResponseEntity<CommonResponse<Boolean>> deleteUser(String email) {
        log.info("회원 탈퇴 요청: email={}", EmailMaskingUtil.maskEmail(email));

        SuccessDetailMessage message = SuccessDetailMessage.SUCCESS_USER_DELETED;
        CommonResponse<Boolean> response = new CommonResponse<>(message.name(), message.getMessage(), userService.deleteUser(email));

        return ResponseEntity.ok(response);
    }
}
