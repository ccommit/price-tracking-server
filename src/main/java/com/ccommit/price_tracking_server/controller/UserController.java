package com.ccommit.price_tracking_server.controller;

import com.ccommit.price_tracking_server.DTO.CommonResponseDTO;
import com.ccommit.price_tracking_server.DTO.UserDTO;
import com.ccommit.price_tracking_server.DTO.UserSignUpDTO;
import com.ccommit.price_tracking_server.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDTO<UserSignUpDTO>> registerUser(@RequestBody @Validated({UserDTO.SignUp.class}) UserDTO userDTO) {
        log.info("회원가입 요청: username={}, email={}", userDTO.getUsername(), userDTO.getEmail());

        UserSignUpDTO signUpDTO = userService.registerUser(userDTO);
        CommonResponseDTO<UserSignUpDTO> response = new CommonResponseDTO<>("SUCCESS", "회원가입 성공",
                signUpDTO, "", "", 0);

        log.info("회원가입 성공: username={}", signUpDTO.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
