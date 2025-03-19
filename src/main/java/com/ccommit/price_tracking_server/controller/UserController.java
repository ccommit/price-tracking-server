package com.ccommit.price_tracking_server.controller;

import com.ccommit.price_tracking_server.DTO.CommonResponseDTO;
import com.ccommit.price_tracking_server.DTO.UserDTO;
import com.ccommit.price_tracking_server.DTO.UserSignUpDTO;
import com.ccommit.price_tracking_server.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/members")
@RestController
@Log4j2
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final Logger logger = LogManager.getLogger(UserController.class);

    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDTO<UserSignUpDTO>> registerUser(@RequestBody @Validated({UserDTO.SignUp.class}) UserDTO userDTO) {
        logger.debug("회원을 가입합니다.");
        CommonResponseDTO<UserSignUpDTO> response = new CommonResponseDTO<>("SUCCESS", "회원가입 성공",
                userService.registerUser(userDTO), "", 0);
        logger.info("회원가입 성공");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
