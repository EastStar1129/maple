package com.nexon.maple.userInfo.controller;

import com.nexon.maple.config.dto.ResponseDTO;
import com.nexon.maple.userInfo.dto.RegisterUserInfoDTO;
import com.nexon.maple.userInfo.service.RegisteUserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Validated
public class UserController {
    private final RegisteUserInfoService registeUserInfoService;

    @PostMapping("/users")
    public ResponseEntity<ResponseDTO> register(@RequestBody RegisterUserInfoDTO registerUserInfoDTO) {
        registeUserInfoService.regist(registerUserInfoDTO);

        return ResponseEntity.ok().body(ResponseDTO.ofSuccess("회원가입이 완료되었습니다. 로그인해주세요.", null));
    }
}
