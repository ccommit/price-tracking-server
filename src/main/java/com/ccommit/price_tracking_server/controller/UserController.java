package com.ccommit.price_tracking_server.controller;

import com.ccommit.price_tracking_server.DTO.CommonResponseDTO;
import com.ccommit.price_tracking_server.DTO.UserDTO;
import com.ccommit.price_tracking_server.DTO.UserLoginResponseDTO;
import com.ccommit.price_tracking_server.DTO.UserProfileResponseDTO;
import com.ccommit.price_tracking_server.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
        log.info("회원가입 요청: email={}", userDTO.getEmail());

        UserProfileResponseDTO signUpDTO = userService.registerUser(userDTO);
        CommonResponseDTO<UserProfileResponseDTO> response = new CommonResponseDTO<>("SUCCESS", "회원가입 성공",
                signUpDTO, "", "", 0);

        log.info("회원가입 성공: email={}", userDTO.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponseDTO<UserLoginResponseDTO>> loginUser(@RequestBody @Validated({UserDTO.Login.class}) UserDTO userDTO) {
        log.info("로그인 요청: email={}", userDTO.getEmail());

        UserLoginResponseDTO loginDTO = userService.loginUser(userDTO);
        CommonResponseDTO<UserLoginResponseDTO> response = new CommonResponseDTO<>("SUCCESS", "로그인 성공",
                loginDTO, "", "", 0);

        log.info("로그인 성공: email={}", userDTO.getEmail());
        return ResponseEntity.ok(response);
    }

    @PatchMapping
    public ResponseEntity<CommonResponseDTO<UserProfileResponseDTO>> updateUser(@AuthenticationPrincipal String email, @RequestBody @Validated({UserDTO.Update.class}) UserDTO userDTO) {
        log.info("회원 정보 수정 요청: email={}", email);

        UserProfileResponseDTO updateDTO = userService.updateUser(email, userDTO);
        CommonResponseDTO<UserProfileResponseDTO> response = new CommonResponseDTO<>("SUCCESS", "회원 정보 수정 성공",
                updateDTO, "", "", 0);

        log.info("회원 정보 수정 성공: email={}", email);
        return ResponseEntity.ok(response);
    }
}
