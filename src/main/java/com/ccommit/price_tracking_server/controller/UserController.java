package com.ccommit.price_tracking_server.controller;

import com.ccommit.price_tracking_server.DTO.CommonResponseDTO;
import com.ccommit.price_tracking_server.DTO.UserDTO;
import com.ccommit.price_tracking_server.DTO.UserLoginResponseDTO;
import com.ccommit.price_tracking_server.DTO.UserProfileResponseDTO;
import com.ccommit.price_tracking_server.annotation.CheckToken;
import com.ccommit.price_tracking_server.enums.UserStatus;
import com.ccommit.price_tracking_server.service.UserService;
import com.ccommit.price_tracking_server.utils.EmailMaskingUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<CommonResponseDTO<UserProfileResponseDTO>> registerUser(@RequestBody @Validated({UserDTO.SignUp.class}) UserDTO userDTO) {
        log.info("회원가입 요청: email={}", EmailMaskingUtil.maskEmail(userDTO.getEmail()));

        UserProfileResponseDTO signUpDTO = userService.registerUser(userDTO);
        CommonResponseDTO<UserProfileResponseDTO> response = new CommonResponseDTO<>(signUpDTO, "", "", 0);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponseDTO<UserLoginResponseDTO>> loginUser(@RequestBody @Validated({UserDTO.Login.class}) UserDTO userDTO) {
        log.info("로그인 요청: email={}", EmailMaskingUtil.maskEmail(userDTO.getEmail()));

        UserLoginResponseDTO loginDTO = userService.loginUser(userDTO);
        CommonResponseDTO<UserLoginResponseDTO> response = new CommonResponseDTO<>(loginDTO, "", "", 0);

        return ResponseEntity.ok(response);
    }

    @CheckToken(roles = {UserStatus.ACTIVE})
    @PatchMapping
    public ResponseEntity<CommonResponseDTO<UserProfileResponseDTO>> updateUser(String email, @RequestBody @Validated({UserDTO.Update.class}) UserDTO userDTO) {
        log.info("회원 정보 수정 요청: email={}", EmailMaskingUtil.maskEmail(email));

        UserProfileResponseDTO updateDTO = userService.updateUser(email, userDTO);
        CommonResponseDTO<UserProfileResponseDTO> response = new CommonResponseDTO<>(updateDTO, "", "", 0);

        return ResponseEntity.ok(response);
    }

    @CheckToken(roles = {UserStatus.ACTIVE})
    @PostMapping("/refresh-token")
    public ResponseEntity<CommonResponseDTO<String>> refreshToken() {
        // 이 부분은 AOP에서 처리되므로 실제 코드가 필요없습니다.
        log.info("토큰 재발급 요청");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/username-check")
    public ResponseEntity<CommonResponseDTO<Boolean>> nicknameCheck(@RequestParam("userName") String userName) {
        log.info("닉네임 중복 확인 요청: nickname={}", userName);
        CommonResponseDTO<Boolean> response = new CommonResponseDTO<>(userService.checkNickname(userName), "", "", 0);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    @CheckToken(roles = {UserStatus.ACTIVE})
    public ResponseEntity<CommonResponseDTO<Boolean>> logout(String email) {
        log.info("로그아웃 요청");
        CommonResponseDTO<Boolean> response = new CommonResponseDTO<>(userService.logoutUser(email), "", "", 0);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    @CheckToken(roles = {UserStatus.ACTIVE})
    public ResponseEntity<CommonResponseDTO<Boolean>> deleteUser(String email) {
        log.info("회원 탈퇴 요청: email={}", EmailMaskingUtil.maskEmail(email));
        CommonResponseDTO<Boolean> response = new CommonResponseDTO<>(userService.deleteUser(email), "", "", 0);
        return ResponseEntity.ok(response);
    }
}
