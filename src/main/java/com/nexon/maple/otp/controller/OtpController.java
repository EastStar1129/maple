package com.nexon.maple.otp.controller;

import com.nexon.maple.config.dto.ResponseDTO;
import com.nexon.maple.otp.service.OtpWriteService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@RestController
@Validated
public class OtpController {
    private final OtpWriteService otpWriteService;

    @PostMapping("/{userName}/otps")
    public ResponseEntity<ResponseDTO> createOtp(
            @NotNull(message = "이름은 필수 값입니다.")
            @Length(min = 2, max = 12, message = "이름의 최소길이는 2자리에서 12자리입니다.")
            @PathVariable("userName") String userName) {
        return ResponseEntity.ok().body(ResponseDTO.ofSuccess("OTP 번호가 발급되었습니다.", otpWriteService.createOtp(userName)));
    }

}
