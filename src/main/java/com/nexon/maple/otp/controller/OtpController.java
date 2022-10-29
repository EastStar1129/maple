package com.nexon.maple.otp.controller;

import com.nexon.maple.config.dto.ResponseDTO;
import com.nexon.maple.otp.service.OtpWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OtpController {
    private final OtpWriteService otpWriteService;

    @PostMapping("/{name}/otps")
    public ResponseEntity<ResponseDTO> createOtp(@PathVariable("name") String userName) {
        return ResponseEntity.ok().body(ResponseDTO.ofSuccess("OTP 번호가 발급되었습니다.", otpWriteService.createOtp(userName)));
    }

}
